package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Municipio;
import com.leftjoiners.bancosol.proyectobackend.service.MunicipioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/municipios")
public class MunicipioRestController {
    private final MunicipioService municipioService;

    @GetMapping("/")
    public List<Municipio> doInit() {
        return this.municipioService.listarMunicipios();
    }
}
