/*
Javier Urbaneja Benítez: 50%
IA: 50%
*/
package com.leftjoiners.bancosol.proyectobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turno {
    private Integer id;
    private TiendaCampanya tiendaCampanya;
    private Colaborador colaborador;
    private TipoTurno tipoTurno;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer lineal;
    private Integer numVoluntarios;
    private String observaciones;
    private LocalDate dia;
}