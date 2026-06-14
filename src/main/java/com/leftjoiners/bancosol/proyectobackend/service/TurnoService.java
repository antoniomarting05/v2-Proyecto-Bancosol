/*
Javier Urbaneja Benítez: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.ColaboradoresRespository;
import com.leftjoiners.bancosol.proyectobackend.dao.TiendaCampanyaRepository;
import com.leftjoiners.bancosol.proyectobackend.dao.TipoTurnoRepository;
import com.leftjoiners.bancosol.proyectobackend.dao.TurnoRepository;
import com.leftjoiners.bancosol.proyectobackend.dto.Turno;
import com.leftjoiners.bancosol.proyectobackend.entity.TurnoEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.TurnoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TurnoService {
    private final TurnoRepository turnoRepository;
    private final TiendaCampanyaRepository tiendaCampanyaRepository;
    private final TipoTurnoRepository tipoTurnoRepository;
    private final ColaboradoresRespository colaboradoresRespository;
    private final TurnoMapper turnoMapper;

    public List<Turno> listarTurnos () {
        List<TurnoEntity> turnos = this.turnoRepository.findAll();
        return this.turnoMapper.toDTOList(turnos);
    }

    public Turno buscarTurnoEspecifico (Integer idTienda, Integer turno, Integer linealActual) {
        TurnoEntity asignacionTurno = this.turnoRepository.buscarTurnoEspecifico(idTienda, turno, linealActual);
        return this.turnoMapper.toDTO(asignacionTurno);
    }

    public void guardarTurno(Integer id, Integer tiendaCampanyaId, Integer tipoTurnoId, Integer lineal,
                             Integer idColaborador, LocalTime horaInicio, LocalTime horaFin,
                             Integer numVoluntarios, String observaciones) {
        TurnoEntity turnoEntity;

        if (id != null) {
            turnoEntity = this.turnoRepository.findById(id).orElse(new TurnoEntity());
        } else {
            turnoEntity = new TurnoEntity();
        }

        turnoEntity.setTiendaCampanya(this.tiendaCampanyaRepository.findById(tiendaCampanyaId).orElse(null));
        turnoEntity.setTipoTurno(this.tipoTurnoRepository.findById(tipoTurnoId).orElse(null));

        if (idColaborador != null) {
            turnoEntity.setColaborador(this.colaboradoresRespository.findById(idColaborador).orElse(null));
        } else {
            turnoEntity.setColaborador(null);
        }

        turnoEntity.setLineal(lineal);
        turnoEntity.setHoraInicio(horaInicio);
        turnoEntity.setHoraFin(horaFin);
        turnoEntity.setNumVoluntarios(numVoluntarios);
        turnoEntity.setObservaciones(observaciones);

        this.turnoRepository.save(turnoEntity);
    }
}
