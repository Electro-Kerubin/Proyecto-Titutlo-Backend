package com.proyectotitulo.springbootproyectotitulo.dao;

import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {


}
