package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.*;
import com.leftjoiners.bancosol.proyectobackend.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UsuarioMapper extends MapperDTO<Usuario, UsuarioEntity> {
    @Override
    public Usuario toDTO(UsuarioEntity entity) {
        if (entity == null) return null;

        Usuario dto = new Usuario();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setCp(entity.getCp());
        //dto.setContrasenya(entity.getContrasenya());
        dto.setUsuario(entity.getUsuario());            //SIN ESTO EN REACT NO IBA
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        if (entity.getRol() != null) {
            dto.setRol(entity.getRol().getNombre());
        }
        if (entity.getEntidad() != null) {
            dto.setIdEntidad(entity.getEntidad().getId());
            dto.setEntidad(entity.getEntidad().getNombre());
        }
        if (entity.getZonaAsignada() != null) {
            dto.setIdZonaAsignada(entity.getZonaAsignada().getId());
            dto.setZonaAsignada(entity.getZonaAsignada().getNombre());
        }
        if (entity.getDistrito() != null) {
            dto.setIdDistrito(entity.getDistrito().getId());
            dto.setDistrito(entity.getDistrito().getNombre());
        }
        if (entity.getLocalidad() != null) {
            dto.setIdLocalidad(entity.getLocalidad().getId());
            dto.setLocalidad(entity.getLocalidad().getNombre());
        }
        if (entity.getTiendasCoordinadas() != null) {
            dto.setTiendasCoordinadas(entity.getTiendasCoordinadas().stream()
                    .map(tiendaCampanya -> tiendaCampanya.getId())
                    .collect(Collectors.toList()));
        }
        if (entity.getTiendasCapitaneadas() != null) {
            dto.setTiendasCapitaneadas(entity.getTiendasCapitaneadas().stream()
                    .map(tienda -> tienda.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
