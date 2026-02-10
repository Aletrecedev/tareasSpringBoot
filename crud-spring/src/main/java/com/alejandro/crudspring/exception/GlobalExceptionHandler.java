package com.alejandro.crudspring.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Error: Usuario no encontrado
    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUserNotFound(UsernameNotFoundException ex, Model model, HttpServletRequest req) {
        model.addAttribute("titulo", "Usuario no encontrado");
        model.addAttribute("mensaje", ex.getMessage());
        model.addAttribute("path", req.getRequestURI());
        model.addAttribute("status", 404);
        return "error";
    }

    // 2. Error: Argumentos inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException ex, Model model, HttpServletRequest req) {
        model.addAttribute("titulo", "Petición no válida");
        model.addAttribute("mensaje", ex.getMessage());
        model.addAttribute("path", req.getRequestURI());
        model.addAttribute("status", 400);
        return "error";
    }

    // 3. Error Genérico: Cualquier otra cosa que falle
    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model, HttpServletRequest req) {
        model.addAttribute("titulo", "Error inesperado");
        model.addAttribute("mensaje", "Ha ocurrido un error. Inténtalo más tarde.");
        // mostrar el mensaje real del error para depurar (ex.getMessage())
        model.addAttribute("path", req.getRequestURI());
        model.addAttribute("status", 500);
        return "error";
    }
}