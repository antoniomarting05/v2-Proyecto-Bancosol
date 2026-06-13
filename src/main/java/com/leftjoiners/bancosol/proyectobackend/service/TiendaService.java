package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.Tienda;
import com.leftjoiners.bancosol.proyectobackend.entity.*;
import com.leftjoiners.bancosol.proyectobackend.mapper.TiendaMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TiendaService {

    private final TiendaRepository tiendaRepository;
    private final CadenaRepository cadenaRepository;
    private final LocalidadRepository localidadRepository;
    private final DistritoRepository distritoRepository;
    private final TiendaCampanyaRepository tiendaCampanyaRepository;
    private final CampanyaRepository campanyaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TiendaMapper tiendaMapper;

    public List<Tienda> listarTiendas() {
        List<TiendaEntity> tiendas = this.tiendaRepository.findAll();
        return this.tiendaMapper.toDTOList(tiendas);
    }

    public List<Tienda> filtrarTiendas(Integer cadenaId, Integer localidadId, Integer zonaId) {
        List<TiendaEntity> tiendasEntities = this.tiendaRepository.filtrarTiendasMulticriterio(cadenaId, localidadId, zonaId);
        return this.tiendaMapper.toDTOList(tiendasEntities);
    }

    public Tienda buscarTienda(Integer id) {
        TiendaEntity tienda = this.tiendaRepository.findById(id).orElse(null);
        return this.tiendaMapper.toDTO(tienda);
    }

    public void guardarTienda(Integer id, String nombre, Integer lineales, String domicilio,
                              String cp, Integer distritoId, Integer cadenaId, Integer localidadId,
                              Integer capitanId) {
        TiendaEntity tiendaEntity;

        if (id != null) {
            tiendaEntity = this.tiendaRepository.findById(id).orElse(new TiendaEntity());
        } else {
            tiendaEntity = new TiendaEntity();
        }

        tiendaEntity.setNombre(nombre);
        tiendaEntity.setDomicilio(domicilio);

        if (lineales != null) {
            tiendaEntity.setLineales(lineales);
        } else {
            tiendaEntity.setLineales(0);
        }

        tiendaEntity.setCp(cp);

        if (distritoId != null) {
            tiendaEntity.setDistrito(this.distritoRepository.findById(distritoId).orElse(null));
        } else {
            tiendaEntity.setDistrito(null);
        }

        if (cadenaId != null) {
            tiendaEntity.setCadena(this.cadenaRepository.findById(cadenaId).orElse(null));
        } else {
            tiendaEntity.setCadena(null);
        }

        if (localidadId != null) {
            tiendaEntity.setLocalidad(this.localidadRepository.findById(localidadId).orElse(null));
        } else {
            tiendaEntity.setLocalidad(null);
        }

        if (capitanId != null) {
            tiendaEntity.setCapitan(this.usuarioRepository.findById(capitanId).orElse(null));
        } else {
            tiendaEntity.setCapitan(null);
        }

        this.tiendaRepository.save(tiendaEntity);

    }

    @Transactional
    public void eliminarTienda(Integer idTienda) {
        if (idTienda != null) {
            TiendaEntity tienda = this.tiendaRepository.findById(idTienda).orElse(null);

            if (tienda != null) {
                if (tienda.getTiendasCampanya() != null && !tienda.getTiendasCampanya().isEmpty()) {
                    //elimino relaciones
                    this.tiendaCampanyaRepository.deleteAll(tienda.getTiendasCampanya());
                }

                this.tiendaRepository.delete(tienda);
            }
        }
    }

    @Transactional
    public void actualizarCoordinadorEnCampanya(Integer tiendaId, Integer campanyaId, Integer coordinadorId) {
        if (tiendaId == null || campanyaId == null) return;

        TiendaEntity tienda = this.tiendaRepository.findById(tiendaId).orElse(null);

        if (tienda != null && tienda.getTiendasCampanya() != null) {
            for (TiendaCampanyaEntity tc : tienda.getTiendasCampanya()) {
                // Busco la tienda campanya
                if (tc.getCampanya().getId().equals(campanyaId)) {

                    UsuarioEntity coordinador = null;
                    // Si hay ID coord, lo busco
                    if (coordinadorId != null) {
                        coordinador = this.usuarioRepository.findById(coordinadorId).orElse(null);
                    }

                    // actualizo y guardo
                    tc.setCoordinador(coordinador);
                    this.tiendaCampanyaRepository.save(tc);
                    break;
                }
            }
        }
    }
}