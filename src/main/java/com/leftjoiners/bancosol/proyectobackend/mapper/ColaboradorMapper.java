/*
Javier Urbaneja Benítez 80%
Marina Fernández Serrano 20%
*/
package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.Colaborador;
import com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador;
import com.leftjoiners.bancosol.proyectobackend.dto.Localidad;
import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import com.leftjoiners.bancosol.proyectobackend.entity.ColaboradorEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ColaboradorMapper extends MapperDTO<Colaborador, ColaboradorEntity> {

    @Override
    public Colaborador toDTO(ColaboradorEntity entity) {
        if (entity == null) return null;

        Colaborador dto = new Colaborador();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setNombre(entity.getNombre());
        dto.setContactoPrincipal(entity.getContactoPrincipal());
        dto.setDomicilio(entity.getDomicilio());
        dto.setCp(entity.getCp());
        dto.setTemporal(entity.getTemporal());
        dto.setObservaciones(entity.getObservaciones());

        if (entity.getLocalidadSede() != null) {
            Localidad locSede = new Localidad();
            locSede.setId(entity.getLocalidadSede().getId());
            locSede.setNombre(entity.getLocalidadSede().getNombre());
            dto.setLocalidadSede(locSede);
        }

        if (entity.getColaboraEn() != null) {
            Localidad locColab = new Localidad();
            locColab.setId(entity.getColaboraEn().getId());
            locColab.setNombre(entity.getColaboraEn().getNombre());
            dto.setColaboraEn(locColab);
        }

        if (entity.getCoordinador() != null) {
            Usuario u = new Usuario();
            u.setId(entity.getCoordinador().getId());
            u.setNombre(entity.getCoordinador().getNombre());
            dto.setCoordinador(u);
        }

        if (entity.getContactos() != null) {
            dto.setContactos(entity.getContactos().stream().map(c -> {
                ContactoColaborador cc = new ContactoColaborador();
                cc.setId(c.getId());
                cc.setIdColaborador(entity.getId());
                cc.setNombre(c.getNombre());
                cc.setEmail(c.getEmail());
                cc.setTelefono(c.getTelefono());
                cc.setEsPrincipal(c.getEsPrincipal());
                return cc;
            }).collect(Collectors.toList()));
        }

        if (entity.getTurnos() != null) {
            dto.setTurnos(entity.getTurnos().stream().map(t -> t.getId()).collect(Collectors.toList()));
        }

        return dto;
    }
}