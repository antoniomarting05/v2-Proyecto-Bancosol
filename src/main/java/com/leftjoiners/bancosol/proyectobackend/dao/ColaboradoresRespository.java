/*
Marina Fernández Serrano: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.ColaboradorEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColaboradoresRespository  extends JpaRepository<ColaboradorEntity, Integer> {
    @Query("SELECT c FROM ColaboradorEntity c WHERE " +
            "(:idLocalidad IS NULL OR c.colaboraEn.id = :idLocalidad) AND " +
            "(:idCoordinador IS NULL OR c.coordinador.id = :idCoordinador)"
    )
    List<ColaboradorEntity> filtrarColaboradores(@Param("idLocalidad") Integer idLocalidad,
                                                   @Param("idCoordinador") Integer idCoordinador);
}
