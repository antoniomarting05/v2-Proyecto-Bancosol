/*
Javier Urbaneja Benítez 100%
*/

package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.Cadena;
import com.leftjoiners.bancosol.proyectobackend.dto.Campanya;
import com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador;
import com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya;
import com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CampanyaMapper extends MapperDTO<Campanya, CampanyaEntity> {

    @Override
    public Campanya toDTO(CampanyaEntity entity) {
        if (entity == null) return null;

        Campanya dto = new Campanya();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setDuracion(entity.getDuracion());

        if (entity.getTipoCampanya() != null) {
            TipoCampanya tc = new TipoCampanya();
            tc.setId(entity.getTipoCampanya().getId());
            tc.setNombre(entity.getTipoCampanya().getNombre());
            dto.setTipoCampanya(tc);
        }

        if (entity.getCadenasParticipantes() != null) {
            dto.setCadenasParticipantes(entity.getCadenasParticipantes().stream().map(c -> {
                Cadena cad = new Cadena();
                cad.setId(c.getId());
                cad.setCodigo(c.getCodigo());
                cad.setNombre(c.getNombre());
                return cad;
            }).collect(Collectors.toList()));
        }

        if (entity.getTiendasCampanya() != null) {
            dto.setTiendasCampanya(entity.getTiendasCampanya().stream()
                    .map(tc -> tc.getId()).collect(Collectors.toList()));
        }

        return dto;
    }
}