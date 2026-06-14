/*
Javier Urbaneja Benítez: 50%
IA: 50%
*/
package com.leftjoiners.bancosol.proyectobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campanya {
    private Integer id;
    private TipoCampanya tipoCampanya;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer duracion;

    private List<Cadena> cadenasParticipantes;
    private List<Integer> tiendasCampanya;
}