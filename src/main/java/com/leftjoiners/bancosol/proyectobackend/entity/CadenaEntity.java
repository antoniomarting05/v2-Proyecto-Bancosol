/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cadena")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CadenaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;
    private String nombre;

    @OneToMany(mappedBy = "cadena")
    private List<TiendaEntity> tiendas = new ArrayList<>();

    // --- MAPEO INVERSO MUCHOS A MUCHOS ---
    @ManyToMany(mappedBy = "cadenasParticipantes")
    private List<CampanyaEntity> campanyas = new ArrayList<>();
}