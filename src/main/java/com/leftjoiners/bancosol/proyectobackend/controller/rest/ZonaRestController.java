package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Zona;
import com.leftjoiners.bancosol.proyectobackend.service.ZonaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/zonas")
public class ZonaRestController {

    private final ZonaService zonaService;

    @GetMapping("/")
    public List<Zona> doInit() {
        return this.zonaService.listarZonas();
    }
}