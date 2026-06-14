/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private RolEntity rol;

    private String usuario;
    private String contrasenya;
    private String nombre;
    private String telefono;
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_entidad")
    private EntidadEntity entidad;

    @ManyToOne
    @JoinColumn(name = "id_zona_asignada")
    private ZonaEntity zonaAsignada;

    @ManyToOne
    @JoinColumn(name = "id_localidad")
    private LocalidadEntity localidad;

    private Integer cp;

    @ManyToOne
    @JoinColumn(name = "id_distrito")
    private DistritoEntity distrito;

    // Relaciones inversas (Un usuario puede coordinar colaboradores y tiendas)
    @OneToMany(mappedBy = "coordinador")
    private List<ColaboradorEntity> colaboradoresCoordinados = new ArrayList<>();

    @OneToMany(mappedBy = "coordinador")
    private List<TiendaCampanyaEntity> tiendasCoordinadas = new ArrayList<>();

    @OneToMany(mappedBy = "capitan")
    private List<TiendaEntity> tiendasCapitaneadas = new ArrayList<>();
}