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
public class Usuario {
    private Integer id;
    private String rol;
    private String usuario;
    private String nombre;
    private String telefono;
    private String email;
    private Integer idEntidad;
    private String entidad;
    private Integer idZonaAsignada;
    private String zonaAsignada;
    private Integer idLocalidad;
    private String localidad;
    private Integer cp;
    private Integer idDistrito;
    private String distrito;

    private List<Integer> colaboradoresCoordinados;
    private List<Integer> tiendasCoordinadas;
    private List<Integer> tiendasCapitaneadas;
}
