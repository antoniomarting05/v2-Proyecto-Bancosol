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
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tienda")
public class TiendaRestController {
    private final TiendaService tiendaService;
    private final TiendaCampanyaService tiendaCampanyaService;
    private final TipoCampanyaService tipoCampanyaService;

    // Parte de visualización y edición de tiendas: Daniel Robles Cantos

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TiendaRequest {
        private Integer id;
        private String nombre;
        private Integer lineales;
        private String domicilio;
        private String codigoPostal;
        private Integer distritoId;
        private Integer cadenaId;
        private Integer localidadId;
        private Integer coordinadorPrimaveraId;
        private Integer coordinadorGRId;
        private Integer capitanId;
        private List<Integer> campanyaIds;
        private List<Integer> coordinadorIds;
    }

    @PostMapping("/guardar")
    public void guardarTienda(@RequestBody TiendaRequest request) {
        this.tiendaService.guardarTienda(
                request.getId(),
                request.getNombre(),
                request.getLineales(),
                request.getDomicilio(),
                request.getCodigoPostal(),
                request.getDistritoId(),
                request.getCadenaId(),
                request.getLocalidadId(),
                request.getCapitanId()
        );

        if (request.getId() != null && request.getCampanyaIds() != null) {
            for (int i = 0; i < request.getCampanyaIds().size(); i++) {
                Integer cId = request.getCampanyaIds().get(i);

                // Extraemos el coordinador correspondiente, controlando que la lista no sea nula y tenga el índice
                Integer uId = (request.getCoordinadorIds() != null && i < request.getCoordinadorIds().size())
                        ? request.getCoordinadorIds().get(i) : null;

                this.tiendaService.actualizarCoordinadorEnCampanya(request.getId(), cId, uId);
            }
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarTienda(@PathVariable Integer id) {
        if (id != null) {
            this.tiendaService.eliminarTienda(id);
        }
    }


    // Parte de Asignación de Participación: Javier Urbaneja Benítez

    @GetMapping("/asignarParticipacion/{id}")
    public List<TiendaCampanya> asignarParicipacion(@PathVariable Integer id) {
        Tienda tienda = this.tiendaService.buscarTienda(id);

        return this.tiendaCampanyaService.buscarTiendasCampanyaPorTienda(tienda.getId());
    }


    /*
    Este endpoint se ha realizado usando la propia request porque no se ha visto
    en clase cómo hacer un controlador con un número indeterminado de parámetros.

    Esto es así porque el administrador tiene una cantidad de Selects dinámicos
    dependiendo de cuantos tipos de campañas existen, por eso no sabemos de primeras
    cuales van a ser todos los parámetros que recibe el Endpoint.
    */
    @PostMapping("/guardarParticipacion")
    public void guardarParticipacion(@RequestParam("idTienda") Integer idTienda,
                                     HttpServletRequest request) {

        List<TipoCampanya> tipos = this.tipoCampanyaService.buscarTipoCampanyaParticipantes(idTienda);

        for (TipoCampanya tipo : tipos) {
            String valorSeleccionado = request.getParameter("tipo_campanya_" + tipo.getId());

            if (valorSeleccionado != null) {
                Integer idCampanyaSeleccionada = Integer.parseInt(valorSeleccionado);
                this.tiendaCampanyaService.actualizarParticipacion(idTienda, tipo.getId(), idCampanyaSeleccionada);
            }
        }
    }
}