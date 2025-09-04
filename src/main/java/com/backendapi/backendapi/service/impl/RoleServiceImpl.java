/**
 * Implementazione della logica di business per la gestione dei ruoli.
 * Qui scrivi la logica effettiva dei metodi dichiarati in RoleService.
 *
 * Per estendere:
 * - Aggiungi nuove logiche di business o modifica quelle esistenti.
 * - Integra servizi esterni o logiche di validazione avanzate.
 */
package com.backendapi.backendapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.backendapi.backendapi.model.entity.RoleEntity;
import com.backendapi.backendapi.repo.RoleJpaRepository;
import com.backendapi.backendapi.service.RoleService;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        log.info("Saving role {} to the database", roleEntity.getName());
        return roleJpaRepository.save(roleEntity);
    }


}