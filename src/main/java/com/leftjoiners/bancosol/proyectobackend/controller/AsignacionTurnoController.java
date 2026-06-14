/*
Javier Urbaneja Benítez: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.controller;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.*;
import com.leftjoiners.bancosol.proyectobackend.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/turnos")
public class AsignacionTurnoController {
    private final TiendaCampanyaService tiendaCampanyaService;
    private final AsignacionTurnoService asignacionTurnoService;
    private final TurnoService turnoService;
    private final ColaboradorService colaboradorService;
    private final TipoTurnoService tipoTurnoService;
    private final ContactoColaboradorService contactoColaboradorService;
    private final TipoCampanyaService tipoCampanyaService;
    private final CampanyasService campanyasService;

    private void cargarInicio(Model model) {
        List<TipoCampanya> tipoCampanyas = this.tipoCampanyaService.listarTipoCampanyas();

        model.addAttribute("tipoCampanyas", tipoCampanyas);
        model.addAttribute("currentSection", "turnos");
    }

    @GetMapping("")
    public String doInit(Model model) {
        this.cargarInicio(model);

        List<AsignacionTurno> asignacionesTurno = asignacionTurnoService.listarAsignacionColaboradores();
        List<Campanya> campanyas = this.campanyasService.listarCampanyas();

        model.addAttribute("asignacionesTurno", asignacionesTurno);
        model.addAttribute("campanyas", campanyas);
        model.addAttribute("tipoCampanyaActual", 0);
        model.addAttribute("campanyaActual", 0);

        return "turnos/asignacion_turno";
    }

    @GetMapping("/filtrar")
    public String filtrarAsignaciones(@RequestParam("tipoCampanyaId") Integer tipoCampanyaId,
                                      @RequestParam("campanyaId") Integer campanyaId,
                                      Model model) {
        this.cargarInicio(model);

        List<AsignacionTurno> asignacionesTurno = new ArrayList<>();
        List<Campanya> campanyas = new ArrayList<>();


        if (tipoCampanyaId != null && campanyaId != null) {
            asignacionesTurno = this.asignacionTurnoService.filtrarPorTipoyCampanya(tipoCampanyaId, campanyaId);
            campanyas = this.campanyasService.filtrarCampanyasPorTipo(tipoCampanyaId);
        }

        model.addAttribute("asignacionesTurno", asignacionesTurno);
        model.addAttribute("campanyas", campanyas);
        model.addAttribute("tipoCampanyaActual", tipoCampanyaId);
        model.addAttribute("campanyaActual", campanyaId);

        return "turnos/asignacion_turno";
    }


    @PostMapping("/buscarTurno")
    public String buscarTurno(@RequestParam(value = "id", required = false) Integer idTienda,
                              @RequestParam(value = "turno", required = false) Integer turno,
                              @RequestParam(value = "lineales", required = false) Integer lineales,
                              @RequestParam(value = "linealActual", required = false) Integer linealActual,
                              Model model) {

        Turno asignacionTurno = this.turnoService.buscarTurnoEspecifico(idTienda, turno, linealActual);
        TiendaCampanya tiendaCampanya = this.tiendaCampanyaService.buscarTiendaCampanya(idTienda);

        model.addAttribute("turno", turno);
        model.addAttribute("lineales", lineales);
        model.addAttribute("linealActual", linealActual);
        model.addAttribute("tienda", tiendaCampanya);
        model.addAttribute("asignacionTurno", asignacionTurno);

        return "turnos/info_turno";
    }

    @GetMapping("/crearTurno")
    public String crearTurno(@RequestParam(value = "id", required = false) Integer id,
                             @RequestParam(value = "turno", required = false) Integer turno,
                             @RequestParam(value = "lineal", required = false) Integer lineal,
                             @RequestParam(value = "errorModal", required = false) String errorModal,
                             Model model) {

        TiendaCampanya tienda = this.tiendaCampanyaService.buscarTiendaCampanya(id);
        Turno asignacionTurno = this.turnoService.buscarTurnoEspecifico(id, turno, lineal);

        Colaborador colaborador = null;
        ContactoColaborador contactoPrincipal = null;

        if (asignacionTurno != null && asignacionTurno.getColaborador() != null) {
            colaborador = this.colaboradorService.buscarColaborador(asignacionTurno.getColaborador().getId());
            contactoPrincipal = this.contactoColaboradorService.buscarContactoPrincipalDe(colaborador.getId());
        }

        if (asignacionTurno == null) {
            asignacionTurno = new Turno();
            asignacionTurno.setTiendaCampanya(tienda);
            asignacionTurno.setLineal(lineal);
            asignacionTurno.setTipoTurno(this.tipoTurnoService.buscarTipoTurno(turno));
        }

        model.addAttribute("colaboradores", this.colaboradorService.listarColaboradores());
        model.addAttribute("contactoPrincipal", contactoPrincipal);
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("tienda", tienda.getTienda());
        model.addAttribute("asignacionTurno", asignacionTurno);
        model.addAttribute("currentSection", "turnos");
        model.addAttribute("errorModal", errorModal);

        return "turnos/formulario_turno";
    }

    @PostMapping("/guardarTurno")
    public String guardarTurno(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("tiendaCampanyaId") Integer tiendaCampanyaId,
                               @RequestParam("tipoTurnoId") Integer tipoTurnoId,
                               @RequestParam("lineal") Integer lineal,
                               @RequestParam("idColaborador") Integer idColaborador,
                               @RequestParam(value = "horaInicio", required = false ) LocalTime horaInicio,
                               @RequestParam(value = "horaFin", required = false) LocalTime horaFin,
                               @RequestParam(value ="numVoluntarios", required = false) Integer numVoluntarios,
                               @RequestParam("observaciones") String observaciones,
                               Model model) {
        String errorModal = null;
        if (horaInicio == null || horaFin == null) {
          errorModal = "Error: Debes introducir hora de inicio y fin";
        } else if (horaInicio.isAfter(horaFin)) {
            errorModal = "Error: La hora de inicio no puede ser posterior a la de fin.";
        } else if (numVoluntarios != null && numVoluntarios < 1) {
            errorModal = "Error: Debe haber al menos 1 voluntario asignado.";
        }

        if (errorModal != null) {
            TiendaCampanya tienda = this.tiendaCampanyaService.buscarTiendaCampanya(tiendaCampanyaId);
            Colaborador colaborador = (idColaborador != null && idColaborador != 0) ?
                    this.colaboradorService.buscarColaborador(idColaborador) : null;

            Turno asignacionTurno = new Turno();
            asignacionTurno.setId(id);
            asignacionTurno.setTiendaCampanya(tienda);
            asignacionTurno.setTipoTurno(this.tipoTurnoService.buscarTipoTurno(tipoTurnoId));
            asignacionTurno.setLineal(lineal);
            asignacionTurno.setColaborador(colaborador);
            asignacionTurno.setHoraInicio(horaInicio);
            asignacionTurno.setHoraFin(horaFin);
            asignacionTurno.setNumVoluntarios(numVoluntarios);
            asignacionTurno.setObservaciones(observaciones);

            model.addAttribute("colaboradores", this.colaboradorService.listarColaboradores());
            model.addAttribute("colaborador", colaborador);
            model.addAttribute("tienda", tienda.getTienda());
            model.addAttribute("asignacionTurno", asignacionTurno);
            model.addAttribute("currentSection", "turnos");
            model.addAttribute("errorModal", errorModal);

            return "turnos/formulario_turno";
        }


        this.turnoService.guardarTurno(
                id, tiendaCampanyaId, tipoTurnoId, lineal, idColaborador,
                horaInicio, horaFin, numVoluntarios, observaciones
        );

        return "redirect:/turnos";
    }
}
