/*
Javier Urbaneja Benítez: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.AsignacionTurnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsignacionTurnoRepository extends JpaRepository<AsignacionTurnoEntity, Integer> {
    @Query("SELECT a FROM AsignacionTurnoEntity a, TiendaCampanyaEntity tc " +
            "WHERE a.idTiendaCampanya = tc.id AND tc.campanya.tipoCampanya.id = :tipoId")
    List<AsignacionTurnoEntity> findByTipoCampanya(@Param("tipoId") Integer tipoId);

    @Query("SELECT a FROM AsignacionTurnoEntity a, TiendaCampanyaEntity tc " +
            "WHERE a.idTiendaCampanya = tc.id AND tc.campanya.id = :campanyaId")
    List<AsignacionTurnoEntity> findByCampanya(@Param("campanyaId") Integer campanyaId);

    @Query("SELECT a FROM AsignacionTurnoEntity a, TiendaCampanyaEntity tc " +
            "WHERE a.idTiendaCampanya = tc.id AND tc.campanya.tipoCampanya.id = :tipoId AND tc.campanya.id = :campanyaId")
    List<AsignacionTurnoEntity> findByTipoAndCampanya(@Param("tipoId") Integer tipoId, @Param("campanyaId") Integer campanyaId);
}
