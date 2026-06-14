/*
Marina Fernández Serrano: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador;
import com.leftjoiners.bancosol.proyectobackend.service.ContactoColaboradorService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/contacto")
public class ContactoColaboradorRestController {
    private final ContactoColaboradorService contactoColaboradorService;

    @GetMapping("/buscar/{id}")
    public ContactoColaborador buscarContacto(@PathVariable Integer id) {
        return this.contactoColaboradorService.buscarContacto(id);
    }

    @GetMapping("/contactoPrincipal/{idColaborador}")
    public ContactoColaborador buscarContactoPrincipalDe(@PathVariable Integer idColaborador){
        return this.contactoColaboradorService.buscarContactoPrincipalDe(idColaborador);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactoRequest {
        private Integer id;
        private Integer idColaborador;
        private String nombre;
        private String email;
        private String telefono;
    }

    @PostMapping("/guardar")
    public void guardarContacto(@RequestBody ContactoRequest request) {
        this.contactoColaboradorService.guardarContacto(
                request.getId(),
                request.getIdColaborador(),
                request.getNombre(),
                request.getEmail(),
                request.getTelefono());
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarContacto(@PathVariable Integer id) {
        this.contactoColaboradorService.eliminarContacto(id);
    }
}
