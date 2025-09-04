/**
 * Interfaccia che definisce i metodi di business per la gestione dei ruoli.
 * Puoi aggiungere nuovi metodi per logiche di business aggiuntive.
 *
 * Per estendere:
 * - Aggiungi metodi per nuove funzionalità relative ai ruoli.
 */
package com.backendapi.backendapi.service;

import com.backendapi.backendapi.model.entity.RoleEntity;

public interface RoleService {
    RoleEntity save(RoleEntity roleEntity);
}
