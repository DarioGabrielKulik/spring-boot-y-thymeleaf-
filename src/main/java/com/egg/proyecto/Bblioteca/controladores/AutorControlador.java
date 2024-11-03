package com.egg.proyecto.Bblioteca.controladores;

import com.egg.proyecto.Bblioteca.entidades.Autor;
import com.egg.proyecto.Bblioteca.excepciones.MiExcepciones;
import com.egg.proyecto.Bblioteca.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String crearAutor(){
        return "autor-form";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "Se registro con exito el Autor");
            return "autor-form";
        } catch (MiExcepciones e) {
            modelo.put("error", e.getMessage());
            return "autor-form";
        }


    }

    @GetMapping("/lista")
    private String lista( ModelMap modelo){
        List<Autor> autores = new ArrayList<>();
        autores = autorServicio.listaAutores();
        modelo.addAttribute("autores", autores);
        return "autor-lista";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorServicio.buscarAutor(id));
        return "autor-editar";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            autorServicio.modificarAutor(id, nombre);
            modelo.put("exito","Se modifico exitosamente el autor");
            return "redirect:../lista";
        } catch (MiExcepciones e) {
            modelo.put("error", e.getMessage());
            return "redirect:../lista";
        }
    }
}
