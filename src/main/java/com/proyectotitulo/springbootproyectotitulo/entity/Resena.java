package com.proyectotitulo.springbootproyectotitulo.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reseña")
@Data
public class Resena {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_email")
    private String usuarioEmail;

    @Column(name = "fecha")
    @CreationTimestamp
    private Date fecha;

    @Column(name = "puntaje")
    private double puntaje;

    @Column(name = "libro_id")
    private Long libroId;

    @Column(name = "reseña_descripcion")
    private String resenaDescripcion;
}
