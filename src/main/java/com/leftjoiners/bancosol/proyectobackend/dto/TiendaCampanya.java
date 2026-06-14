/*
Javier Urbaneja Benítez: 50%
IA: 50%
*/
package com.leftjoiners.bancosol.proyectobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaCampanya {
    private Integer id;
    private Tienda tienda;
    private Campanya campanya;
    private Usuario coordinador;

    private List<Integer> turnos;
}