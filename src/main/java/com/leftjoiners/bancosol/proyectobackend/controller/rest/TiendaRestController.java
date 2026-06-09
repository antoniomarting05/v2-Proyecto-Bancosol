/*
Daniel Robles Cantos 90%
IA: 10%
*/
package com.leftjoiners.bancosol.proyectobackend.controller.rest;

import com.leftjoiners.bancosol.proyectobackend.dto.Tienda;
import com.leftjoiners.bancosol.proyectobackend.dto.TiendaCampanya;
import com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya;
import com.leftjoiners.bancosol.proyectobackend.service.TiendaCampanyaService;
import com.leftjoiners.bancosol.proyectobackend.service.TiendaService;
import com.leftjoiners.bancosol.proyectobackend.service.TipoCampanyaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/tienda")
public class TiendaRestController {

    private final TiendaService tiendaService;
    private final TiendaCampanyaService tiendaCampanyaService;
    private final TipoCampanyaService tipoCampanyaService;

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

    @GetMapping("/asignarParticipacion/{id}")
    public List<TiendaCampanya> asignarParicipacion(@PathVariable Integer id) {
        Tienda tienda = this.tiendaService.buscarTienda(id);

        return this.tiendaCampanyaService.buscarTiendasCampanyaPorTienda(tienda.getId());
    }


    /*
    Este endpoint se ha realizado usando la propia request porque no se ha visto
    en clase cómo hacer un controlador con un número indeterminado de parámetros.

    Esto es así porque el administrador tiene una cantidad de Selects dinámicos
    dependiendo de cuantas Campañas hayan, por eso no sabemos de primeras cuales
    van a ser todos los parámetros.
    */
    @PostMapping("/guardarParticipacion")
    public void guardarParticipacion(@RequestParam("idTienda") Integer idTienda,
                                     HttpServletRequest request) {

        List<TipoCampanya> tipos = this.tipoCampanyaService.listarTipoCampanyas();

        for (TipoCampanya tipo : tipos) {
            String valorSeleccionado = request.getParameter("tipo_campanya_" + tipo.getId());

            if (valorSeleccionado != null) {
                Integer idCampanyaSeleccionada = Integer.parseInt(valorSeleccionado);
                this.tiendaCampanyaService.actualizarParticipacion(idTienda, tipo.getId(), idCampanyaSeleccionada);
            }
        }
    }
}