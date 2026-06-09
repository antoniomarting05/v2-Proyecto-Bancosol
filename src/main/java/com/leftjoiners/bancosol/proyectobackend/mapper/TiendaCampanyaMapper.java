/*
Javier Urbaneja Benítez 80%
IA: 20%
*/

package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.*;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaCampanyaEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class TiendaCampanyaMapper extends MapperDTO<TiendaCampanya, TiendaCampanyaEntity> {

    @Override
    public TiendaCampanya toDTO(TiendaCampanyaEntity entity) {
        if (entity == null) return null;

        TiendaCampanya dto = new TiendaCampanya();
        dto.setId(entity.getId());

        if (entity.getTienda() != null) {
            Tienda t = new Tienda();
            t.setId(entity.getTienda().getId());
            t.setNombre(entity.getTienda().getNombre());
            t.setDomicilio(entity.getTienda().getDomicilio());
            t.setCp(entity.getTienda().getCp());
            t.setLineales(entity.getTienda().getLineales());
            dto.setTienda(t);
        }

        if (entity.getCampanya() != null) {
            Campanya c = new Campanya();
            c.setId(entity.getCampanya().getId());
            c.setNombre(entity.getCampanya().getNombre());
            if (entity.getCampanya().getTipoCampanya() != null) {
                TipoCampanya tipoCampanya = new TipoCampanya();
                tipoCampanya.setId(entity.getCampanya().getTipoCampanya().getId());
                tipoCampanya.setNombre(entity.getCampanya().getTipoCampanya().getNombre());
                c.setTipoCampanya(tipoCampanya);
            }
            dto.setCampanya(c);
        }

        if (entity.getCoordinador() != null) {
            Usuario u = new Usuario();
            u.setId(entity.getCoordinador().getId());
            u.setNombre(entity.getCoordinador().getNombre());
            dto.setCoordinador(u);
        }

        if (entity.getTurnos() != null) {
            dto.setTurnos(entity.getTurnos().stream().map(t -> t.getId()).collect(Collectors.toList()));
        }

        return dto;
    }
}