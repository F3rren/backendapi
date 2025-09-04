/**
 * Entità JPA che rappresenta la tabella 'user' nel database.
 * Qui definisci i campi che corrispondono alle colonne del DB.
 * Puoi aggiungere nuovi campi (es: email, data di nascita) e aggiornare il DB di conseguenza.
 *
 * Per estendere:
 * - Aggiungi nuovi campi e aggiorna le migration del database.
 * - Integra relazioni con altre entità.
 */
package com.backendapi.backendapi.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Con @Entity indichiamo a JPA che questa classe Java mappa una tabella a DB.
@Entity
//Con @Table, indichiamo a JPA il nome della tabella.
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserEntity implements Serializable {

    //Con @Id e @GeneratedValue indichiamo a JPA che l'attributo annotato è una primary key, 
    // che deve essere auto-generata.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    //Con @Column(unique = true, nullable = false) indichiamo a JPA che, quando 
    // genererà le tabelle a DB, dovrà creare anche i vincoli unique e not null 
    // per il campo annotato.
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles = new ArrayList<>();
}
