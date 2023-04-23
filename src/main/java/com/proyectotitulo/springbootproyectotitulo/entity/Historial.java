package com.proyectotitulo.springbootproyectotitulo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "historial")
@Data
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_email")
    private String usuarioEmail;

    @Column(name = "fecha_alquiler")
    private String fechaPrestamo;

    @Column(name = "fecha_retornado")
    private String fechaRetorno;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autor")
    private String autor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "img")
    private String img;

    public Historial() {
    }

    public Historial(String usuarioEmail, String fechaPrestamo, String fechaRetorno, String titulo, String autor, String descripcion, String img) {
        this.usuarioEmail = usuarioEmail;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaRetorno = fechaRetorno;
        this.titulo = titulo;
        this.autor = autor;
        this.descripcion = descripcion;
        this.img = img;
    }
}
