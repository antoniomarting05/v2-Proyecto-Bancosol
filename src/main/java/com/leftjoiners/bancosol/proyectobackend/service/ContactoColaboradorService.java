/*
Marina Fernández Serrano: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.ColaboradoresRespository;
import com.leftjoiners.bancosol.proyectobackend.dao.ContactoColaboradorRepository;
import com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador;
import com.leftjoiners.bancosol.proyectobackend.entity.ColaboradorEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.ContactoColaboradorEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.ContactoColaboradorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ContactoColaboradorService {
    private final ContactoColaboradorRepository contactoColaboradorRepository;
    private final ColaboradoresRespository colaboradoresRespository;
    private final ContactoColaboradorMapper contactoColaboradorMapper;

    public ContactoColaborador buscarContacto(Integer id) {
        ContactoColaboradorEntity contactoColaborador = this.contactoColaboradorRepository.findById(id).orElse(null);
        return this.contactoColaboradorMapper.toDTO(contactoColaborador);
    }

    @Transactional
    public void guardarContacto(Integer id, Integer idColaborador, String nombre, String email, String telefono) {
        ContactoColaboradorEntity contactoColaborador;

        if(id!= null) {
            contactoColaborador = this.contactoColaboradorRepository.findById(id).orElse(new ContactoColaboradorEntity());
        } else {
            contactoColaborador = new ContactoColaboradorEntity();
        }

        ColaboradorEntity colaborador = this.colaboradoresRespository.findById(idColaborador).get();

        contactoColaborador.setColaborador(colaborador);
        contactoColaborador.setNombre(nombre);
        contactoColaborador.setEmail(email);
        contactoColaborador.setTelefono(telefono);

        this.contactoColaboradorRepository.save(contactoColaborador);
    }

    public ContactoColaborador buscarContactoPrincipalDe(Integer id) {
        ContactoColaboradorEntity contacto = this.contactoColaboradorRepository.buscarContactoPrincipalDe(id);
        return this.contactoColaboradorMapper.toDTO(contacto);

    }

    public void eliminarContacto(Integer id) {
        if (id != null){
            this.contactoColaboradorRepository.deleteById(id);
        }
    }

}
