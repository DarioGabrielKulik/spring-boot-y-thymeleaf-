package com.egg.proyecto.Bblioteca.servicios;

import com.egg.proyecto.Bblioteca.entidades.Autor;
import com.egg.proyecto.Bblioteca.excepciones.MiExcepciones;
import com.egg.proyecto.Bblioteca.repositorios.AutorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiExcepciones{

        validar(nombre);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    public List<Autor> listaAutores(){
        List<Autor> autores = new ArrayList<>();
        autores = autorRepositorio.findAll();
        return autores;
    }

    public void modificarAutor(String idAutor, String nombre ) throws MiExcepciones{

        validar(nombre);

        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Autor autor= new Autor();

        if(respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
            autor.setNombre(nombre);

            autorRepositorio.save(autor);
        }
    }

    public void validar(String nombre) throws MiExcepciones{
        if(nombre.isEmpty() || nombre == null){
            throw new MiExcepciones("El nombre no puede estar vacío o ser nulo");
        }
    }

    public Autor buscarAutor(String idAutor ){
        Autor autor = autorRepositorio.findById(idAutor).get();
        return autor;
    }
}

