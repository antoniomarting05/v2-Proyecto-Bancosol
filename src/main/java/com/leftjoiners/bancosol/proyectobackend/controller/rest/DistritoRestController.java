package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Distrito;
import com.leftjoiners.bancosol.proyectobackend.service.DistritoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/distritos")
public class DistritoRestController {
    private final DistritoService distritoService;

    @GetMapping("/")
    public List<Distrito> doInit() {
        return this.distritoService.listarDistritos();
    }

}
