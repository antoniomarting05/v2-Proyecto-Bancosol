package com.leftjoiners.bancosol.proyectobackend.controller.rest;


import com.leftjoiners.bancosol.proyectobackend.dto.TipoTurno;
import com.leftjoiners.bancosol.proyectobackend.service.TipoTurnoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tipoTurno")
public class TipoTurnoRestController {
    private final TipoTurnoService tipoTurnoService;

    @GetMapping("/{idTurno}")
    public TipoTurno buscarTurno(@PathVariable Integer idTurno) {
        return this.tipoTurnoService.buscarTipoTurno(idTurno);
    }

}
