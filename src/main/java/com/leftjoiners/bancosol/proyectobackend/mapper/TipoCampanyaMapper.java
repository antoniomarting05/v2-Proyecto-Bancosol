/*
Javier Urbaneja Benítez 100%
*/

package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya;
import com.leftjoiners.bancosol.proyectobackend.entity.TipoCampanyaEntity;
import org.springframework.stereotype.Component;

@Component
public class TipoCampanyaMapper extends MapperDTO<TipoCampanya, TipoCampanyaEntity> {

    @Override
    public TipoCampanya toDTO(TipoCampanyaEntity entity) {
        if (entity == null) return null;

        TipoCampanya dto = new TipoCampanya();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());

        return dto;
    }
}