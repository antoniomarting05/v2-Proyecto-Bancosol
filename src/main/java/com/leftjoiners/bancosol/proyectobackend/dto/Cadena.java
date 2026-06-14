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
public class Cadena {
    private Integer id;
    private String codigo;
    private String nombre;

    private List<Integer> tiendas;
    private List<Integer> campanyas;
}