package com.egg.proyecto.Bblioteca.servicios;

import com.egg.proyecto.Bblioteca.entidades.Editorial;
import com.egg.proyecto.Bblioteca.excepciones.MiExcepciones;
import com.egg.proyecto.Bblioteca.repositorios.EditorialRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiExcepciones{

        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listaEditoriales(){
        List<Editorial> editoriales = new ArrayList<>();
        editoriales = editorialRepositorio.findAll();

        return  editoriales;
    }

    public void modificarEditorial(String idEditorial, String nombre) throws MiExcepciones{

        validar(nombre);

        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        Editorial editorial = new Editorial();

        if (respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }

    }

    public Editorial buscarEditorial(String id){
        return editorialRepositorio.findById(id).get();
    }

    public void validar( String nombre) throws MiExcepciones{

        if (nombre.isEmpty() || nombre == null){
            throw new MiExcepciones("El nombre no puede estar vacío o ser nulo");
        }

    }
}
