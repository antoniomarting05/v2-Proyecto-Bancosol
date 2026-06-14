/*
Antonio Martín García : 100%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TipoCampanyaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CadenaRepository extends JpaRepository<CadenaEntity,Integer> {
    Integer id(Integer id);
}
