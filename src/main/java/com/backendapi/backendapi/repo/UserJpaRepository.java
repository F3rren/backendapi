/**
 * Interfaccia che estende JpaRepository per accedere ai dati degli utenti.
 * Qui puoi aggiungere query personalizzate (es: findByEmail, findByUsername).
 *
 * Per estendere:
 * - Aggiungi metodi per query personalizzate.
 * - Integra query JPQL o native query se necessario.
 */
package com.backendapi.backendapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendapi.backendapi.model.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}