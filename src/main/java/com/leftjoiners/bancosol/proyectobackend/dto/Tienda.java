package com.leftjoiners.bancosol.proyectobackend.dto;

import com.leftjoiners.bancosol.proyectobackend.entity.TiendaCampanyaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {
    private Integer id;
    private Cadena cadena;
    private String nombre;
    private Integer lineales;
    private Localidad localidad;
    private String cp;
    private Distrito distrito;
    private String domicilio;
    private Usuario capitan;


    private List<TiendaCampanya> tiendasCampanya;
}