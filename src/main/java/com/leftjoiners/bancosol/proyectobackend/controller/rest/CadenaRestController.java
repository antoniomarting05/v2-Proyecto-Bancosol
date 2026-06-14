package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Cadena;
import com.leftjoiners.bancosol.proyectobackend.service.CadenaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cadenas")
public class CadenaRestController {
    private final CadenaService cadenaService;

    @GetMapping("/")
    public List<Cadena> doInit() {
        return this.cadenaService.listarCadenas();
    }

    @GetMapping("/{id}")
    public Cadena buscarCadena(@PathVariable Integer id) {
        return this.cadenaService.buscarCadena(id);
    }


    @DeleteMapping("/eliminar")
    public void eliminarCadenas(@RequestBody List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            this.cadenaService.eliminarCadenas(ids);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CadenaRequest {
        private Integer id;
        private String nombre;
        private String codigo;
    }

    @PostMapping("/guardar")
    public void guardarCadena(@RequestBody CadenaRequest request) {
        this.cadenaService.guardarCadena(
                request.getId(),
                request.getNombre(),
                request.getCodigo()
        );
    }
}