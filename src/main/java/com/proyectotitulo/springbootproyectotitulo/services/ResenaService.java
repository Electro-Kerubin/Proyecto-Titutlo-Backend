package com.proyectotitulo.springbootproyectotitulo.services;

import com.proyectotitulo.springbootproyectotitulo.dao.LibroRepositorio;
import com.proyectotitulo.springbootproyectotitulo.dao.ResenaRepositorio;
import com.proyectotitulo.springbootproyectotitulo.entity.Resena;
import com.proyectotitulo.springbootproyectotitulo.modeloPeticiones.ResenaPeticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ResenaService {

    private LibroRepositorio libroRepo;

    private ResenaRepositorio resenaRepo;

    @Autowired
    public ResenaService(LibroRepositorio libroRepo, ResenaRepositorio resenaRepo) {
        this.libroRepo = libroRepo;
        this.resenaRepo = resenaRepo;
    }

    public void postResena(String usuarioEmail, ResenaPeticion resenaPeticion) throws Exception {

        Resena validarResena = resenaRepo.findByUsuarioEmailAndLibroId(usuarioEmail, resenaPeticion.getLibroId());

        if (validarResena != null) {
            throw new Exception("Resena ya creada");
        }

        Resena resena = new Resena();
        resena.setLibroId(resenaPeticion.getLibroId());
        resena.setPuntaje(resenaPeticion.getPuntuacion());
        resena.setUsuarioEmail(usuarioEmail);

        if (resenaPeticion.getResenaDescripcion().isPresent()) {
            resena.setResenaDescripcion(resenaPeticion.getResenaDescripcion().map(
                    Object::toString
            ).orElse(null));
        }

        resena.setFecha(Date.valueOf(LocalDate.now()));

        resenaRepo.save(resena);

    }

    public Boolean existeResena(String usuarioEmail, Long libroId) {
        Resena validateReview = resenaRepo.findByUsuarioEmailAndLibroId(usuarioEmail, libroId);
        if (validateReview != null) {
            return true;
        } else {
            return false;
        }
    }

}
