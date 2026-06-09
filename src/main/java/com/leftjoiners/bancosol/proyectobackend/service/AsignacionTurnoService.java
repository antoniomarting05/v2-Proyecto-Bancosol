/*
Javier Urbaneja Benítez: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.AsignacionTurno;
import com.leftjoiners.bancosol.proyectobackend.entity.AsignacionTurnoEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.AsignacionTurnoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AsignacionTurnoService {
    private final AsignacionTurnoRepository asignacionTurnoRepository;
    private final AsignacionTurnoMapper asignacionTurnoMapper;

    public List<AsignacionTurno> listarAsignacionColaboradores () {
        List<AsignacionTurnoEntity> asignacionTurnos = asignacionTurnoRepository.findAll();
        return asignacionTurnoMapper.toDTOList(asignacionTurnos);
    }

    public List<AsignacionTurno> filtrarPorTipoyCampanya(Integer tipoCampanyaId, Integer campanyaId) {
        List<AsignacionTurnoEntity> asignacionTurnos = new ArrayList<>();
        if (tipoCampanyaId == 0 && campanyaId == 0) {
            asignacionTurnos = asignacionTurnoRepository.findAll();
        } else if (tipoCampanyaId != 0 && campanyaId == 0) {
            asignacionTurnos = asignacionTurnoRepository.findByTipoCampanya(tipoCampanyaId);
        } else if (tipoCampanyaId == 0 && campanyaId != 0) {
            asignacionTurnos = asignacionTurnoRepository.findByCampanya(campanyaId);
        } else {
            asignacionTurnos = asignacionTurnoRepository.findByTipoAndCampanya(tipoCampanyaId, campanyaId);
        }

        return this.asignacionTurnoMapper.toDTOList(asignacionTurnos);
    }
}
