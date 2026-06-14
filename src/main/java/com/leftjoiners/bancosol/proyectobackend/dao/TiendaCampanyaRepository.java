/*
Javier Urbaneja Benítez: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.TiendaCampanyaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TiendaCampanyaRepository extends JpaRepository<TiendaCampanyaEntity, Integer> {
    @Query("SELECT tc FROM TiendaCampanyaEntity tc WHERE tc.tienda.id = :idTienda")
    List<TiendaCampanyaEntity> buscarTodosPorIdTienda(@Param("idTienda") Integer idTienda);
}
