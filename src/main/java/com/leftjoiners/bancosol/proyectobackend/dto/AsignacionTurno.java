/*
Javier Urbaneja Benítez: 50%
IA: 50%
*/
package com.leftjoiners.bancosol.proyectobackend.dto;

import lombok.Data;

@Data
public class AsignacionTurno {
    private Integer idTiendaCampanya;
    private String tienda;
    private String domicilio;
    private String localidad;
    private String capitan;
    private Integer lineales;
    private String viernesManana;
    private String viernesTarde;
    private String sabadoManana;
    private String sabadoTarde;
}