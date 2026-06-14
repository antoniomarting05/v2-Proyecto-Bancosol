/*
Javier Urbaneja Benítez: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.TipoCampanyaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TipoCampanyasRepository extends JpaRepository<TipoCampanyaEntity,Integer> {
    @Query("SELECT DISTINCT tc FROM TipoCampanyaEntity tc JOIN tc.campanyas c JOIN c.cadenasParticipantes cp WHERE cp.id = :id")
    List<TipoCampanyaEntity> findTipoCampanyasParticipantes(@Param("id") Integer id);
}
