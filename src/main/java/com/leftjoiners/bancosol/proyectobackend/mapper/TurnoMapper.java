/*
Javier Urbaneja Benítez 100%
*/

package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.*;
import com.leftjoiners.bancosol.proyectobackend.entity.TurnoEntity;
import org.springframework.stereotype.Component;

@Component
public class TurnoMapper extends MapperDTO<Turno, TurnoEntity> {

    @Override
    public Turno toDTO(TurnoEntity entity) {
        if (entity == null) return null;

        Turno dto = new Turno();
        dto.setId(entity.getId());
        dto.setHoraInicio(entity.getHoraInicio());
        dto.setHoraFin(entity.getHoraFin());
        dto.setLineal(entity.getLineal());
        dto.setNumVoluntarios(entity.getNumVoluntarios());
        dto.setObservaciones(entity.getObservaciones());
        dto.setDia(entity.getDia());

        if (entity.getTiendaCampanya() != null) {
            TiendaCampanya tc = new TiendaCampanya();
            tc.setId(entity.getTiendaCampanya().getId());

            if (entity.getTiendaCampanya().getTienda() != null) {
                Tienda t = new Tienda();
                t.setId(entity.getTiendaCampanya().getTienda().getId());
                t.setNombre(entity.getTiendaCampanya().getTienda().getNombre());
                t.setDomicilio(entity.getTiendaCampanya().getTienda().getDomicilio());
                tc.setTienda(t);
            }

            dto.setTiendaCampanya(tc);
        }

        if (entity.getColaborador() != null) {
            Colaborador c = new Colaborador();
            c.setId(entity.getColaborador().getId());
            c.setNombre(entity.getColaborador().getNombre());

            dto.setColaborador(c);
        }

        if (entity.getTipoTurno() != null) {
            TipoTurno tt = new TipoTurno();
            tt.setId(entity.getTipoTurno().getId());
            tt.setNombre(entity.getTipoTurno().getNombre());
            dto.setTipoTurno(tt);
        }

        return dto;
    }
}