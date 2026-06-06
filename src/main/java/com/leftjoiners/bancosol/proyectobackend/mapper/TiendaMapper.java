/*
Javier Urbaneja Benítez 20%
Daniel Robles Cantos 60%
IA: 20%
*/

package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.*;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class TiendaMapper extends MapperDTO<Tienda, TiendaEntity> {

    @Override
    public Tienda toDTO(TiendaEntity entity) {
        if (entity == null) return null;

        Tienda dto = new Tienda();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setLineales(entity.getLineales());
        dto.setCp(entity.getCp());
        dto.setDomicilio(entity.getDomicilio());

        if (entity.getCadena() != null) {
            Cadena c = new Cadena();
            c.setId(entity.getCadena().getId());
            c.setNombre(entity.getCadena().getNombre());
            dto.setCadena(c);
        }

        if (entity.getLocalidad() != null) {
            Localidad l = new Localidad();
            l.setId(entity.getLocalidad().getId());
            l.setNombre(entity.getLocalidad().getNombre());

            if (entity.getLocalidad().getMunicipio() != null) {
                Municipio m = new Municipio();
                m.setId(entity.getLocalidad().getMunicipio().getId());
                m.setNombre(entity.getLocalidad().getMunicipio().getNombre());

                if (entity.getLocalidad().getMunicipio().getZona() != null) {
                    Zona z = new Zona();
                    z.setId(entity.getLocalidad().getMunicipio().getZona().getId());
                    z.setNombre(entity.getLocalidad().getMunicipio().getZona().getNombre());
                    m.setZona(z);
                }
                l.setMunicipio(m);
            }
            dto.setLocalidad(l);
        }

        if (entity.getDistrito() != null) {
            Distrito d = new Distrito();
            d.setId(entity.getDistrito().getId());
            d.setNombre(entity.getDistrito().getNombre());
            dto.setDistrito(d);
        }

        if (entity.getTiendasCampanya() != null) {
            dto.setTiendasCampanya(entity.getTiendasCampanya().stream().map(tc -> {
                TiendaCampanya tcDTO = new TiendaCampanya();
                tcDTO.setId(tc.getId());

                // campaña y su tipo (Primavera = 2, GR = 1)
                if (tc.getCampanya() != null) {
                    Campanya cDTO = new Campanya();
                    cDTO.setId(tc.getCampanya().getId());
                    if (tc.getCampanya().getTipoCampanya() != null) {
                        TipoCampanya tipoDTO = new TipoCampanya();
                        tipoDTO.setId(tc.getCampanya().getTipoCampanya().getId());
                        cDTO.setTipoCampanya(tipoDTO);
                    }
                    tcDTO.setCampanya(cDTO);
                }

                if (tc.getCoordinador() != null) {
                    Usuario uDTO = new Usuario();
                    uDTO.setId(tc.getCoordinador().getId());
                    uDTO.setNombre(tc.getCoordinador().getNombre());
                    tcDTO.setCoordinador(uDTO);
                }

                if (tc.getCapitan() != null) {
                    Usuario uDTO = new Usuario();
                    uDTO.setId(tc.getCapitan().getId());
                    uDTO.setNombre(tc.getCapitan().getNombre());
                    tcDTO.setCapitan(uDTO);
                }

                return tcDTO;
            }).collect(Collectors.toList()));
        }

        return dto;
    }
}