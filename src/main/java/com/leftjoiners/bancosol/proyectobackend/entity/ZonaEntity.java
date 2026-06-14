/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @OneToMany(mappedBy = "zona")
    private List<MunicipioEntity> municipios = new ArrayList<>();

    @OneToMany(mappedBy = "zonaAsignada")
    private List<UsuarioEntity> usuarios = new ArrayList<>();
}