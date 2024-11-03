package com.egg.proyecto.Bblioteca.repositorios;

import com.egg.proyecto.Bblioteca.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
}
