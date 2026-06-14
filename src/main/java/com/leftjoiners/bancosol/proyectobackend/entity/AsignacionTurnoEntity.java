/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Subselect("SELECT * FROM vista_asignacion_colaboradores")
@Immutable // Muy importante: Le dice a Hibernate que esta "tabla" es de solo lectura y no intente hacer UPDATEs o INSERTs
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionTurnoEntity {

    @Id
    @Column(name = "id_tienda_campanya")
    private Integer idTiendaCampanya;

    private String tienda;

    private String domicilio;

    private String localidad;

    private String capitan;

    private Integer lineales;

    @Column(name = "viernes_manana")
    private String viernesManana;

    @Column(name = "viernes_tarde")
    private String viernesTarde;

    @Column(name = "sabado_manana")
    private String sabadoManana;

    @Column(name = "sabado_tarde")
    private String sabadoTarde;
}