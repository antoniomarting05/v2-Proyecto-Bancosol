/*
Daniel Robles Cantos 90%
IA: 10%
*/
package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Tienda;
import com.leftjoiners.bancosol.proyectobackend.service.TiendaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/tienda")
public class TiendaRestController {

    private final TiendaService tiendaService;

    @GetMapping("/")
    public List<Tienda> doInit() {
        return this.tiendaService.listarTiendas();
    }

    @GetMapping("/{id}")
    public Tienda buscarTienda(@PathVariable Integer id) {
        return this.tiendaService.buscarTienda(id);
    }

    @GetMapping("/filtrar")
    public List<Tienda> filtrarTiendas(
            @RequestParam(required = false) Integer cadenaId,
            @RequestParam(required = false) Integer localidadId,
            @RequestParam(required = false) Integer zonaId) {

        return this.tiendaService.filtrarTiendas(cadenaId, localidadId, zonaId);
    }

    public record TiendaRequest(
            Integer id,
            String nombre,
            Integer lineales,
            String domicilio,
            String codigoPostal,
            Integer distritoId,
            Integer cadenaId,
            Integer localidadId,
            Integer coordinadorPrimaveraId,
            Integer coordinadorGRId,
            Integer capitanId
    ) {}

    @PostMapping("/guardar")
    public void guardarTienda(@RequestBody TiendaRequest request) {
        this.tiendaService.guardarTienda(
                request.id(),
                request.nombre(),
                request.lineales(),
                request.domicilio(),
                request.codigoPostal(),
                request.distritoId(),
                request.cadenaId(),
                request.localidadId(),
                request.coordinadorPrimaveraId(),
                request.coordinadorGRId(),
                request.capitanId()
        );
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarTienda(@PathVariable Integer id) {
        if (id != null) {
            this.tiendaService.eliminarTienda(id);
        }
    }
}