package com.proyectotitulo.springbootproyectotitulo.controlador;

import com.proyectotitulo.springbootproyectotitulo.Utilidad.JWT;
import com.proyectotitulo.springbootproyectotitulo.entity.Mensajes;
import com.proyectotitulo.springbootproyectotitulo.modeloPeticiones.PreguntasAdmin;
import com.proyectotitulo.springbootproyectotitulo.services.MensajesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/mensajeses")
public class MensajesControlador {

    private MensajesService mensajesService;

    @Autowired
    public MensajesControlador(MensajesService mensajesService) {
        this.mensajesService = mensajesService;
    }

    @RequestMapping(value = "/confidencial/enviar/mensaje", method = RequestMethod.POST)
    public void enviarMensaje(@RequestHeader(value = "Authorization") String token,
                              @RequestBody Mensajes mensajeUsuario) {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");
        mensajesService.enviarMensaje(mensajeUsuario, usuarioEmail);
    }

    @RequestMapping(value = "/confidencial/admin/mensaje", method = RequestMethod.PUT)
    public void responderMensaje(@RequestHeader(value = "Authorization") String token,
                                 @RequestBody PreguntasAdmin preguntasAdmin) throws Exception {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");
        String admin = JWT.procesandoJWT(token, "\"usuarioRol\"");

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Ingreso no autorizado");
        }

        mensajesService.responderMensaje(preguntasAdmin, usuarioEmail);
    }

}
