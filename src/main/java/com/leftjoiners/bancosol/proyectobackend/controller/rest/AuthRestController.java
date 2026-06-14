package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import com.leftjoiners.bancosol.proyectobackend.security.JwtUtil;
import com.leftjoiners.bancosol.proyectobackend.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.autenticar(request.getUsername(), request.getPassword());

        if (usuario != null) {
            String token = jwtUtil.generateToken(usuario.getUsuario(), usuario.getRol(), usuario.getNombre());
            return new JwtResponse(token, usuario);
        } else {
            return new JwtResponse(null, null);
        }
    }

    // DTOs para la comunicación JSON
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class JwtResponse {
        private String token;
        private Usuario usuario;
    }
}