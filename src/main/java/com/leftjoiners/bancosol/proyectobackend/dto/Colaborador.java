/*
Javier Urbaneja Benítez: 40%
Marina Fernández Serrano: 40%
IA: 20%
*/

package com.leftjoiners.bancosol.proyectobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colaborador {
    private Integer id;
    private String codigo;
    private String nombre;
    private String contactoPrincipal;
    private String domicilio;
    private String cp;
    private Localidad localidadSede;
    private Localidad colaboraEn;
    private Boolean temporal;
    private Usuario coordinador;
    private String observaciones;

    private List<ContactoColaborador> contactos;
    private List<Integer> turnos;
}