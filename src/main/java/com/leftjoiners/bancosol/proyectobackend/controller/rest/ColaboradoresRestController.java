/*
Marina Fernández Serrano: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Colaborador;
import com.leftjoiners.bancosol.proyectobackend.service.ColaboradorService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/colaboradores")
public class ColaboradoresRestController {
    private final ColaboradorService colaboradorService;

    @GetMapping("/")
    public List<Colaborador> doInit() {
        return this.colaboradorService.listarColaboradores();
    }

    @GetMapping("/buscar/{id}")
    public Colaborador buscarColaborador(@PathVariable Integer id) {
        return this.colaboradorService.buscarColaborador(id);
    }
    @GetMapping("/filtrar")
    public List<Colaborador> filtrar(@RequestParam(required = false) Integer idLocalidad, @RequestParam(required = false) Integer idCoordinador){
        return this.colaboradorService.filtrarColaboradores(idLocalidad,idCoordinador);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ColaboradorRequest {
        private Integer id;
        private String nombre;
        private String codigo;
        private String contactoPrincipal;
        private String domicilio;
        private String cp;
        private String observaciones;
        private Integer idLocalidadSede;
        private  Integer idLocalidadColabora;
        private Boolean temporal;
        private Integer idCoordinador;
    }

    @PostMapping("/guardar")
    public void guardarColaborador(@RequestBody ColaboradoresRestController.ColaboradorRequest request) {
        this.colaboradorService.guardarColaborador(
                request.getId(),
                request.getNombre(),
                request.getCodigo(),
                request.getContactoPrincipal(),
                request.getDomicilio(),
                request.getCp(),
                request.getObservaciones(),
                request.getIdLocalidadSede(),
                request.getIdLocalidadColabora(),
                request.getTemporal(),
                request.getIdCoordinador());
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarColaborador(@PathVariable Integer id) {
        this.colaboradorService.eliminarColaborador(id);
    }
}
