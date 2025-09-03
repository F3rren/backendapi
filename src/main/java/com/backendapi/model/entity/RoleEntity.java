package com.backendapi.model.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Con @Entity indichiamo a JPA che questa classe Java mappa una tabella a DB.
@Entity
//Con @Table, indichiamo a JPA il nome della tabella.
@Table(name = "ROLES")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoleEntity implements Serializable {

    //Con @Id e @GeneratedValue indichiamo a JPA che l'attributo annotato è una primary key, 
    // che deve essere auto-generata.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

}