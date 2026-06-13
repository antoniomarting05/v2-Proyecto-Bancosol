package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<RolEntity, Integer> {
}