package com.backendapi.backendapi.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendapi.backendapi.model.entity.RoleEntity;
import com.backendapi.backendapi.model.entity.UserEntity;
import com.backendapi.backendapi.repo.RoleJpaRepository;
import com.backendapi.backendapi.repo.UserJpaRepository;
import com.backendapi.backendapi.service.UserService;
import com.backendapi.backendapi.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Con @Service indichiamo a Spring che la classe è un bean con logica di business.
@Service
//Con @Transactional indichiamo a Spring che tutti i metodi della classe sono in transazione.
@Transactional

//@RequiredArgsConstructor e @Slf4j sono due annotations della libreria Lombok, che ci permettono 
// di autogenerare un costruttore in base ai campi final, e creare un logger, rispettivamente.
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with username %s not found";

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;

    
    @Transactional(readOnly = true)
    @Override
    // Il metodo loadUserByUsername semplicemente cerca l'utente con username in input, sul DB.
    // Se esiste, trasforma i ruoli RoleEntity in SimpleGrantedAuthority, che è la classe di default 
    // di Spring Security per la gestione dei ruoli ed infine restituisce una istanza di tipo User, 
    // che è una classe di Spring Security che implementa UserDetails.
    // Se non esiste l'utente, viene lanciata una eccezione di tipo UsernameNotFoundException.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userJpaRepository.findByUsername(username);
        if(user == null){
            String message = String.format(USER_NOT_FOUND_MESSAGE, username);
            log.error(message);
            throw new UsernameNotFoundException(message);
        } else {
            log.debug("User found at database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role->{
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
        
    }
    
    //Il metodo save, oltre a richiamare banalmente il metodo save del repository, codifica la 
    // password prima di salvare a db. Creeremo successivamente un bean di tipo PasswordEncoder.
    public UserEntity save(UserEntity user) 
    {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userJpaRepository.save(user);
    }

    //Il metodo addRoleToUser permette di aggiungere un ruolo esistente ad un utente esistente. 
    // Non viene invocato il metodo save di UserJpaRepository in quanto userEntity è già una entity 
    // managed, essendo in transazione, e quindi tutte le sue modifiche dopo il findByUsername 
    // vengono salvare.
    @Override
    public UserEntity addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        UserEntity userEntity = userJpaRepository.findByUsername(username);
        RoleEntity roleEntity = roleJpaRepository.findByName(roleName);
        userEntity.getRoles().add(roleEntity);
        return userEntity;
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findByUsername(String username) {
        log.info("Retrieving user {}", username);
        return userJpaRepository.findByUsername(username);
    }   

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> findAll() {
        log.info("Retrieving all users");
        return userJpaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String,String> refreshToken(String authorizationHeader, String issuer) throws BadJOSEException,
            ParseException, JOSEException {

        String refreshToken = authorizationHeader.substring("Bearer ".length());
        UsernamePasswordAuthenticationToken authenticationToken = JwtUtil.parseToken(refreshToken);
        String username = authenticationToken.getName();
        UserEntity userEntity = findByUsername(username);
        List<String> roles = userEntity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList());
        String accessToken = JwtUtil.createAccessToken(username, issuer, roles);
        return Map.of("access_token", accessToken, "refresh_token", refreshToken);
    }

}

