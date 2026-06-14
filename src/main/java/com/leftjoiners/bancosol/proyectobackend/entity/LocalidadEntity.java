/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "localidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private MunicipioEntity municipio;

    @OneToMany(mappedBy = "localidad")
    private List<TiendaEntity> tiendas = new ArrayList<>();

    @OneToMany(mappedBy = "localidad")
    private List<UsuarioEntity> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "localidadSede")
    private List<ColaboradorEntity> colaboradoresSede = new ArrayList<>();

    @OneToMany(mappedBy = "colaboraEn")
    private List<ColaboradorEntity> colaboradoresColaboran = new ArrayList<>();
}