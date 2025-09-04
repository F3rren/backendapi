
/**
 * Configurazione della sicurezza Spring Security.
 * Qui imposti le regole di accesso agli endpoint, i filtri di autenticazione/autorizzazione, ecc.
 * Puoi aggiungere nuove regole, endpoint pubblici, o integrare provider di autenticazione esterni.
 *
 * Per estendere:
 * - Aggiungi nuove regole nei metodi authorizeHttpRequests.
 * - Integra nuovi filtri personalizzati.
 * - Modifica la gestione delle sessioni o dei token JWT.
 */
package com.backendapi.backendapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backendapi.backendapi.security.filter.CustomAuthenticationFilter;
import com.backendapi.backendapi.security.filter.CustomAuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

        return http.build();
    }

}