package com.proyectotitulo.springbootproyectotitulo.dao;

import com.proyectotitulo.springbootproyectotitulo.entity.Resena;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface ResenaRepositorio extends JpaRepository<Resena, Long> {

    Page<Resena> findByLibroId(@RequestParam("libro_id") Long libroId, Pageable pageable);

    Resena findByUsuarioEmailAndLibroId(String usuarioEmail, Long libroId);

    @Modifying
    @Query("delete from Resena where libro_id in :libro_id")
    void deleteAllByLibroId(@Param("libro_id") Long libroId);

}
