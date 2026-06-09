/*
Javier Urbaneja Benítez: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.CampanyaRepository;
import com.leftjoiners.bancosol.proyectobackend.dao.TiendaCampanyaRepository;
import com.leftjoiners.bancosol.proyectobackend.dao.TiendaRepository;
import com.leftjoiners.bancosol.proyectobackend.dao.TurnoRepository;
import com.leftjoiners.bancosol.proyectobackend.dto.TiendaCampanya;
import com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaCampanyaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.TiendaCampanyaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TiendaCampanyaService {
    private final TiendaCampanyaRepository tiendaCampanyaRepository;
    private final TiendaCampanyaMapper tiendaCampanyaMapper;
    private final TiendaRepository tiendaRepository;
    private final CampanyaRepository campanyaRepository;
    private final TurnoRepository turnoRepository;

    public List<TiendaCampanya> listarTiendaCampanya () {
        List<TiendaCampanyaEntity> tiendaCampanyas = this.tiendaCampanyaRepository.findAll();
        return tiendaCampanyaMapper.toDTOList(tiendaCampanyas);
    }

    public TiendaCampanya buscarTiendaCampanya (Integer id) {
        if (id == null) return null;

        TiendaCampanyaEntity tiendaCampanya = this.tiendaCampanyaRepository.findById(id).orElse(null);

        return tiendaCampanyaMapper.toDTO(tiendaCampanya);
    }

    public List<TiendaCampanya> buscarTiendasCampanyaPorTienda (Integer idTienda) {
        if (idTienda == null) return null;

        List<TiendaCampanyaEntity> tiendaCampanyas = this.tiendaCampanyaRepository.buscarTodosPorIdTienda(idTienda);

        return this.tiendaCampanyaMapper.toDTOList(tiendaCampanyas);
    }

    @Transactional
    public void actualizarParticipacion(Integer idTienda, Integer idTipoCampanya, Integer idNuevaCampanya) {
        TiendaEntity tienda = this.tiendaRepository.findById(idTienda).orElse(null);
        if (tienda == null) return;

        List<TiendaCampanyaEntity> participacionesActuales = this.tiendaCampanyaRepository.buscarTodosPorIdTienda(idTienda);

        // Buscamos si la tienda ya estaba participando en alguna campaña de ESTE tipo
        TiendaCampanyaEntity participacionExistente = participacionesActuales.stream()
                .filter(tc -> tc.getCampanya().getTipoCampanya().getId().equals(idTipoCampanya))
                .findFirst()
                .orElse(null);

        // Si el usuario ha seleccionado que no participa, borramos su participación
        if (idNuevaCampanya == 0) {
            if (participacionExistente != null) {
                if (participacionExistente.getTurnos() != null && !participacionExistente.getTurnos().isEmpty()) {
                    this.turnoRepository.deleteAll(participacionExistente.getTurnos());
                }
                this.tiendaCampanyaRepository.delete(participacionExistente);
            }
        }
        // Si el usuario ha seleccionado una Campaña, actualizamos la Campaña
        else {
            CampanyaEntity nuevaCampanya = this.campanyaRepository.findById(idNuevaCampanya).orElse(null);

            if (nuevaCampanya != null) {
                if (participacionExistente != null) {
                    if (!participacionExistente.getCampanya().getId().equals(idNuevaCampanya)) {
                        participacionExistente.setCampanya(nuevaCampanya);

                        if (participacionExistente.getTurnos() != null && !participacionExistente.getTurnos().isEmpty()) {
                            this.turnoRepository.deleteAll(participacionExistente.getTurnos());
                            participacionExistente.getTurnos().clear();
                        }
                        this.tiendaCampanyaRepository.save(participacionExistente);
                    }
                } else {
                    // Si no participaba, creamos el registro nuevo
                    TiendaCampanyaEntity tiendaCampanya = new TiendaCampanyaEntity();
                    tiendaCampanya.setTienda(tienda);
                    tiendaCampanya.setCampanya(nuevaCampanya);
                    this.tiendaCampanyaRepository.save(tiendaCampanya);
                }
            }
        }
    }
}
