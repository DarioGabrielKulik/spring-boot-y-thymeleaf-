package com.egg.proyecto.Bblioteca.servicios;

import com.egg.proyecto.Bblioteca.entidades.Autor;
import com.egg.proyecto.Bblioteca.entidades.Editorial;
import com.egg.proyecto.Bblioteca.entidades.Libro;
import com.egg.proyecto.Bblioteca.excepciones.MiExcepciones;
import com.egg.proyecto.Bblioteca.repositorios.AutorRepositorio;
import com.egg.proyecto.Bblioteca.repositorios.EditorialRepositorio;
import com.egg.proyecto.Bblioteca.repositorios.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepciones {

        validar(isbn, titulo, ejemplares,idAutor,idEditorial);

        Libro libro = new Libro();
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);

        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public List<Libro> buscarListaLibro(){
        List<Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();
        return libros;
    }

    public Libro buscarLibro(){
        return null;
    }

    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepciones {

        validar(isbn, titulo, ejemplares,idAutor,idEditorial);

        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();


        if(respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }

        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }

        if (respuestaLibro.isPresent()){
            Libro libro = respuestaLibro.get();

            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroRepositorio.save(libro);
        }

    }

    public void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepciones {

        if(isbn == null){
            throw new MiExcepciones("El ISBN es nulo");
        }

        if(titulo.isEmpty() || titulo == null){
            throw new MiExcepciones("El Tìtulo no puede estar nulo o vacío");
        }

        if(ejemplares == null){
            throw new MiExcepciones("Los ejemplares no puede esta nulo");
        }

        if(idAutor.isEmpty() || idAutor == null){
            throw new MiExcepciones("El id de autor no puede ser nulo");
        }

        if(idEditorial.isEmpty() || idEditorial == null){
            throw new MiExcepciones("El id de editorial no puede estar nulo o vacío");
        }
    }

    public Libro buscarLibro(Long isbn){
        return libroRepositorio.findById(isbn).get();
    }
}
