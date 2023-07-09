package com.proyectotitulo.springbootproyectotitulo.dao;

import com.proyectotitulo.springbootproyectotitulo.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {

    Prestamo findByUsuarioEmailAndLibroId(String usuarioEmail, Long libroId);

    @Modifying
    @Query("select p from Prestamo p where estado = :estado")
    List<Prestamo> findAllPrestamosByEstado(@Param("estado") String estado);

    @Modifying
    @Query("select p from Prestamo p where estado = 'Espera' and usuario_email = :correoUsuario")
    List<Prestamo> findAllPrestamosEsperaByUsuario(@Param("correoUsuario") String correoUsuario);

    @Modifying
    @Query("select p from Prestamo p where estado = :estado and usuario_email = :correoUsuario")
    List<Prestamo> findAllPrestamosByEstadoByUsuario(@Param("correoUsuario") String correoUsuario, @Param("estado") String estado);

    @Modifying
    @Query("update Prestamo set estado = 'Confirmado' where id = :idPrestamo")
    void confirmarPrestamo(@Param("idPrestamo") Long idPrestamo);

    @Modifying
    @Query("delete from Prestamo where id = :idPrestamo")
    void cancelarPrestamo(@Param("idPrestamo") Long idPrestamo);

    @Modifying
    @Query("select p from Prestamo p where usuario_email = :usuarioEmail and estado in ('Confirmado', 'Espera Renovacion', 'Espera Retorno')")
    List<Prestamo> findLibrosByUsuarioEmail(@Param("usuarioEmail") String usuarioEmail);

    @Modifying
    @Query("delete from Prestamo where libro_id in :libro_id")
    void deleteAllByLibroId(@Param("libro_id") Long libroId);

}
