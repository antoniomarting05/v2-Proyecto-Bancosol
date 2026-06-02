package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CampanyaRepository extends JpaRepository<CampanyaEntity, Integer> {
    @Query("SELECT c FROM CampanyaEntity c WHERE c.tipoCampanya.id = :tipoId AND c.id = (SELECT MAX(c2.id) FROM CampanyaEntity c2 WHERE c2.tipoCampanya.id = :tipoId)")
    CampanyaEntity buscarUltimaCampanyaPorTipo(Integer tipoId);

}
