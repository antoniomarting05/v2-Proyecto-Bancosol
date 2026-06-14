package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Localidad;
import com.leftjoiners.bancosol.proyectobackend.service.LocalidadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/localidades")
public class LocalidadRestController {

    private final LocalidadService localidadService;

    @GetMapping("/")
    public List<Localidad> doInit() {
        return this.localidadService.listarLocalidades();
    }
}