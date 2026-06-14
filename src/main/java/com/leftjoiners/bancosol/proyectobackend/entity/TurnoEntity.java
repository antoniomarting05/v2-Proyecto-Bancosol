/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "asignacion_turno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_tienda_campanya")
    private TiendaCampanyaEntity tiendaCampanya;

    @ManyToOne
    @JoinColumn(name = "id_colaborador")
    private ColaboradorEntity colaborador;

    // Relación añadida con la tabla tipo_turno
    @ManyToOne
    @JoinColumn(name = "id_turno")
    private TipoTurnoEntity tipoTurno;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    private Integer lineal;

    @Column(name = "num_voluntarios")
    private Integer numVoluntarios;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    private LocalDate dia;
}