package com.proyectotitulo.springbootproyectotitulo.modeloRespuestas;

import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import lombok.Data;

@Data
public class PrestamosRespuesta {

    public PrestamosRespuesta(Libro libro, int diasAlquilerRestantes) {
        this.libro = libro;
        this.diasAlquilerRestantes = diasAlquilerRestantes;
    }

    private Libro libro;

    private int diasAlquilerRestantes;
}
