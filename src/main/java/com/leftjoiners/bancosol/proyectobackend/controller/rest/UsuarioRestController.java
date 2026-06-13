package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import com.leftjoiners.bancosol.proyectobackend.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioRestController {
    private final UsuarioService usuarioService;

    public record CoordinadorRequest(
            Integer id,
            String nombre,
            String usuario,
            String contrasenya,
            String telefono,
            String email,
            Integer idEntidad,
            Integer idZona
    ) {}

    @GetMapping("/coordinadores")
    public List<Usuario> getCoordinadores() {
        return usuarioService.listarCoordinadores();
    }

    @GetMapping("/coordinadores/{id}")
    public Usuario getCoordinador(@PathVariable Integer id) {
        return usuarioService.buscarUsuario(id);
    }

    @PostMapping("/coordinadores/guardar")
    public void guardarCoordinador(@RequestBody CoordinadorRequest request) {
        usuarioService.guardarCoordinador(
                request.id(),
                request.nombre(),
                request.usuario(),
                request.contrasenya(),
                request.telefono(),
                request.email(),
                null,
                request.idEntidad(),
                request.idZona(),
                null,
                null
        );
    }

    @DeleteMapping("/coordinadores/{id}")
    public void eliminarCoordinador(@PathVariable Integer id) {
        if (id != null) {
            usuarioService.eliminarUsuarios(List.of(id));
        }
    }

    @DeleteMapping("/coordinadores")
    public void eliminarCoordinadores(@RequestBody List<Integer> ids) {
        usuarioService.eliminarUsuarios(ids);
    }

    @GetMapping("/capitanes")
    public List<Usuario> getCapitanes() {
        return usuarioService.listarCapitanes();
    }
}
