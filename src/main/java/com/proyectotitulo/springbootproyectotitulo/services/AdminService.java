package com.proyectotitulo.springbootproyectotitulo.services;

import com.proyectotitulo.springbootproyectotitulo.dao.LibroRepositorio;
import com.proyectotitulo.springbootproyectotitulo.dao.PrestamoRepositorio;
import com.proyectotitulo.springbootproyectotitulo.dao.ResenaRepositorio;
import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import com.proyectotitulo.springbootproyectotitulo.modeloPeticiones.AñadirLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private LibroRepositorio libroRepo;

    private ResenaRepositorio resenaRepo;

    private PrestamoRepositorio prestamoRepo;

    @Autowired
    public AdminService(LibroRepositorio libroRepo, ResenaRepositorio resenaRepo, PrestamoRepositorio prestamoRepo) {
        this.libroRepo = libroRepo;
        this.resenaRepo = resenaRepo;
        this.prestamoRepo = prestamoRepo;
    }

    public void anadirNuevoLibro(AñadirLibro añadirLibro) {
        Libro libro = new Libro();
        libro.setTitulo(añadirLibro.getTitulo());
        libro.setAutor(añadirLibro.getAutor());
        libro.setDescripcion(añadirLibro.getDescripcion());
        libro.setCopias(añadirLibro.getCopias());
        libro.setCopiasDisponibles(añadirLibro.getCopias());
        libro.setCategoria(añadirLibro.getCategoria());
        libro.setImg(añadirLibro.getImg());

        libroRepo.save(libro);

    }

    public void aumentarCantidadLibro(Long libroId) throws Exception {

        Optional<Libro> libro = libroRepo.findById(libroId);

        if (!libro.isPresent()) {
            throw new Exception("libro no encontrado");
        }

        libro.get().setCopiasDisponibles(libro.get().getCopiasDisponibles() + 1);
        libro.get().setCopias(libro.get().getCopias() + 1);

        libroRepo.save(libro.get());
    }

    public void disminuirCantidadLibro(Long libroId) throws Exception {

        Optional<Libro> libro = libroRepo.findById(libroId);

        if(!libro.isPresent() || libro.get().getCopiasDisponibles() <= 0 || libro.get().getCopias() <= 0) {
            throw new Exception("Libro no encontrado o no la cantidad es 0");
        }

        libro.get().setCopiasDisponibles(libro.get().getCopiasDisponibles() - 1);
        libro.get().setCopias(libro.get().getCopias() - 1);

        libroRepo.save(libro.get());
    }

    public void eliminarLibro(Long libroId) throws Exception {

        Optional<Libro> libro = libroRepo.findById(libroId);

        if (!libro.isPresent()) {
            throw new Exception("Libro no encontrado");
        }

        libroRepo.delete(libro.get());

        prestamoRepo.deleteAllByLibroId(libroId);

        resenaRepo.deleteAllByLibroId(libroId);
    }

}
