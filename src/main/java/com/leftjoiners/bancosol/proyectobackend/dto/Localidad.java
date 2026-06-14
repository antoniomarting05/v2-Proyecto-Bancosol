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
public class Localidad {
    private Integer id;
    private String nombre;
    private Municipio municipio;

    private List<Integer> tiendas;
    private List<Integer> usuarios;
    private List<Integer> colaboradoresSede;
    private List<Integer> colaboradoresColaboran;
}