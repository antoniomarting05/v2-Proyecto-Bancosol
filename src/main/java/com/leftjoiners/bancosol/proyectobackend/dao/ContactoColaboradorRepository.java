/*
Marina Fernández Serrano: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.ContactoColaboradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoColaboradorRepository extends JpaRepository<ContactoColaboradorEntity, Integer> {

    @Query("SELECT c FROM ContactoColaboradorEntity c WHERE c.colaborador.id = :id AND c.colaborador.contactoPrincipal = c.nombre")
    ContactoColaboradorEntity buscarContactoPrincipalDe(Integer id);
}
