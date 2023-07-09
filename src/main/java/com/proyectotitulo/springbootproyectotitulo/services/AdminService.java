package com.proyectotitulo.springbootproyectotitulo.services;

import com.proyectotitulo.springbootproyectotitulo.dao.HistorialRepositorio;
import com.proyectotitulo.springbootproyectotitulo.dao.LibroRepositorio;
import com.proyectotitulo.springbootproyectotitulo.dao.PrestamoRepositorio;
import com.proyectotitulo.springbootproyectotitulo.dao.ResenaRepositorio;
import com.proyectotitulo.springbootproyectotitulo.entity.Historial;
import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import com.proyectotitulo.springbootproyectotitulo.entity.Prestamo;
import com.proyectotitulo.springbootproyectotitulo.modeloPeticiones.AñadirLibro;
import com.proyectotitulo.springbootproyectotitulo.modeloRespuestas.PrestamosRespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AdminService {

    private LibroRepositorio libroRepo;

    private ResenaRepositorio resenaRepo;

    private PrestamoRepositorio prestamoRepo;

    private HistorialRepositorio historialRepo;

    @Autowired
    public AdminService(LibroRepositorio libroRepo, ResenaRepositorio resenaRepo, PrestamoRepositorio prestamoRepo, HistorialRepositorio historialRepo) {
        this.libroRepo = libroRepo;
        this.resenaRepo = resenaRepo;
        this.prestamoRepo = prestamoRepo;
        this.historialRepo = historialRepo;
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

    public void confirmarPrestamo(Long idPrestamo) throws Exception {

        Optional<Prestamo> prestamo = prestamoRepo.findById(idPrestamo);

        if (!prestamo.isPresent()) {
            throw new Exception("Prestamo no encontrado");
        }

        Optional<Libro> libro = libroRepo.findById(prestamo.get().getLibroId());

        libro.get().setCopiasDisponibles(libro.get().getCopiasDisponibles() - 1);
        libroRepo.save(libro.get());

        prestamoRepo.confirmarPrestamo(idPrestamo);

    }

    public void cancelarPrestamo(Long idPrestamo) throws Exception {

        Optional<Prestamo> prestamo = prestamoRepo.findById(idPrestamo);

        if (!prestamo.isPresent()) {
            throw new Exception("Prestamo no encontrado");
        }

        prestamoRepo.cancelarPrestamo(idPrestamo);

    }

    public List<PrestamosRespuesta> listarPrestamosPorEstado(String estado) throws Exception {

        List<PrestamosRespuesta> prestamosRespuesta = new ArrayList<>();
        List<Prestamo> listaPrestamos = prestamoRepo.findAllPrestamosByEstado(estado);

        List<Long> libroIdLista = new ArrayList<>();

        for (Prestamo p: listaPrestamos) {
            libroIdLista.add(p.getLibroId());
        }

        List<Libro> libros = libroRepo.findLibrosByLibroId(libroIdLista);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Libro libro: libros) {
            Optional<Prestamo> prestamo = listaPrestamos.stream().filter(x -> x.getLibroId() == libro.getId()).findFirst();

            if (prestamo.isPresent()) {
                Date fechaRetorno = simpleDateFormat.parse(prestamo.get().getFechaRetorno());
                Date fechaHoy = simpleDateFormat.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long diasRestantes = time.convert(fechaRetorno.getTime() - fechaHoy.getTime(), TimeUnit.MILLISECONDS);

                prestamosRespuesta.add(new PrestamosRespuesta(prestamo.get().getId(), libro, prestamo.get().getEstado(), prestamo.get().getUsuarioEmail(), (int) diasRestantes, prestamo.get().getFechaPrestamo(), prestamo.get().getFechaRetorno()));
            }
        }

        return prestamosRespuesta;
    }

    public List<PrestamosRespuesta> listarPrestamosPorCorreoUsuarioPorConfirmar(String correoUsuario, String estado) throws Exception {

        List<PrestamosRespuesta> prestamosRespuesta = new ArrayList<>();
        List<Prestamo> listaPrestamos = prestamoRepo.findAllPrestamosByEstadoByUsuario(correoUsuario, estado);

        List<Long> libroIdLista = new ArrayList<>();

        for (Prestamo p: listaPrestamos) {
            libroIdLista.add(p.getLibroId());
        }

        List<Libro> libros = libroRepo.findLibrosByLibroId(libroIdLista);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Libro libro: libros) {
            Optional<Prestamo> prestamo = listaPrestamos.stream().filter(x -> x.getLibroId() == libro.getId()).findFirst();

            if (prestamo.isPresent()) {
                Date fechaRetorno = simpleDateFormat.parse(prestamo.get().getFechaRetorno());
                Date fechaHoy = simpleDateFormat.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long diasRestantes = time.convert(fechaRetorno.getTime() - fechaHoy.getTime(), TimeUnit.MILLISECONDS);

                prestamosRespuesta.add(new PrestamosRespuesta(prestamo.get().getId(), libro, prestamo.get().getEstado(), prestamo.get().getUsuarioEmail(), (int) diasRestantes, prestamo.get().getFechaPrestamo(), prestamo.get().getFechaRetorno()));
            }
        }

        return prestamosRespuesta;
    }

    public void confirmarRenovacionPrestamo (String usuarioEmail, Long libroId) throws Exception {

        Prestamo renovarPrestamo = prestamoRepo.findByUsuarioEmailAndLibroId(usuarioEmail, libroId);

        if(renovarPrestamo == null) {
            throw new Exception("Prestamo no existe");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaRetorno = simpleDateFormat.parse(renovarPrestamo.getFechaRetorno());
        Date fechaActual = simpleDateFormat.parse(LocalDate.now().toString());

        renovarPrestamo.setEstado("Confirmado");

        if (fechaRetorno.compareTo(fechaActual) >= 0) {
            renovarPrestamo.setFechaRetorno(LocalDate.now().plusDays(7).toString());
            prestamoRepo.save(renovarPrestamo);
        }
    }

    public void cancelarRenovacionPrestamo(String usuarioEmail, Long libroId) throws Exception {

        Prestamo renovarPrestamo = prestamoRepo.findByUsuarioEmailAndLibroId(usuarioEmail, libroId);

        if(renovarPrestamo == null) {
            throw new Exception("Prestamo no existe");
        }

        renovarPrestamo.setEstado("Confirmado");

        prestamoRepo.save(renovarPrestamo);
    }

    public void confirmarRetornoPrestamo(String usuarioEmail, Long libroId) throws Exception {

        Optional<Libro> libro = libroRepo.findById(libroId);

        Prestamo validarPrestamo = prestamoRepo.findByUsuarioEmailAndLibroId(usuarioEmail, libroId);

        if(!libro.isPresent() || validarPrestamo == null) {

            throw new Exception("El libro o el prestamo no existe");
        }

        libro.get().setCopiasDisponibles(libro.get().getCopiasDisponibles() + 1);

        libroRepo.save(libro.get());

        prestamoRepo.deleteById(validarPrestamo.getId());

        Historial historial = new Historial(
                usuarioEmail,
                validarPrestamo.getFechaPrestamo(),
                LocalDate.now().toString(),
                libro.get().getTitulo(),
                libro.get().getAutor(),
                libro.get().getDescripcion(),
                libro.get().getImg()
        );

        historialRepo.save(historial);



    }

}
