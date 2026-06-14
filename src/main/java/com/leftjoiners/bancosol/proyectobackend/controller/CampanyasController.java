/*
Antonio Martín García : 90%
IA: 10%
*/
package com.leftjoiners.bancosol.proyectobackend.controller;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.Cadena;
import com.leftjoiners.bancosol.proyectobackend.dto.Campanya;
import com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity;
import com.leftjoiners.bancosol.proyectobackend.service.CadenaService;
import com.leftjoiners.bancosol.proyectobackend.service.CampanyasService;
import com.leftjoiners.bancosol.proyectobackend.service.TipoCampanyaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequestMapping("/campanyas")
@Controller
@AllArgsConstructor
public class CampanyasController {
    private final CampanyasService campanyasService;
    private final TipoCampanyaService tipoCampanyaService;
    private final CadenaService cadenaService;

    @GetMapping("")
    public String listarCampanyas(Model model) {
        model.addAttribute("campanyas", this.campanyasService.listarCampanyas());
        model.addAttribute("currentSection", "campanyas");
        model.addAttribute("eliminar", false);
        return "campanyas/campanya";
    }

    @GetMapping("/crearCampanya")
    public String crearCampanya(Model model) {
        model.addAttribute("tiposCampanya", this.tipoCampanyaService.listarTipoCampanyas());
        model.addAttribute("cadenas", this.cadenaService.listarCadenas());
        model.addAttribute("editando", false);
        model.addAttribute("currentSection", "campanyas");
        return "campanyas/formularioCampanya";
    }

    @PostMapping("/guardarCampanya")
    public String guardarCampanya(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("nombreCampanya") String nombre,
            @RequestParam("tipoCampanya") Integer idTipo,
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin,
            @RequestParam(value = "cadenaParticipa", required = false) List<Integer> cadenasSeleccionadas
            ) {
        this.campanyasService.guardarCampanya(id, nombre, idTipo,
                fechaInicio, fechaFin, cadenasSeleccionadas);
        return "redirect:/campanyas";
    }

    @GetMapping("/editarCampanya")
    public String editarCampanya(@RequestParam("id") Integer id, Model model) {
        Campanya campanya = this.campanyasService.buscarCampanya(id);
        if (campanya == null) {
            return "redirect:/campanyas";
        }
        model.addAttribute("currentSection", "campanyas");

        //Valores de la campañana que estamos editando.
        model.addAttribute("nombreCampanya", campanya.getNombre());
        model.addAttribute("idCampanya", campanya.getId());
        model.addAttribute("fechaInicio", campanya.getFechaInicio());
        model.addAttribute("fechaFin", campanya.getFechaFin());
        model.addAttribute("tipoCampanyaActual", campanya.getTipoCampanya());

        List<Cadena> cadenasCampanya = campanya.getCadenasParticipantes();
        model.addAttribute("cadenasCampanyaActual", cadenasCampanya);
        model.addAttribute("editando", true);

        //Valores genericos
        model.addAttribute("tiposCampanya", this.tipoCampanyaService.listarTipoCampanyas());
        model.addAttribute("cadenas", this.cadenaService.listarCadenas());

        model.addAttribute("currentSection", "campanyas");

        return "campanyas/formularioCampanya";
    }

    @GetMapping("/seleccionCampanyasEliminar")
    public String seleccionarCampanyasEliminar(Model model){

        model.addAttribute("campanyas", this.campanyasService.listarCampanyas());
        model.addAttribute("currentSection", "campanyas");
        model.addAttribute("eliminar", true);
        return "campanyas/campanya";
    }

    @PostMapping("/eliminarCampanyas")
    public String eliminarCampanyas(Model model,
                                  @RequestParam(required = false, value="campanyasElim") List<Integer> idCampanyasEliminar
                                  ){
        if(idCampanyasEliminar != null){
            this.campanyasService.eliminarCampanyas(idCampanyasEliminar);
        } else {
            return "redirect:/campanyas";
        }

        model.addAttribute("eliminar", false);
        return "redirect:/campanyas";

    }

    // ==========




    @GetMapping("/gestionarCadenas")
    public String gestionarCadenas(Model model){
        List<Cadena> listaCadenas = this.cadenaService.listarCadenas();

        model.addAttribute("cadenasSistema", listaCadenas);
        model.addAttribute("currentSection", "campanyas");
        model.addAttribute("eliminar", false);
        return "campanyas/cadenas";
    }

    @GetMapping("/seleccionCadenasEliminar")
    public String seleccionarCadenasEliminar(Model model){
        List<Cadena> listaCadenas = this.cadenaService.listarCadenas();

        model.addAttribute("cadenasSistema", listaCadenas);
        model.addAttribute("currentSection", "campanyas");
        model.addAttribute("eliminar", true);
        return "campanyas/cadenas";
    }

    @GetMapping("/crearCadena")
    public String crearCadena(Model model){
        model.addAttribute("editando", false);
        model.addAttribute("currentSection", "campanyas");
        return "campanyas/formularioCadena";
    }

    @GetMapping("/editarCadena")
    public String editarCadena(Model model,
                               @RequestParam("id")Integer idCadena
                                ){
        Cadena cadena = this.cadenaService.buscarCadena(idCadena);

        if (cadena == null) {
            return "redirect:/campanyas/gestionarCadenas";
        }

        model.addAttribute("nombreCadena", cadena.getNombre());
        model.addAttribute("codigoCadena", cadena.getCodigo());
        model.addAttribute("editando", true);
        model.addAttribute("idCadena", idCadena);
        model.addAttribute("currentSection", "campanyas");

        return "campanyas/formularioCadena";
    }

    @PostMapping("/guardarCadena")
    public String guardarCadena(@RequestParam("nombre") String nombreCadena,
                                @RequestParam("codigo") String codigoCadena,
                                @RequestParam(required = false, value = "id" ) Integer idCadena
                                ){
        this.cadenaService.guardarCadena(idCadena, nombreCadena, codigoCadena);
        return "redirect:/campanyas/gestionarCadenas";
    }

    @PostMapping("/eliminarCadenasSistema")
    public String guardarCadenas (@RequestParam(required = false, value = "cadenas") List<Integer> idCadenasEliminar){

        if(idCadenasEliminar != null){
            this.cadenaService.eliminarCadenas(idCadenasEliminar);
        }

        return "redirect:/campanyas/gestionarCadenas";
    }

}
