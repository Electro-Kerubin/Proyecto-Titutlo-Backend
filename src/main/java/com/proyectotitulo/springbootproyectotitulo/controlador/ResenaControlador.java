package com.proyectotitulo.springbootproyectotitulo.controlador;

import com.proyectotitulo.springbootproyectotitulo.Utilidad.JWT;
import com.proyectotitulo.springbootproyectotitulo.modeloPeticiones.ResenaPeticion;
import com.proyectotitulo.springbootproyectotitulo.services.ResenaService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/resenas")
public class ResenaControlador {

    private ResenaService resenaService;

    public ResenaControlador(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @RequestMapping(value = "/confidencial", method = RequestMethod.POST)
    public void postResena(@RequestHeader(value = "Authorization") String token,
                           @RequestBody ResenaPeticion resenaPeticion) throws Exception {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");

        if(usuarioEmail == null) {
            throw new Exception("Falta el usuarioEmail");
        }

        resenaService.postResena(usuarioEmail, resenaPeticion);
    }

    @RequestMapping(value = "/confidencial/existe", method = RequestMethod.GET)
    public Boolean existeResenaDeUsuario(@RequestHeader(value = "Authorization") String token,
                                         @RequestParam Long libroId) throws Exception {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");

        if (usuarioEmail == null) {
            throw new Exception("El usuario esta vacio");
        }

        return resenaService.existeResena(usuarioEmail, libroId);
    }

}
