/*
Javier Urbaneja Benítez: 40%
Marina Fernández Serrano: 40%
IA: 20%
*/
package com.leftjoiners.bancosol.proyectobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactoColaborador {
    private Integer id;
    private Integer idColaborador;
    private String nombre;
    private String email;
    private String telefono;
    private Boolean esPrincipal;
    private Colaborador colaborador;
}