package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.AsignacionTurno;
import com.leftjoiners.bancosol.proyectobackend.dto.Campanya;
import com.leftjoiners.bancosol.proyectobackend.dto.Turno;
import com.leftjoiners.bancosol.proyectobackend.service.AsignacionTurnoService;
import com.leftjoiners.bancosol.proyectobackend.service.TurnoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/asignacionTurnos")
public class AsignacionTurnoRestController {
    private final AsignacionTurnoService asignacionTurnoService;
    private final TurnoService turnoService;

    @GetMapping("/")
    public List<AsignacionTurno> doInit() {
        return this.asignacionTurnoService.listarAsignacionColaboradores();
    }

    @GetMapping("/buscarTurno/{idTienda}/{turno}/{linealActual}")
    public Turno buscarTurno(
            @PathVariable Integer idTienda,
            @PathVariable Integer turno,
            @PathVariable Integer linealActual) {

        return turnoService.buscarTurnoEspecifico(idTienda, turno, linealActual);
    }

    @GetMapping("/filtrar/{tipoCampanyaId}/{campanyaId}")
    public List<AsignacionTurno> filtrarTurnos(@PathVariable Integer tipoCampanyaId,
                                               @PathVariable Integer campanyaId) {
        List<AsignacionTurno> asignacionesTurno = new ArrayList<>();

        if (tipoCampanyaId != null && campanyaId != null) {
            asignacionesTurno = this.asignacionTurnoService.filtrarPorTipoyCampanya(tipoCampanyaId, campanyaId);
        }

        return asignacionesTurno;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TurnoRequest {
        private Integer idTurno;
        private Integer idTiendaCampanya;
        private Integer idTipoTurno;
        private Integer lineal;
        private Integer idColaborador;
        private Integer numVoluntarios;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private String observaciones;
    }

    @PostMapping("/guardar")
    public void guardarTurno(@RequestBody TurnoRequest request) {
        this.turnoService.guardarTurno(
                request.getIdTurno(),
                request.getIdTiendaCampanya(),
                request.getIdTipoTurno(),
                request.getLineal(),
                request.getIdColaborador(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getNumVoluntarios(),
                request.getObservaciones()
        );
    }
}

