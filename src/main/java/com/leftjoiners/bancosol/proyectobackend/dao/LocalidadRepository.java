/*
Javier Urbaneja Benítez: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.dto.Localidad;
import com.leftjoiners.bancosol.proyectobackend.entity.LocalidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalidadRepository extends JpaRepository<LocalidadEntity, Integer> {
}
