/*
Javier Urbaneja Benítez 10%
*/
package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import com.leftjoiners.bancosol.proyectobackend.entity.*;
import com.leftjoiners.bancosol.proyectobackend.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private static final Integer idRolCoordinador = 2;
    private static final Integer idRolCapitan = 3;

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final EntidadRepository entidadRepository;
    private final ZonaRepository zonaRepository;
    private final LocalidadRepository localidadRepository;
    private final DistritoRepository distritoRepository;

    private final ColaboradoresRespository colaboradoresRespository;
    private final TiendaCampanyaRepository tiendaCampanyaRepository;
    private final TiendaRepository tiendaRepository;

    private final UsuarioMapper usuarioMapper;

    public Usuario autenticar(String user, String password) {
        UsuarioEntity usuario = this.usuarioRepository.autenticar(user, password);
        return this.usuarioMapper.toDTO(usuario);
    }

    public List<Usuario> listarCoordinadores() {
        List<UsuarioEntity> coordinadores = this.usuarioRepository.findCoordinadores();
        return this.usuarioMapper.toDTOList(coordinadores);
    }

    public List<Usuario> listarCapitanes() {
        List<UsuarioEntity> capitanes = this.usuarioRepository.findCapitanes();
        return this.usuarioMapper.toDTOList(capitanes);
    }

    public Usuario buscarUsuario(Integer id) {
        UsuarioEntity usuario = this.usuarioRepository.findById(id).get();
        return this.usuarioMapper.toDTO(usuario);
    }

    public void guardarCoordinador(Integer id,
                                   String nombre,
                                   String usuario,
                                   String contrasenya,
                                   String telefono,
                                   String email,
                                   Integer cp,
                                   Integer idEntidad,
                                   Integer idZona,
                                   Integer idLocalidad,
                                   Integer idDistrito) {

        UsuarioEntity usuarioEntity;

        if (id != null) {
            usuarioEntity = this.usuarioRepository.findById(id).get();
        } else {
            usuarioEntity = new UsuarioEntity();
        }

        usuarioEntity.setNombre(nombre);
        usuarioEntity.setUsuario(usuario);
        usuarioEntity.setTelefono(telefono);
        usuarioEntity.setEmail(email);
        usuarioEntity.setCp(cp);

        if (contrasenya != null && !contrasenya.isBlank()) {
            usuarioEntity.setContrasenya(contrasenya);
        }

        usuarioEntity.setRol(this.rolRepository.findById(idRolCoordinador).get());

        if (idEntidad != null) {
            usuarioEntity.setEntidad(this.entidadRepository.findById(idEntidad).get());
        } else {
            usuarioEntity.setEntidad(null);
        }

        if (idZona != null) {
            usuarioEntity.setZonaAsignada(this.zonaRepository.findById(idZona).get());
        } else {
            usuarioEntity.setZonaAsignada(null);
        }

        if (idLocalidad != null) {
            usuarioEntity.setLocalidad(this.localidadRepository.findById(idLocalidad).get());
        } else {
            usuarioEntity.setLocalidad(null);
        }

        if (idDistrito != null) {
            usuarioEntity.setDistrito(this.distritoRepository.findById(idDistrito).get());
        } else {
            usuarioEntity.setDistrito(null);
        }

        this.usuarioRepository.save(usuarioEntity);
    }

    public void guardarCapitan(Integer id,
                               String nombre,
                               String usuario,
                               String contrasenya,
                               String telefono,
                               String email,
                               Integer cp,
                               Integer idEntidad,
                               Integer idZona,
                               Integer idLocalidad,
                               Integer idDistrito) {

        UsuarioEntity usuarioEntity;

        if (id != null) {
            usuarioEntity = this.usuarioRepository.findById(id).get();
        } else {
            usuarioEntity = new UsuarioEntity();
        }

        usuarioEntity.setNombre(nombre);
        usuarioEntity.setUsuario(usuario);
        usuarioEntity.setTelefono(telefono);
        usuarioEntity.setEmail(email);
        usuarioEntity.setCp(cp);

        if (contrasenya != null && !contrasenya.isBlank()) {
            usuarioEntity.setContrasenya(contrasenya);
        }

        usuarioEntity.setRol(this.rolRepository.findById(idRolCapitan).get());

        if (idEntidad != null) {
            usuarioEntity.setEntidad(this.entidadRepository.findById(idEntidad).get());
        } else {
            usuarioEntity.setEntidad(null);
        }

        if (idZona != null) {
            usuarioEntity.setZonaAsignada(this.zonaRepository.findById(idZona).get());
        } else {
            usuarioEntity.setZonaAsignada(null);
        }

        if (idLocalidad != null) {
            usuarioEntity.setLocalidad(this.localidadRepository.findById(idLocalidad).get());
        } else {
            usuarioEntity.setLocalidad(null);
        }

        if (idDistrito != null) {
            usuarioEntity.setDistrito(this.distritoRepository.findById(idDistrito).get());
        } else {
            usuarioEntity.setDistrito(null);
        }

        this.usuarioRepository.save(usuarioEntity);
    }

    @Transactional
    public void eliminarUsuarios(List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            List<UsuarioEntity> usuarios = this.usuarioRepository.findAllById(ids);

            for (UsuarioEntity usuario : usuarios) {

                if (usuario.getColaboradoresCoordinados() != null &&
                        !usuario.getColaboradoresCoordinados().isEmpty()) {

                    for (ColaboradorEntity colaborador : usuario.getColaboradoresCoordinados()) {
                        colaborador.setCoordinador(null);
                    }

                    this.colaboradoresRespository.saveAll(usuario.getColaboradoresCoordinados());
                }

                if (usuario.getTiendasCoordinadas() != null &&
                        !usuario.getTiendasCoordinadas().isEmpty()) {

                    for (TiendaCampanyaEntity tiendaCampanya : usuario.getTiendasCoordinadas()) {
                        tiendaCampanya.setCoordinador(null);
                    }

                    this.tiendaCampanyaRepository.saveAll(usuario.getTiendasCoordinadas());
                }

                if (usuario.getTiendasCapitaneadas() != null &&
                        !usuario.getTiendasCapitaneadas().isEmpty()) {

                    for (TiendaEntity tienda : usuario.getTiendasCapitaneadas()) {
                        tienda.setCapitan(null);
                    }

                    this.tiendaRepository.saveAll(usuario.getTiendasCapitaneadas());
                }
            }

            this.usuarioRepository.deleteAll(usuarios);
        }
    }
}