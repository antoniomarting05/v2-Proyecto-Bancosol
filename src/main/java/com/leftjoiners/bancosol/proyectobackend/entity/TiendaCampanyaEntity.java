/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tienda_campanya")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiendaCampanyaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_tienda")
    private TiendaEntity tienda;

    @ManyToOne
    @JoinColumn(name = "id_campanya")
    private CampanyaEntity campanya;

    @ManyToOne
    @JoinColumn(name = "id_coordinador")
    private UsuarioEntity coordinador;

    @OneToMany(mappedBy = "tiendaCampanya")
    private List<TurnoEntity> turnos = new ArrayList<>();
}