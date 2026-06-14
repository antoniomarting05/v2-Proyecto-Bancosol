/*
Daniel Robles Cantos: 50%
Javier Urbaneja Benítez: 50%
*/
package com.leftjoiners.bancosol.proyectobackend.dao;

import com.leftjoiners.bancosol.proyectobackend.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    @Query("SELECT u FROM UsuarioEntity u WHERE u.usuario LIKE :user AND u.contrasenya LIKE :password")
    UsuarioEntity autenticar(@Param("user") String user, @Param("password") String password);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.rol.id = 2")
    List<UsuarioEntity> findCoordinadores();

    @Query("SELECT u FROM UsuarioEntity u WHERE u.rol.id = 3")
    List<UsuarioEntity> findCapitanes();
}
