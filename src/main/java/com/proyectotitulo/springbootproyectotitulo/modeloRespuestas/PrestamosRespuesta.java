package com.proyectotitulo.springbootproyectotitulo.modeloRespuestas;

import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import com.proyectotitulo.springbootproyectotitulo.entity.Prestamo;
import lombok.Data;

import javax.persistence.Column;

@Data
public class PrestamosRespuesta {

    private Long idPrestamo;

    private Libro libro;

    private String estado;

    private String correoUsuario;

    private int diasAlquilerRestantes;

    private String fechaPrestamo;

    private String fechaRetorno;

    public PrestamosRespuesta(Long idPrestamo, Libro libro, String estado, String correoUsuario, int diasAlquilerRestantes, String fechaPrestamo, String fechaRetorno) {
        this.idPrestamo = idPrestamo;
        this.libro = libro;
        this.estado = estado;
        this.correoUsuario = correoUsuario;
        this.diasAlquilerRestantes = diasAlquilerRestantes;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaRetorno = fechaRetorno;
    }
}
