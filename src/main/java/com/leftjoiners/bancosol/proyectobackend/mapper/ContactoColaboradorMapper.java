/*
Marina Fernández Serrano: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.mapper;

import com.leftjoiners.bancosol.proyectobackend.dto.Colaborador;
import com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador;
import com.leftjoiners.bancosol.proyectobackend.entity.ColaboradorEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.ContactoColaboradorEntity;
import org.springframework.stereotype.Component;

@Component
public class ContactoColaboradorMapper extends MapperDTO<ContactoColaborador, ContactoColaboradorEntity> {

    @Override
    public ContactoColaborador toDTO(ContactoColaboradorEntity entity) {
        if (entity == null) return null;

        ContactoColaborador dto = new ContactoColaborador();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        if(entity.getColaborador() != null) {
            Colaborador colabContacto = new Colaborador();
            colabContacto.setId(entity.getColaborador().getId());
            colabContacto.setNombre(entity.getColaborador().getNombre());
            dto.setColaborador(colabContacto);
            dto.setIdColaborador(colabContacto.getId());
        }

        return dto;
    }
}
