package com.proyectotitulo.springbootproyectotitulo.controlador;

import com.proyectotitulo.springbootproyectotitulo.Utilidad.JWT;
import com.proyectotitulo.springbootproyectotitulo.modeloPeticiones.AñadirLibro;
import com.proyectotitulo.springbootproyectotitulo.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminControlador {

    private AdminService adminService;

    @Autowired
    public AdminControlador(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(value = "/confidencial/anadir/libro", method = RequestMethod.POST)
    public void anadirLibro(@RequestHeader(value = "Authorization") String token,
                            @RequestBody AñadirLibro añadirLibro) throws Exception {
        String admin = JWT.procesandoJWT(token, "\"usuarioRol\"");

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Autorizacion no permitida");
        }

        adminService.anadirNuevoLibro(añadirLibro);
    }

    @RequestMapping(value = "/confidencial/incrementar/libro", method = RequestMethod.PUT)
    public void incrementarCantidadLibro(@RequestHeader(value = "Authorization") String token,
                                         @RequestParam Long libroId) throws Exception {
        String admin = JWT.procesandoJWT(token, "\"usuarioRol\"");

        if(admin == null || !admin.equals("admin")) {
            throw new Exception("el rol de usuario no tiene permiso para realizar esta peticion");
        }

        adminService.aumentarCantidadLibro(libroId);
    }

    @RequestMapping(value = "/confidencial/disminuir/libro", method = RequestMethod.PUT)
    public void disminuirCantidadLibro(@RequestHeader(value = "Authorization") String token,
                                       @RequestParam Long libroId) throws Exception {
        String admin = JWT.procesandoJWT(token, "\"usuarioRol\"");

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("el rol de usuario no tiene permiso para realizar esta peticion");
        }

        adminService.disminuirCantidadLibro(libroId);
    }

    @RequestMapping(value = "/confidencial/eliminar/libro", method = RequestMethod.DELETE)
    public void eliminarLibro(@RequestHeader(value = "Authorization") String token,
                              @RequestParam Long libroId) throws Exception {

        String admin = JWT.procesandoJWT(token, "\"usuarioRol\"");

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("el rol de usuario no tiene permiso para realizar esta peticion");
        }

        adminService.eliminarLibro(libroId);
    }

}
