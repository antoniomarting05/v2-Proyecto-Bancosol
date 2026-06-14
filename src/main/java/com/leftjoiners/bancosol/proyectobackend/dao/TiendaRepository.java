/*
Daniel Robles Cantos: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TiendaRepository extends JpaRepository<TiendaEntity, Integer> {

    @Query("SELECT t FROM TiendaEntity t WHERE " +
            "(:cadenaId IS NULL OR t.cadena.id = :cadenaId) AND " +
            "(:localidadId IS NULL OR t.localidad.id = :localidadId) AND "
            + "(:zonaId IS NULL OR t.localidad.municipio.zona.id = :zonaId)"
            )
    List<TiendaEntity> filtrarTiendasMulticriterio(@Param("cadenaId") Integer cadenaId,
                                                   @Param("localidadId") Integer localidadId,
                                                   @Param("zonaId") Integer zonaId);
}
