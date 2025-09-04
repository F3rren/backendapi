/**
 * Classe di utilità per aggiungere header di sicurezza alle risposte HTTP.
 * Puoi aggiungere o modificare header per migliorare la sicurezza (es: CSP, XSS-Protection).
 *
 * Per estendere:
 * - Aggiungi nuovi header o modifica quelli esistenti.
 * - Integra controlli dinamici in base all'endpoint.
 */
package com.backendapi.backendapi.util;

public enum CustomSecurityHeaders {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private final String value;

    CustomSecurityHeaders(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
