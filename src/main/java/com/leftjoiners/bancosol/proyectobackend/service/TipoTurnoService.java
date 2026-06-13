/*
Javier Urbaneja Benítez: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.TipoTurnoRepository;
import com.leftjoiners.bancosol.proyectobackend.dto.TipoTurno;
import com.leftjoiners.bancosol.proyectobackend.entity.TipoTurnoEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TurnoEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.TipoTurnoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipoTurnoService {
    private final TipoTurnoRepository tipoTurnoRepository;
    private final TipoTurnoMapper tipoTurnoMapper;

    public TipoTurno buscarTipoTurno (Integer id) {
        TipoTurnoEntity tipoTurno = this.tipoTurnoRepository.findById(id).orElse(null);
        return this.tipoTurnoMapper.toDTO(tipoTurno);
    }


}
