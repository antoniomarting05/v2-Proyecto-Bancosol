package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.Campanya;
import com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaCampanyaEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.CampanyaMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class CampanyasService {
    private final CampanyaRepository campanyaRepository;
    private final TipoCampanyasRepository tipoCampanyasRepository;
    private final CadenaRepository cadenaRepository;
    private final TurnoRepository turnoRepository;
    private final TiendaCampanyaRepository tiendaCampanyaRepository;
    private CampanyaMapper campanyaMapper;

    public List<Campanya> listarCampanyas() {
        List<CampanyaEntity> campanyas = this.campanyaRepository.findAll();
        return this.campanyaMapper.toDTOList(campanyas);
    }

    public Campanya buscarCampanya(Integer id) {
        CampanyaEntity campanya = this.campanyaRepository.findById(id).orElse(null);
        return this.campanyaMapper.toDTO(campanya);
    }

    public void guardarCampanya(Integer id, String nombre, Integer idTipo,
                                LocalDate fechaInicio, LocalDate fechaFin,
                                List<Integer> cadenasSeleccionadas) {
        CampanyaEntity campanyaEntity;

        if (id != null) {
            campanyaEntity = this.campanyaRepository.findById(id).orElse(new CampanyaEntity());
        } else {
            campanyaEntity = new CampanyaEntity();
        }

        campanyaEntity.setNombre(nombre);

        if (idTipo != null) {
            campanyaEntity.setTipoCampanya(this.tipoCampanyasRepository.findById(idTipo).orElse(null));
        } else {
            campanyaEntity.setTipoCampanya(null);
        }

        campanyaEntity.setFechaInicio(fechaInicio);
        campanyaEntity.setFechaFin(fechaFin);

        if (cadenasSeleccionadas != null) {
            campanyaEntity.setCadenasParticipantes(this.cadenaRepository.findAllById(cadenasSeleccionadas));
        }

        if (fechaInicio != null && fechaFin != null) {
            int duracion = (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
            campanyaEntity.setDuracion(duracion);
        }

        this.campanyaRepository.save(campanyaEntity);
    }

    @Transactional
    public void eliminarCampanyas(List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            List<CampanyaEntity> campanyas = this.campanyaRepository.findAllById(ids);

            for (CampanyaEntity campanya : campanyas) {
                if (campanya.getTiendasCampanya() != null && !campanya.getTiendasCampanya().isEmpty()) {
                    for (TiendaCampanyaEntity tc : campanya.getTiendasCampanya()) {
                        if (tc.getTurnos() != null && !tc.getTurnos().isEmpty()) {
                            turnoRepository.deleteAll(tc.getTurnos());
                        }
                    }
                    tiendaCampanyaRepository.deleteAll(campanya.getTiendasCampanya());
                }
            }

            this.campanyaRepository.deleteAll(campanyas);
        }
    }

    public List<Campanya> filtrarCampanyasPorTipo(Integer tipoCampanyaId) {
        List<CampanyaEntity> campanyas;

        if (tipoCampanyaId == 0) {
            campanyas = this.campanyaRepository.findAll();
        } else {
            campanyas = this.campanyaRepository.findByTipoCampanya(tipoCampanyaId);
        }

        return this.campanyaMapper.toDTOList(campanyas);
    }
}