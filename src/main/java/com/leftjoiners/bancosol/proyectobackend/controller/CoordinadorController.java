package com.leftjoiners.bancosol.proyectobackend.controller;

import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import com.leftjoiners.bancosol.proyectobackend.service.UsuarioService;
import com.leftjoiners.bancosol.proyectobackend.service.ZonaService;
import com.leftjoiners.bancosol.proyectobackend.service.EntidadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/coordinadores")
@Controller
@AllArgsConstructor
public class CoordinadorController {

    private final UsuarioService usuarioService;
    private final EntidadService entidadService;
    private final ZonaService zonaService;

    @GetMapping("")
    public String listarCoordinadores(Model model) {
        model.addAttribute("coordinadores", this.usuarioService.listarCoordinadores());
        model.addAttribute("currentSection", "coordinadores");
        model.addAttribute("eliminar", false);
        return "coordinadores/coordinador";
    }

    @GetMapping("/crearCoordinador")
    public String crearCoordinador(Model model) {
        model.addAttribute("entidades", this.entidadService.listarEntidades());
        model.addAttribute("zonas", this.zonaService.listarZonas());

        model.addAttribute("editando", false);
        model.addAttribute("currentSection", "coordinadores");

        return "coordinadores/formularioCoordinador";
    }

    @PostMapping("/guardarCoordinador")
    public String guardarCoordinador(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam("usuario") String usuario,
            @RequestParam(value = "contrasenya", required = false) String contrasenya,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "idEntidad", required = false) Integer idEntidad,
            @RequestParam(value = "idZona", required = false) Integer idZona
    ) {
        this.usuarioService.guardarCoordinador(
                id,
                nombre,
                usuario,
                contrasenya,
                telefono,
                email,
                null,
                idEntidad,
                idZona,
                null,
                null
        );

        return "redirect:/coordinadores";
    }

    @GetMapping("/editarCoordinador")
    public String editarCoordinador(@RequestParam("id") Integer id, Model model) {
        Usuario coordinador = this.usuarioService.buscarUsuario(id);

        if (coordinador == null) {
            return "redirect:/coordinadores";
        }

        // Valores coordinador actual
        model.addAttribute("idCoordinador", coordinador.getId());
        model.addAttribute("nombreCoordinador", coordinador.getNombre());
        model.addAttribute("usuarioCoordinador", coordinador.getUsuario());
        model.addAttribute("telefonoCoordinador", coordinador.getTelefono());
        model.addAttribute("emailCoordinador", coordinador.getEmail());
        model.addAttribute("idEntidadActual", coordinador.getIdEntidad());
        model.addAttribute("idZonaActual", coordinador.getIdZonaAsignada());

        model.addAttribute("editando", true);

        model.addAttribute("entidades", this.entidadService.listarEntidades());
        model.addAttribute("zonas", this.zonaService.listarZonas());

        model.addAttribute("currentSection", "coordinadores");

        return "coordinadores/formularioCoordinador";
    }

    @GetMapping("/seleccionCoordinadoresEliminar")
    public String seleccionarCoordinadoresEliminar(Model model) {
        model.addAttribute("coordinadores", this.usuarioService.listarCoordinadores());
        model.addAttribute("currentSection", "coordinadores");
        model.addAttribute("eliminar", true);

        return "coordinadores/coordinador";
    }

    @PostMapping("/eliminarCoordinadores")
    public String eliminarCoordinadores(
            Model model,
            @RequestParam(required = false, value = "coordinadoresElim") List<Integer> idCoordinadoresEliminar
    ) {
        if (idCoordinadoresEliminar != null) {
            this.usuarioService.eliminarUsuarios(idCoordinadoresEliminar);
        } else {
            return "redirect:/coordinadores";
        }

        model.addAttribute("eliminar", false);
        return "redirect:/coordinadores";
    }
}
