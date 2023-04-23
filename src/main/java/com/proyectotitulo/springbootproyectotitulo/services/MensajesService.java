package com.proyectotitulo.springbootproyectotitulo.services;

import com.proyectotitulo.springbootproyectotitulo.dao.MensajesRepositorio;
import com.proyectotitulo.springbootproyectotitulo.entity.Mensajes;
import com.proyectotitulo.springbootproyectotitulo.modeloPeticiones.PreguntasAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MensajesService {

    private MensajesRepositorio mensajesRepo;

    @Autowired
    public MensajesService(MensajesRepositorio mensajesRepo) {
        this.mensajesRepo = mensajesRepo;
    }

    public void enviarMensaje(Mensajes mensajeUsuario, String usuarioEmail) {
        Mensajes mensaje = new Mensajes(mensajeUsuario.getTitulo(), mensajeUsuario.getPregunta());
        mensaje.setUsuarioEmail(usuarioEmail);
        mensajesRepo.save(mensaje);
    }

    public void responderMensaje(PreguntasAdmin preguntasAdmin, String usuarioEmail) throws Exception {

        Optional<Mensajes> mensaje = mensajesRepo.findById(preguntasAdmin.getId());

        if (!mensaje.isPresent()) {
            throw new Exception("Mensaje no encontrado");
        }

        mensaje.get().setAdminEmail(usuarioEmail);
        mensaje.get().setRespuesta(preguntasAdmin.getRespuesta());
        mensaje.get().setCerrado(true);
        mensajesRepo.save(mensaje.get());


    }
}
