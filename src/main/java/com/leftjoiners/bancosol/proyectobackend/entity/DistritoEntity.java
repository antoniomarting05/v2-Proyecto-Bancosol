/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "distrito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistritoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numero;
    private String nombre;

    @OneToMany(mappedBy = "distrito")
    private List<TiendaEntity> tiendas = new ArrayList<>();

    @OneToMany(mappedBy = "distrito")
    private List<UsuarioEntity> usuarios = new ArrayList<>();
}