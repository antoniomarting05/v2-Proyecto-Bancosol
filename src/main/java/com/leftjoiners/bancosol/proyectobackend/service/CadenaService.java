/*
Antonio Martín García: 80%
IA: 20%
*/
package com.leftjoiners.bancosol.proyectobackend.service;

import com.leftjoiners.bancosol.proyectobackend.dao.*;
import com.leftjoiners.bancosol.proyectobackend.dto.Cadena;
import com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaCampanyaEntity;
import com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity;
import com.leftjoiners.bancosol.proyectobackend.mapper.CadenaMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CadenaService {
    private final CadenaRepository cadenaRepository;
    private final TiendaRepository tiendaRepository;
    private final TiendaCampanyaRepository tiendaCampanyaRepository;
    private final TurnoRepository turnoRepository;
    private final CampanyaRepository campanyaRepository;
    private final CadenaMapper cadenaMapper;

    public List<Cadena> listarCadenas() {
        List<CadenaEntity> cadenas = this.cadenaRepository.findAll();
        return this.cadenaMapper.toDTOList(cadenas);
    }

    public Cadena buscarCadena(Integer id) {
        CadenaEntity cadena = this.cadenaRepository.findById(id).orElse(null);
        return this.cadenaMapper.toDTO(cadena);
    }

    public void guardarCadena(Integer id, String nombre, String codigo) {
        CadenaEntity cadenaEntity;

        if (id != null) {
            cadenaEntity = this.cadenaRepository.findById(id).orElse(new CadenaEntity());
        } else {
            cadenaEntity = new CadenaEntity();
        }

        cadenaEntity.setNombre(nombre);
        cadenaEntity.setCodigo(codigo);

        this.cadenaRepository.save(cadenaEntity);
    }

    @Transactional
    public void eliminarCadenas(List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            List<CadenaEntity> cadenas = this.cadenaRepository.findAllById(ids);

            for (CadenaEntity cadena : cadenas) {
                // Borramos la cadena de todas las Campañas
                if (cadena.getCampanyas() != null) {
                    for (CampanyaEntity campanya : cadena.getCampanyas()) {
                        campanya.getCadenasParticipantes().remove(cadena);
                    }
                }

                // Borramos todas sus tiendas y las dependencias de estas
                if (cadena.getTiendas() != null && !cadena.getTiendas().isEmpty()) {
                    for (TiendaEntity tienda : cadena.getTiendas()) {

                        // Borramos la participacion de la tienda en la campaña y sus turnos
                        if (tienda.getTiendasCampanya() != null && !tienda.getTiendasCampanya().isEmpty()) {
                            for (TiendaCampanyaEntity tc : tienda.getTiendasCampanya()) {
                                // Borramos sus turnos
                                if (tc.getTurnos() != null && !tc.getTurnos().isEmpty()) {
                                    turnoRepository.deleteAll(tc.getTurnos());
                                }
                            }
                            // Borramos la participacion de la tienda
                            tiendaCampanyaRepository.deleteAll(tienda.getTiendasCampanya());
                        }
                    }
                    // Borramos las tiendas
                    tiendaRepository.deleteAll(cadena.getTiendas());
                }
            }

            // Borramos las cadenas
            this.cadenaRepository.deleteAll(cadenas);
        }
    }
}
