package com.proyectotitulo.springbootproyectotitulo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "alquiler")
@Data
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_email")
    private String usuarioEmail;

    @Column(name = "fecha_alquiler")
    private String fechaPrestamo;

    @Column(name = "fecha_retorno")
    private String fechaRetorno;

    @Column(name = "libro_id")
    private Long libroId;

    public Prestamo() {
    }

    public Prestamo(String usuarioEmail, String fechaPrestamo, String fechaRetorno, Long libroId) {
        this.usuarioEmail = usuarioEmail;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaRetorno = fechaRetorno;
        this.libroId = libroId;
    }
}