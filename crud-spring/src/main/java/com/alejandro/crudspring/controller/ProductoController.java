package com.alejandro.crudspring.controller;

import com.alejandro.crudspring.entity.Producto;
import com.alejandro.crudspring.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.security.Principal;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("/productos")
    public String listarProductos(@PageableDefault(size = 10) Pageable pageable, Model model, Principal principal) {

        Page<Producto> pagina = productoService.listarProductos(pageable);

        model.addAttribute("productos", pagina.getContent());
        model.addAttribute("totalItems", pagina.getTotalElements());
        model.addAttribute("totalPages", pagina.getTotalPages());
        model.addAttribute("currentPage", pagina.getNumber());


        if (principal != null) {
            model.addAttribute("nombreUsuario", principal.getName());
        }

        return "lista_productos";
    }


    @GetMapping("/productos/nuevo")
    public String mostrarFormularioDeRegistrar(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        return "crear_producto";
    }


    @PostMapping("/productos")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos";
    }


    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.obtenerProductoPorId(id));
        return "editar_producto";
    }


    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }
}