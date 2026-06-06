/*
Daniel Robles Cantos 90%
IA: 10%
*/
package com.leftjoiners.bancosol.proyectobackend.controller;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.Tienda;
import com.leftjoiners.bancosol.proyectobackend.entity.*;
import com.leftjoiners.bancosol.proyectobackend.service.*;
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
    private final CadenaService cadenaService;
    private final ZonaService zonaService;
    private final LocalidadService localidadService;
    private final MunicipioService municipioService;
    private final DistritoService distritoService;
    private final UsuarioService usuarioService;

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
            @RequestParam(value = "coordinadorPrimaveraId", required = false) Integer coordinadorPrimaveraId,
            @RequestParam(value = "coordinadorGRId", required = false) Integer coordinadorGRId,
            @RequestParam(value = "capitanId", required = false) Integer capitanId) {

        this.tiendaService.guardarTienda(id, nombre, lineales, domicilio, codigoPostal, distritoId,
                cadenaId, localidadId, coordinadorPrimaveraId, coordinadorGRId, capitanId);

        return "redirect:/tiendas";
    }


    @GetMapping("/verTienda")
    public String doVerTienda(Model model, @RequestParam("id") Integer idTienda) {
        Tienda tienda = this.tiendaService.buscarTienda(idTienda);

        model.addAttribute("tiendaActual", tienda);
        model.addAttribute("editando", false);
        model.addAttribute("viendo", true);
        model.addAttribute("currentSection", "tiendas");

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
}
