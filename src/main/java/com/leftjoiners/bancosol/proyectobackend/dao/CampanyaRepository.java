/*
Antonio Martín García : 100%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampanyaRepository extends JpaRepository<CampanyaEntity, Integer> {
    @Query("SELECT c FROM CampanyaEntity c WHERE c.tipoCampanya.id = :tipoId AND c.id = (SELECT MAX(c2.id) FROM CampanyaEntity c2 WHERE c2.tipoCampanya.id = :tipoId)")
    CampanyaEntity buscarUltimaCampanyaPorTipo(Integer tipoId);

    @Query("SELECT c FROM CampanyaEntity c WHERE c.tipoCampanya.id = :tipoCampanyaId")
    List<CampanyaEntity> findByTipoCampanya(Integer tipoCampanyaId);

    @Query("SELECT c FROM CampanyaEntity c JOIN c.cadenasParticipantes cp WHERE cp.id = :cadenaId")
    List<CampanyaEntity> findCampanyasParticipantes(@Param("cadenaId") Integer cadenaId);
}
