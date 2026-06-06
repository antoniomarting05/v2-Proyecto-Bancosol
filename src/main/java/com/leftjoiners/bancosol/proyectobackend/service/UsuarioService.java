package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.UsuarioRepository;
import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import com.leftjoiners.bancosol.proyectobackend.entity.UsuarioEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Usuario autenticar(String user, String password) {
        Optional<UsuarioEntity> usuario = this.usuarioRepository.autenticar(user, password);
        if (usuario.isPresent()) {
            return this.usuarioMapper.toDTO(usuario.get());
        } else {
            return null;
        }

    }

    public List<Usuario> listarCoordinadores() {
        List<UsuarioEntity> coordinadores = this.usuarioRepository.findCoordinadores();
        return this.usuarioMapper.toDTOList(coordinadores);
    }

    public List<Usuario> listarCapitanes(){
        List<UsuarioEntity> capitanes = this.usuarioRepository.findCapitanes();
        return this.usuarioMapper.toDTOList(capitanes);
    }

}
