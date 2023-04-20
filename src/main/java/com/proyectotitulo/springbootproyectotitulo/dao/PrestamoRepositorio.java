package com.proyectotitulo.springbootproyectotitulo.dao;

import com.proyectotitulo.springbootproyectotitulo.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {

    Prestamo findByUsuarioEmailAndLibroId(String usuarioEmail, Long libroId);

    List<Prestamo> findLibrosByUsuarioEmail(String usuarioEmail);
}
