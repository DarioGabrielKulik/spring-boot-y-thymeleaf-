package com.egg.proyecto.Bblioteca.repositorios;

import com.egg.proyecto.Bblioteca.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {


}
