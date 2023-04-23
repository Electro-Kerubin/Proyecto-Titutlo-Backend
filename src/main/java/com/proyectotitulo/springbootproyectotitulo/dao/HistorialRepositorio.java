package com.proyectotitulo.springbootproyectotitulo.dao;

import com.proyectotitulo.springbootproyectotitulo.entity.Historial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface HistorialRepositorio extends JpaRepository<Historial, Long> {

    Page<Historial> findLibrosByUsuarioEmail(@RequestParam("usuario_email") String usuarioEmail, Pageable pageable);
}
