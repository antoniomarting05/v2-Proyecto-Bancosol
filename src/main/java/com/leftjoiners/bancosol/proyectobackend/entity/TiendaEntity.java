/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tienda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cadena")
    private CadenaEntity cadena;

    private String nombre;
    private Integer lineales;

    @ManyToOne
    @JoinColumn(name = "id_localidad")
    private LocalidadEntity localidad;

    private String cp;

    @ManyToOne
    @JoinColumn(name = "id_distrito")
    private DistritoEntity distrito;

    private String domicilio;

    @ManyToOne
    @JoinColumn(name = "id_capitan")
    private UsuarioEntity capitan;

    @OneToMany(mappedBy = "tienda")
    private List<TiendaCampanyaEntity> tiendasCampanya = new ArrayList<>();
}