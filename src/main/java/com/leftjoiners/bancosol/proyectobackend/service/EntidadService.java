package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.EntidadRepository;
import com.leftjoiners.bancosol.proyectobackend.dto.Entidad;
import com.leftjoiners.bancosol.proyectobackend.entity.EntidadEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.EntidadMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EntidadService {
    private final EntidadRepository entidadRepository;
    private final EntidadMapper entidadMapper;

    public List<Entidad> listarEntidades() {
        List<EntidadEntity> entidades = this.entidadRepository.findAll();
        return this.entidadMapper.toDTOList(entidades);
    }
}
