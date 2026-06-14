/*
Javier Urbaneja Benítez 100%
*/

package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.Cadena;
import com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CadenaMapper extends MapperDTO<Cadena, CadenaEntity> {

    @Override
    public Cadena toDTO(CadenaEntity entity) {
        if (entity == null) return null;

        Cadena dto = new Cadena();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setNombre(entity.getNombre());

        if (entity.getTiendas() != null) {
            dto.setTiendas(entity.getTiendas().stream().map(t -> t.getId()).collect(Collectors.toList()));
        }
        if (entity.getCampanyas() != null) {
            dto.setCampanyas(entity.getCampanyas().stream().map(c -> c.getId()).collect(Collectors.toList()));
        }

        return dto;
    }
}