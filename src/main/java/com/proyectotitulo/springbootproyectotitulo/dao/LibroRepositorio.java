package com.proyectotitulo.springbootproyectotitulo.dao;

import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    /**
     * Devuelve una página de libros que contienen el título especificado,
     * según los criterios de paginación especificados en el objeto Pageable.
     *
     * @param titulo    el título que deben contener los libros a buscar
     * @param pageable  los criterios de paginación para la búsqueda de libros
     * @return          una página de objetos Libro que contienen el título especificado
     *                  y que cumplen los criterios de paginación especificados
     */
    @Query(value = "SELECT * FROM LIBRO WHERE TITULO LIKE %?1%", nativeQuery = true)
    Page<Libro> findByTitulo(@RequestParam("titulo") String titulo, Pageable pageable);

    /**
     * Devuelve una página de libros que pertenecen a la categoría especificada,
     * según los criterios de paginación especificados en el objeto Pageable.
     *
     * @param categoria el nombre de la categoría a la que deben pertenecer los libros a buscar
     * @param pageable  los criterios de paginación para la búsqueda de libros
     * @return          una página de objetos Libro que pertenecen a la categoría especificada
     *                  y que cumplen los criterios de paginación especificados
     */
    Page<Libro> findByCategoria(@RequestParam("categoria") String categoria, Pageable pageable);

    @Query("select c from Libro c where id in :ids_libros")
    List<Libro> findLibrosByLibroId(@Param("ids_libros") List<Long> libroId);
}
