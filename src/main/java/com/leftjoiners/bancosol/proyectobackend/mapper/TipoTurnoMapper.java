/*
Javier Urbaneja Benítez 100%
*/

package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.TipoTurno;
import com.leftjoiners.bancosol.proyectobackend.entity.TipoTurnoEntity;
import org.springframework.stereotype.Component;

@Component
public class TipoTurnoMapper extends MapperDTO<TipoTurno, TipoTurnoEntity> {

    @Override
    public TipoTurno toDTO(TipoTurnoEntity entity) {
        if (entity == null) return null;

        TipoTurno dto = new TipoTurno();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());

        return dto;
    }
}