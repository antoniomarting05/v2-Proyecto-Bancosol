/*
Antonio Martín García : 100%
*/
package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Entidad;
import com.leftjoiners.bancosol.proyectobackend.service.EntidadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/entidades")
public class EntidadRestController {

    private final EntidadService entidadService;

    @GetMapping("/")
    public List<Entidad> doInit() {
        return entidadService.listarEntidades();
    }
}
