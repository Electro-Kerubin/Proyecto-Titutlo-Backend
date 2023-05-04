package com.proyectotitulo.springbootproyectotitulo.controlador;

import com.proyectotitulo.springbootproyectotitulo.Utilidad.JWT;
import com.proyectotitulo.springbootproyectotitulo.entity.Libro;
import com.proyectotitulo.springbootproyectotitulo.modeloRespuestas.PrestamosRespuesta;
import com.proyectotitulo.springbootproyectotitulo.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/libroes")
public class LibroControlador {

    private LibroService libroService;

    @Autowired
    public LibroControlador(LibroService libroService) {
        this.libroService = libroService;
    }

    @RequestMapping(value = "/confidencial/prestamosusuario/cantidad", method = RequestMethod.GET)
    public int cantidadPrestamosActuales(@RequestHeader(value = "Authorization") String token) {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");
        return libroService.cantidadPrestamosActuales(usuarioEmail);
    }

    @RequestMapping(value = "/confidencial/validarprestamo/usuario", method = RequestMethod.GET)
    public Boolean verificarPrestamo(@RequestHeader(value = "Authorization") String token, @RequestParam Long libroId) {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");
        return libroService.verificarPrestamo(usuarioEmail, libroId);
    }

    @RequestMapping(value = "/confidencial/listaprestamos", method = RequestMethod.GET)
    public List<PrestamosRespuesta> prestamosRespuestaList(@RequestHeader(value = "Authorization") String token) throws Exception {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");
        return libroService.listaPrestamosActuales(usuarioEmail);
    }

    @RequestMapping(value = "/confidencial/prestamo", method = RequestMethod.PUT)
    public Libro pedirPrestamoLibro(@RequestHeader(value = "Authorization") String token, @RequestParam Long libroId) throws Exception {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");
        return libroService.pedirPrestamoLibro(usuarioEmail, libroId);
    }

    @RequestMapping(value = "/confidencial/retorna/libro", method = RequestMethod.PUT)
    public void retornaLibro(@RequestHeader(value = "Authorization") String token,
                             @RequestParam Long libroId) throws Exception {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");
        libroService.retortaLibroPrestamo(usuarioEmail, libroId);
    }

    @RequestMapping(value = "/confidencial/renovar/prestamo", method = RequestMethod.PUT)
    public void renovarPrestamo(@RequestHeader(value = "Authorization") String token,
                                @RequestParam Long libroId) throws Exception {
        String usuarioEmail = JWT.procesandoJWT(token, "\"sub\"");
        libroService.renovarPrestamo(usuarioEmail, libroId);
    }

}
