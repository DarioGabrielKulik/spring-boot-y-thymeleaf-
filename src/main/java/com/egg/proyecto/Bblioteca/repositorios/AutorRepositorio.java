package com.egg.proyecto.Bblioteca.repositorios;

import com.egg.proyecto.Bblioteca.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
}
