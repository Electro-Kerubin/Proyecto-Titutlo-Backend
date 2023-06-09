package com.proyectotitulo.springbootproyectotitulo.services;

import com.proyectotitulo.springbootproyectotitulo.dao.HistorialRepositorio;
import com.proyectotitulo.springbootproyectotitulo.dao.LibroRepositorio;
import com.proyectotitulo.springbootproyectotitulo.dao.PrestamoRepositorio;
import com.proyectotitulo.springbootproyectotitulo.entity.Historial;
import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import com.proyectotitulo.springbootproyectotitulo.entity.Prestamo;
import com.proyectotitulo.springbootproyectotitulo.modeloRespuestas.PrestamosRespuesta;
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
public class LibroService {

    private LibroRepositorio libroRepo;

    private PrestamoRepositorio prestamoRepo;

    private HistorialRepositorio historialRepo;

    public LibroService(LibroRepositorio libroRepo, PrestamoRepositorio prestamoRepo, HistorialRepositorio historialRepo) {
        this.libroRepo = libroRepo;
        this.prestamoRepo = prestamoRepo;
        this.historialRepo = historialRepo;
    }

    public Libro pedirPrestamoLibro (String usuarioEmail, Long libroId) throws Exception {

        // Se busca el libro en la base de datos mediante su identificador
        Optional<Libro> libro = libroRepo.findById(libroId);

        // Se busca un objeto Prestamo en la base de datos para verificar si el usuario ya ha solicitado un préstamo para el mismo libro
        Prestamo validarPrestamo = prestamoRepo.findByUsuarioEmailAndLibroId(usuarioEmail, libroId);

        // Se realizan validaciones para verificar si el libro existe, si está disponible para ser prestado y si el usuario no ha solicitado previamente un préstamo para el mismo libro
        if (!libro.isPresent() || validarPrestamo != null || libro.get().getCopiasDisponibles() <= 0) {
            throw new Exception("Libro no existe o ya prestado.");
        }

        // Se actualiza el estado del libro (se resta 1 al contador de copias disponibles) y se guarda en la base de datos
        //libro.get().setCopiasDisponibles(libro.get().getCopiasDisponibles() - 1);
        //libroRepo.save(libro.get());

        // Se crea un nuevo objeto Prestamo con los datos del usuario, la fecha actual y la fecha de devolución estimada (7 días después de la fecha actual), y se guarda en la base de datos
        Prestamo prestamo = new Prestamo(
                        usuarioEmail,
                        LocalDate.now().toString(),
                        LocalDate.now().plusDays(7).toString(),
                        libro.get().getId(),
                        "Espera"
                );

        // Crea historial


        prestamoRepo.save(prestamo);

        // Se retorna el objeto Libro actualizado después de realizar el préstamo
        return libro.get();
    }

    public Boolean verificarPrestamo (String usuarioEmail, Long libroId) {
        Prestamo existePrestamo = prestamoRepo.findByUsuarioEmailAndLibroId(usuarioEmail, libroId);

        if (existePrestamo != null) {
            return true;
        } else {
            return false;
        }
    }

    public int cantidadPrestamosActuales (String usuarioEmail) {
        return prestamoRepo.findLibrosByUsuarioEmail(usuarioEmail).size();
    }

    public List<PrestamosRespuesta> listaPrestamosActuales(String usuarioEmail) throws Exception {

        List<PrestamosRespuesta> prestamosRespuesta = new ArrayList<>();

        List<Prestamo> listaPrestamos = prestamoRepo.findLibrosByUsuarioEmail(usuarioEmail);

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

                prestamosRespuesta.add(new PrestamosRespuesta(prestamo.get().getId(), libro, prestamo.get().getEstado(), usuarioEmail, (int) diasRestantes, prestamo.get().getFechaPrestamo(), prestamo.get().getFechaRetorno()));
            }
        }

        return prestamosRespuesta;
    }

    public void retortaLibroPrestamo(String usuarioEmail, Long libroId) throws Exception {

        Optional<Libro> libro = libroRepo.findById(libroId);

        Prestamo validarPrestamo = prestamoRepo.findByUsuarioEmailAndLibroId(usuarioEmail, libroId);

        if(!libro.isPresent() || validarPrestamo == null) {

            throw new Exception("El libro o el prestamo no existe");
        }

        validarPrestamo.setEstado("Espera Retorno");
        prestamoRepo.save(validarPrestamo);

    }


    public void renovarPrestamo (String usuarioEmail, Long libroId) throws Exception {

        Prestamo renovarPrestamo = prestamoRepo.findByUsuarioEmailAndLibroId(usuarioEmail, libroId);

        if(renovarPrestamo == null) {
            throw new Exception("Prestamo no existe");
        }

        renovarPrestamo.setEstado("Espera Renovacion");

        prestamoRepo.save(renovarPrestamo);
    }



}
