package com.egg.proyecto.Bblioteca.controladores;


import com.egg.proyecto.Bblioteca.entidades.Usuario;
import com.egg.proyecto.Bblioteca.excepciones.MiExcepciones;
import com.egg.proyecto.Bblioteca.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControladores {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "registro";
    }

    @PostMapping("/registrar")
    private String registrar(@RequestParam String nombre,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String password2,
                             ModelMap modelo,
                             MultipartFile archivo
                             ){

        try {
            usuarioServicio.crearUsuario(archivo, nombre, email, password, password2);
            modelo.put("exito","Se registro correctamente");
            return "index";
        } catch (MiExcepciones e) {
            modelo.put("error",e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){
        if(error != null){
            modelo.put("error","Usuario o Contraseña incorrecto");
        }
        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            System.out.println(logueado.getRol().toString());
            return "redirect:/admin/dashboard";
        }

        return "inicio.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_editar";
    }

    @PostMapping("/perfil/{id}")
    public String perfil(MultipartFile archivo, @PathVariable String id,
                         @RequestParam String nombre,@RequestParam String email,
                        @RequestParam String password,@RequestParam String password2, ModelMap modelo){
        try {
            usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);
            modelo.put("exito", "Se actualizo correctamente los datos");
            return "inicio";
        } catch (MiExcepciones e) {
            modelo.put("error", e.getMessage());
            return "usuario_editar";
        }
    }
}
