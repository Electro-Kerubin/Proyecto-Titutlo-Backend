package com.proyectotitulo.springbootproyectotitulo.dao;

import com.proyectotitulo.springbootproyectotitulo.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {

    Prestamo findByUsuarioEmailAndLibroId(String usuarioEmail, Long libroId);

    List<Prestamo> findLibrosByUsuarioEmail(String usuarioEmail);

    @Modifying
    @Query("delete from Prestamo where libro_id in :libro_id")
    void deleteAllByLibroId(@Param("libro_id") Long libroId);
}
