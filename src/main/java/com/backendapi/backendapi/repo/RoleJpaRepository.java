/**
 * Interfaccia che estende JpaRepository per accedere ai dati dei ruoli.
 * Qui puoi aggiungere query personalizzate (es: findByRoleName).
 *
 * Per estendere:
 * - Aggiungi metodi per query personalizzate.
 * - Integra query JPQL o native query se necessario.
 */
package com.backendapi.backendapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendapi.backendapi.model.entity.RoleEntity;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}