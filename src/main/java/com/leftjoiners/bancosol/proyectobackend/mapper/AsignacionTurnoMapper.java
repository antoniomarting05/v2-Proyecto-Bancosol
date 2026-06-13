/*
Javier Urbaneja Benítez 100%
*/

package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.AsignacionTurno;
import com.leftjoiners.bancosol.proyectobackend.entity.AsignacionTurnoEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AsignacionTurnoMapper extends MapperDTO<AsignacionTurno, AsignacionTurnoEntity> {

    @Override
    public AsignacionTurno toDTO(AsignacionTurnoEntity entity) {
        if (entity == null) return null;

        AsignacionTurno dto = new AsignacionTurno();
        dto.setIdTiendaCampanya(entity.getIdTiendaCampanya());
        dto.setTienda(entity.getTienda());
        dto.setDomicilio(entity.getDomicilio());
        dto.setLocalidad(entity.getLocalidad());
        dto.setCapitan(entity.getCapitan());
        dto.setLineales(entity.getLineales());
        dto.setViernesManana(entity.getViernesManana());
        dto.setViernesTarde(entity.getViernesTarde());
        dto.setSabadoManana(entity.getSabadoManana());
        dto.setSabadoTarde(entity.getSabadoTarde());

        return dto;
    }
}
