package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Distrito;
import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import com.leftjoiners.bancosol.proyectobackend.service.DistritoService;
import com.leftjoiners.bancosol.proyectobackend.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioRestController {
    private final UsuarioService usuarioService;

    @GetMapping("/coordinadores")
    public List<Usuario> getCoordinadores() {
        return usuarioService.listarCoordinadores();
    }

    @GetMapping("/capitanes")
    public List<Usuario> getCapitanes() {
        return usuarioService.listarCapitanes();
    }
}
