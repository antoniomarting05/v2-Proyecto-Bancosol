/*
Javier Urbaneja Benítez: 100%
*/
package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.CadenaRepository;
import com.leftjoiners.bancosol.proyectobackend.dao.TiendaRepository;
import com.leftjoiners.bancosol.proyectobackend.dao.TipoCampanyasRepository;
import com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya;
import com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TipoCampanyaEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.TipoCampanyaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TipoCampanyaService {
    private final TipoCampanyasRepository tipoCampanyasRepository;
    private final TipoCampanyaMapper tipoCampanyaMapper;
    private final TiendaRepository tiendaRepository;
    private final CadenaRepository cadenaRepository;

    public List<TipoCampanya> listarTipoCampanyas() {
        List<TipoCampanyaEntity> tipoCampanyas = this.tipoCampanyasRepository.findAll();
        return this.tipoCampanyaMapper.toDTOList(tipoCampanyas);
    }

    public List<TipoCampanya> buscarTipoCampanyaParticipantes (Integer idTienda) {
        List<TipoCampanyaEntity> campanyasParticipantes = new ArrayList<>();
        TiendaEntity tienda = this.tiendaRepository.findById(idTienda).orElse(null);
        if (tienda != null) {
            CadenaEntity cadenaTienda = tienda.getCadena();
            if (cadenaTienda != null) {
                campanyasParticipantes = this.tipoCampanyasRepository.findTipoCampanyasParticipantes(cadenaTienda.getId());
            }
        }

        return this.tipoCampanyaMapper.toDTOList(campanyasParticipantes);


    }
}