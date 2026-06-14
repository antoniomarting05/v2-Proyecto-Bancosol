/*
Daniel Robles Cantos: 70%
Javier Urbaneja Benítez: 20%
IA: 10%
*/
package com.leftjoiners.bancosol.proyectobackend.controller;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.Campanya;
import com.leftjoiners.bancosol.proyectobackend.dto.Tienda;
import com.leftjoiners.bancosol.proyectobackend.dto.TiendaCampanya;
import com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya;
import com.leftjoiners.bancosol.proyectobackend.entity.*;
import com.leftjoiners.bancosol.proyectobackend.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/tiendas")
@Controller
@AllArgsConstructor
public class TiendasController {

    private final TiendaService tiendaService;
    private final TiendaCampanyaService tiendaCampanyaService;
    private final TipoCampanyaService tipoCampanyaService;
    private final CampanyasService campanyasService;
    private final CadenaService cadenaService;
    private final ZonaService zonaService;
    private final LocalidadService localidadService;
    private final MunicipioService municipioService;
    private final DistritoService distritoService;
    private final UsuarioService usuarioService;


    // Parte de visualización y edición de tiendas: Daniel Robles Cantos

    @GetMapping("")
    public String doTiendas(Model model) {
        model.addAttribute("tiendas", this.tiendaService.listarTiendas());
        model.addAttribute("currentSection", "tiendas");

        // Listas para los filtros
        model.addAttribute("cadenas", this.cadenaService.listarCadenas());
        model.addAttribute("zonas", this.zonaService.listarZonas());
        model.addAttribute("localidades", this.localidadService.listarLocalidades());

        return "tiendas/tiendas";
    }

    @PostMapping("/filtrarTiendas")
    public String doFiltrarTiendas(Model model,
                                   @RequestParam(value = "cadena-tienda", required = false) Integer cadenaId,
                                   @RequestParam(value = "localidad-tienda", required = false) Integer localidadId,
                                   @RequestParam(value = "zona-tienda", required = false) Integer zonaId) {

        List<Tienda> tiendasFiltradas = this.tiendaService.filtrarTiendas(cadenaId, localidadId, zonaId);
        model.addAttribute("tiendas", tiendasFiltradas);
        model.addAttribute("currentSection", "tiendas");

        model.addAttribute("cadenas", this.cadenaService.listarCadenas());
        model.addAttribute("zonas", this.zonaService.listarZonas());
        model.addAttribute("localidades", this.localidadService.listarLocalidades());

        model.addAttribute("cadenaMarcada", cadenaId);
        model.addAttribute("zonaMarcada", zonaId);
        model.addAttribute("localidadMarcada", localidadId);

        return "tiendas/tiendas";
    }

    @GetMapping("/crearTienda")
    public String doCrearTienda(Model model, @RequestParam(value = "id", required = false) Integer idTienda) {
        //si id entonces editamos, sino no
        boolean isEditando = (idTienda != null);

        model.addAttribute("editando", isEditando);
        model.addAttribute("viendo", false);
        model.addAttribute("currentSection", "tiendas");

        if (isEditando) {
            Tienda tienda = this.tiendaService.buscarTienda(idTienda);
            model.addAttribute("tiendaActual", tienda);
            model.addAttribute("campanyasDeLaTienda", this.campanyasService.buscarCampanyasParticipantes(idTienda));
        }

        this.cargarDesplegablesFormulario(model);

        return "tiendas/crear_tienda";
    }


    @PostMapping("/guardarTienda")
    public String guardarTienda(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "lineales", required = false) Integer lineales,
            @RequestParam("domicilio") String domicilio,
            @RequestParam(value = "codigoPostal", required = false) String codigoPostal,
            @RequestParam(value = "distritoId", required = false) Integer distritoId,
            @RequestParam("cadenaId") Integer cadenaId,
            @RequestParam("localidadId") Integer localidadId,
            @RequestParam(value = "capitanId", required = false) Integer capitanId,
            @RequestParam(value = "campanyaIds", required = false) List<Integer> campanyaIds,
            @RequestParam(value = "coordinadorIds", required = false) List<Integer> coordinadorIds) {

        this.tiendaService.guardarTienda(id, nombre, lineales, domicilio, codigoPostal, distritoId,
                cadenaId, localidadId, capitanId);

        if (campanyaIds != null) {
            for (int i = 0; i < campanyaIds.size(); i++) {
                Integer cId = campanyaIds.get(i);
                Integer uId = (coordinadorIds != null && i < coordinadorIds.size()) ? coordinadorIds.get(i) : null;

                // actualizo coordinadores
                this.tiendaService.actualizarCoordinadorEnCampanya(id, cId, uId);
            }
        }

        return "redirect:/tiendas";
    }


    @GetMapping("/verTienda")
    public String doVerTienda(Model model, @RequestParam("id") Integer idTienda) {
        Tienda tienda = this.tiendaService.buscarTienda(idTienda);

        model.addAttribute("tiendaActual", tienda);
        model.addAttribute("editando", false);
        model.addAttribute("viendo", true);
        model.addAttribute("currentSection", "tiendas");

        model.addAttribute("campanyasDeLaTienda", this.campanyasService.buscarCampanyasParticipantes(idTienda));

        this.cargarDesplegablesFormulario(model);

        return "tiendas/crear_tienda";
    }

    @GetMapping("/eliminarTienda")
    public String doEliminarTienda(@RequestParam("id") Integer idTienda) {
        this.tiendaService.eliminarTienda(idTienda);
        return "redirect:/tiendas";
    }

    private void cargarDesplegablesFormulario(Model model) {
        model.addAttribute("cadenas", this.cadenaService.listarCadenas());
        model.addAttribute("zonas", this.zonaService.listarZonas());
        model.addAttribute("municipios", this.municipioService.listarMunicipios());
        model.addAttribute("localidades", this.localidadService.listarLocalidades());
        model.addAttribute("distritos", this.distritoService.listarDistritos());
        model.addAttribute("coordinadores", this.usuarioService.listarCoordinadores());
        model.addAttribute("capitanes", this.usuarioService.listarCapitanes());
    }



    // Parte de Asignación de Participación: Javier Urbaneja Benítez

    @GetMapping("/asignarParticipacion")
    public String asignarParicipacion(@RequestParam("id") Integer idTienda, Model model) {
        Tienda tienda = this.tiendaService.buscarTienda(idTienda);
        List<TiendaCampanya> tiendasCampanya = this.tiendaCampanyaService.buscarTiendasCampanyaPorTienda(tienda.getId());
        List<TipoCampanya> tipoCampanyas = this.tipoCampanyaService.buscarTipoCampanyaParticipantes(idTienda);
        List<Campanya> campanyas = this.campanyasService.buscarCampanyasParticipantes(idTienda);

        model.addAttribute("tienda", tienda);
        model.addAttribute("tiendasCampanya", tiendasCampanya);
        model.addAttribute("tipoCampanyas", tipoCampanyas);
        model.addAttribute("campanyas", campanyas);
        model.addAttribute("currentSection", "tiendas");

        return "tiendas/asignar_participacion";
    }


    /*
    Este endpoint se ha realizado usando la propia request porque no se ha visto
    en clase cómo hacer un controlador con un número indeterminado de parámetros.

    Esto es así porque el administrador tiene una cantidad de Selects dinámicos
    dependiendo de cuantos tipos de campañas existen, por eso no sabemos de primeras
    cuales van a ser todos los parámetros que recibe el Endpoint.
    */
    @PostMapping("/guardarParticipacion")
    public String guardarParticipacion(@RequestParam("idTienda") Integer idTienda,
                                       HttpServletRequest request) {

        List<TipoCampanya> tipos = this.tipoCampanyaService.buscarTipoCampanyaParticipantes(idTienda);

        for (TipoCampanya tipo : tipos) {
            String valorSeleccionado = request.getParameter("tipo_campanya_" + tipo.getId());

            if (valorSeleccionado != null) {
                Integer idCampanyaSeleccionada = Integer.parseInt(valorSeleccionado);
                this.tiendaCampanyaService.actualizarParticipacion(idTienda, tipo.getId(), idCampanyaSeleccionada);
            }
        }

        return "redirect:/tiendas";
    }

}
