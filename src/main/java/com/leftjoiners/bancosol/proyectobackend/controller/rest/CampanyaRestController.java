package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.AsignacionTurno;
import com.leftjoiners.bancosol.proyectobackend.dto.Campanya;
import com.leftjoiners.bancosol.proyectobackend.service.CampanyasService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/campanyas")
public class CampanyaRestController {
    private final CampanyasService campanyasService;

    @GetMapping("/")
    public List<Campanya> doInit() {
        return this.campanyasService.listarCampanyas();
    }

    @GetMapping("/{id}")
    public Campanya buscarCampanya(@PathVariable Integer id) {
        return this.campanyasService.buscarCampanya(id);
    }

    @DeleteMapping("/eliminar")
    public void eliminarCampanyas(@RequestBody List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            this.campanyasService.eliminarCampanyas(ids);
        }
    }

    @GetMapping("/filtrarPorTipo/{idTipo}")
    public List<Campanya> filtrarPorTipo(@PathVariable Integer idTipo){
        List<Campanya> campanyas = new ArrayList<>();

        if (idTipo != null) {
            campanyas = this.campanyasService.filtrarCampanyasPorTipo(idTipo);
        }

        return campanyas;
    }

    @GetMapping("/participantes/{idTienda}")
    public List<Campanya> getCampanyasParticipantes(@PathVariable Integer idTienda) {
        return this.campanyasService.buscarCampanyasParticipantes(idTienda);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampanyaRequest {
        private Integer id;
        private String nombre;
        private Integer idTipo;
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private List<Integer> cadenasSeleccionadas;
    }

    @PostMapping("/guardar")
    public void guardarCampanya(@RequestBody CampanyaRequest request) {
        this.campanyasService.guardarCampanya(
                request.getId(),
                request.getNombre(),
                request.getIdTipo(),
                request.getFechaInicio(),
                request.getFechaFin(),
                request.getCadenasSeleccionadas()
        );
    }
}
