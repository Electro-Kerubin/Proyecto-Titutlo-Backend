package com.proyectotitulo.springbootproyectotitulo.modeloPeticiones;

import lombok.Data;

import java.util.Optional;

@Data
public class ResenaPeticion {

    private double puntuacion;

    private Long libroId;

    private Optional<String> resenaDescripcion;
}
