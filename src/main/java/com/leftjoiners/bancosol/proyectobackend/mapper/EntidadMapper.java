package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.Entidad;
import com.leftjoiners.bancosol.proyectobackend.entity.EntidadEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntidadMapper extends MapperDTO<Entidad, EntidadEntity> {

    @Override
    public Entidad toDTO(EntidadEntity entity) {
        if (entity == null) return null;

        Entidad dto = new Entidad();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());

        if (entity.getUsuarios() != null) {
            dto.setUsuarios(entity.getUsuarios().stream()
                    .map(usuario -> usuario.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
