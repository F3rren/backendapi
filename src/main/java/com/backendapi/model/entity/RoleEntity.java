package com.backendapi.model.entity;

//Con @Entity indichiamo a JPA che questa classe Java mappa una tabella a DB.
@Entity
//Con @Table, indichiamo a JPA il nome della tabella.
@Table(name = 'ROLES')
//Con @Id e @GeneratedValue indichiamo a JPA che l'attributo annotato è una primary key, 
// che deve essere auto-generata.
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

}