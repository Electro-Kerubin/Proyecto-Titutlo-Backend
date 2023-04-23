package com.proyectotitulo.springbootproyectotitulo.modeloPeticiones;

import lombok.Data;

@Data
public class AñadirLibro {

    private String titulo;

    private String autor;

    private String descripcion;

    private int copias;

    private String categoria;

    private String img;

}
