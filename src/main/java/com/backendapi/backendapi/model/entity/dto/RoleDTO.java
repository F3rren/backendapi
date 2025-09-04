/**
 * Oggetto Data Transfer Object per trasferire dati relativi ai ruoli tra backend e frontend.
 * Utile per non esporre direttamente le entità del database.
 * Puoi creare altri DTO per diverse esigenze di trasferimento dati.
 *
 * Per estendere:
 * - Aggiungi nuovi campi per esigenze di frontend.
 * - Crea altri DTO per altri casi d'uso.
 */
package com.backendapi.backendapi.model.entity.dto;

import lombok.Data;

@Data
public class RoleDTO {

    private String roleName;
}

