/*
Inteligencia Artificial: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colaborador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColaboradorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;
    private String nombre;

    @Column(name = "contacto_principal")
    private String contactoPrincipal;

    private String domicilio;
    private String cp;

    @ManyToOne
    @JoinColumn(name = "localidad_sede")
    private LocalidadEntity localidadSede;

    @ManyToOne
    @JoinColumn(name = "colabora_en")
    private LocalidadEntity colaboraEn;

    private Boolean temporal;

    @ManyToOne
    @JoinColumn(name = "id_coordinador")
    private UsuarioEntity coordinador;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @OneToMany(mappedBy = "colaborador")
    private List<ContactoColaboradorEntity> contactos = new ArrayList<>();

    @OneToMany(mappedBy = "colaborador")
    private List<TurnoEntity> turnos = new ArrayList<>();
}