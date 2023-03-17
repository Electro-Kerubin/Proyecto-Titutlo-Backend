package com.proyectotitulo.springbootproyectotitulo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autor")
    private String autor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "copias")
    private int copias;

    @Column(name = "copias_disponibles")
    private int copiasDisponibles;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "img")
    private String img;
}
