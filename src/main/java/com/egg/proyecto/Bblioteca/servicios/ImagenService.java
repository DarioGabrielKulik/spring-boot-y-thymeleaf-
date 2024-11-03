package com.egg.proyecto.Bblioteca.servicios;

import com.egg.proyecto.Bblioteca.entidades.Imagen;
import com.egg.proyecto.Bblioteca.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
public class ImagenService {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardarImagen(MultipartFile archivo)  {

        if(archivo != null){
            try{
                Imagen imagen = new Imagen();
                imagen.setMine(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }

        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String id){

        if(archivo != null){
            try{
                Imagen imagen = new Imagen();

                if(id != null){
                    Optional<Imagen> response = imagenRepositorio.findById(id);
                    if(response.isPresent()){
                        imagen = response.get();
                    }
                }

                imagen.setMine(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }

        return null;


    }
}
