package com.egg.proyecto.Bblioteca.controladores;


import com.egg.proyecto.Bblioteca.entidades.Editorial;
import com.egg.proyecto.Bblioteca.excepciones.MiExcepciones;
import com.egg.proyecto.Bblioteca.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(){
        return "editorial-form";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){

        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "Se registro la Editorial con éxito");
            return "editorial-form";
        } catch (MiExcepciones e) {
            modelo.put("error", e.getMessage());
            return "editorial-form";
        }

    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Editorial> editoriales = new ArrayList<>();
        editoriales = editorialServicio.listaEditoriales();

        modelo.addAttribute("editoriales", editoriales);
        return "editorial-lista";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialServicio.buscarEditorial(id));
        return "editorial-editar";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            editorialServicio.modificarEditorial(id, nombre);
            modelo.put("exito","Se modifico exitosamente la editorial");
            return "redirect:../lista";
        } catch (MiExcepciones e) {
            modelo.put("error", e.getMessage());
            return "redirect:../lista";
        }
    }
}
