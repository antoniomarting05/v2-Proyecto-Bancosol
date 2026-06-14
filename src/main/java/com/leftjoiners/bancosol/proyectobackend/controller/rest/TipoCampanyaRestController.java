package com.leftjoiners.bancosol.proyectobackend.controller.rest;


import com.leftjoiners.bancosol.proyectobackend.dto.Campanya;
import com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya;
import com.leftjoiners.bancosol.proyectobackend.service.TipoCampanyaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tipoCampanyas")
public class TipoCampanyaRestController {
    private final TipoCampanyaService tipoCampanyaService;

    @GetMapping("/")
    public List<TipoCampanya> doInit(){
        return this.tipoCampanyaService.listarTipoCampanyas();
    }

    @GetMapping("/participantes/{idTienda}")
    public List<TipoCampanya> getTipoCampanyasParticipantes(@PathVariable Integer idTienda) {
        return this.tipoCampanyaService.buscarTipoCampanyaParticipantes(idTienda);
    }
}
