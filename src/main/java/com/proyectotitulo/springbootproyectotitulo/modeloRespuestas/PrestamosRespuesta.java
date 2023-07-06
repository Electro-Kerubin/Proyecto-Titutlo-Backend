package com.proyectotitulo.springbootproyectotitulo.modeloRespuestas;

import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import com.proyectotitulo.springbootproyectotitulo.entity.Prestamo;
import lombok.Data;

@Data
public class PrestamosRespuesta {

    private Libro libro;

    private String estado;

    private String correoUsuario;

    private int diasAlquilerRestantes;

    public PrestamosRespuesta(Libro libro, String estado, String correoUsuario, int diasAlquilerRestantes) {
        this.libro = libro;
        this.estado = estado;
        this.correoUsuario = correoUsuario;
        this.diasAlquilerRestantes = diasAlquilerRestantes;
    }
}
