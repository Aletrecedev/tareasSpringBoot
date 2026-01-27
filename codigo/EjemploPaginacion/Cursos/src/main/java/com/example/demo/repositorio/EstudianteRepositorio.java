package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidad.Estudiante;
@Repository
public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {
    // Método para buscar estudiantes por un criterio específico, por ejemplo, nombre
    List<Estudiante> findByNombre(String nombre);

    //Agregados para paginación
    Page<Estudiante> findAll(Pageable pageable);
    Page<Estudiante> findByNombre(String nombre, Pageable pageable);
    Page<Estudiante> findByNombreContaining(String nombre, Pageable pageable);
}
