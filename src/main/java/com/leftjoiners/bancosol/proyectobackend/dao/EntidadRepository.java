package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.EntidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntidadRepository extends JpaRepository<EntidadEntity, Integer> {
}