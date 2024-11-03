package com.egg.proyecto.Bblioteca.repositorios;

import com.egg.proyecto.Bblioteca.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
}
