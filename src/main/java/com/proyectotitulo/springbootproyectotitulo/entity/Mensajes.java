package com.proyectotitulo.springbootproyectotitulo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "mensajes")
@Data
public class Mensajes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_email")
    private String usuarioEmail;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "pregunta")
    private String pregunta;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "respuesta")
    private String respuesta;

    @Column(name = "cerrado")
    private boolean cerrado;

    public Mensajes() {
    }

    public Mensajes(String titulo, String pregunta) {
        this.titulo = titulo;
        this.pregunta = pregunta;
    }
}
