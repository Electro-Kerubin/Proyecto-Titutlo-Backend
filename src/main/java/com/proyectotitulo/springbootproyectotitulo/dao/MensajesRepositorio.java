package com.proyectotitulo.springbootproyectotitulo.dao;

import com.proyectotitulo.springbootproyectotitulo.entity.Mensajes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface MensajesRepositorio extends JpaRepository<Mensajes, Long> {

    Page<Mensajes> findMensajesByUsuarioEmail(@RequestParam("usuario_email") String usuarioEmail, Pageable pageable);

    Page<Mensajes> findByCerrado(@RequestParam("cerrado") boolean cerrado, Pageable pageable);
}
