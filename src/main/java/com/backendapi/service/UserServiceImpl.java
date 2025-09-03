package com.backendapi.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Spring;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendapi.model.entity.RoleEntity;
import com.backendapi.model.entity.UserEntity;
import com.backendapi.repo.RoleJpaRepository;
import com.backendapi.repo.UserJpaRepository;

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
    public UserEntity addRoleToUser(String username, String roleName) 
    {
        log.info("Adding role {} to user {}", roleName, username);
        UserEntity userEntity = userJpaRepository.findByUsername(username);
        RoleEntity roleEntity = roleJpaRepository.findByName(roleName);
        userEntity.getRoles().add(roleEntity);
        return userEntity;
    }

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

}

