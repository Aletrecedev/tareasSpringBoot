package com.alejandro.crudspring.service;

import com.alejandro.crudspring.entity.Producto;
import com.alejandro.crudspring.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;


    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }


    public Page<Producto> listarProductos(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }


    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }


    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }


    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}