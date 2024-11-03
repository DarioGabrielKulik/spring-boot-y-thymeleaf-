package com.egg.proyecto.Bblioteca.controladores;


import com.egg.proyecto.Bblioteca.entidades.Autor;
import com.egg.proyecto.Bblioteca.entidades.Editorial;
import com.egg.proyecto.Bblioteca.entidades.Libro;
import com.egg.proyecto.Bblioteca.excepciones.MiExcepciones;
import com.egg.proyecto.Bblioteca.servicios.AutorServicio;
import com.egg.proyecto.Bblioteca.servicios.EditorialServicio;
import com.egg.proyecto.Bblioteca.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        List<Autor> autores = autorServicio.listaAutores();
        List<Editorial> editoriales = editorialServicio.listaEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        modelo.addAttribute("autores",autores);
        return "libro-form";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
                           @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
                           @RequestParam String idEditorial, ModelMap modelo){
        List<Autor> autores = autorServicio.listaAutores();
        List<Editorial> editoriales = editorialServicio.listaEditoriales();


        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.addAttribute("editoriales", editoriales);
            modelo.addAttribute("autores",autores);
            modelo.put("exito", "El libro fue cargado correctamente");
            return "libro-form";
        } catch (MiExcepciones e) {
           modelo.put("error", e.getMessage());
            modelo.addAttribute("editoriales", editoriales);
            modelo.addAttribute("autores",autores);
           return "libro-form";
        }

    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Libro> libros = new ArrayList<>();
        libros = libroServicio.buscarListaLibro();
        modelo.addAttribute("libros", libros);
        return "libro-lista";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo){
    modelo.put("autores", autorServicio.listaAutores());
    modelo.put("editoriales", editorialServicio.listaEditoriales());
    modelo.put("libro", libroServicio.buscarLibro(id));
    return "libro-editar";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial){

        try {
            libroServicio.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            return "redirect:../lista";
        } catch (MiExcepciones e) {
            return "redirect:../lista";
        }

    }
}
