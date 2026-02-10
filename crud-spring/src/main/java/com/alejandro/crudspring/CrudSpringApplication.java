package com.alejandro.crudspring;

import com.alejandro.crudspring.entity.Producto;
import com.alejandro.crudspring.entity.Rol;
import com.alejandro.crudspring.entity.Usuario;
import com.alejandro.crudspring.repository.ProductoRepository;
import com.alejandro.crudspring.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CrudSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ProductoRepository productoRepository,
                                      UsuarioRepository usuarioRepository,
                                      PasswordEncoder passwordEncoder) {
        return (args) -> {

            // 1. CREAR PRODUCTOS
            if (productoRepository.count() == 0) {

                String[] nombresBase = {"Manzana", "Pera", "Plátano", "Fresa", "Naranja", "Kiwi", "Piña", "Melón", "Sandía", "Uva"};
                String[] apellidosBase = {"Golden", "Verde", "Madura", "Dulce", "Ácida", "Importada", "Nacional", "Bio", "Premium", "Oferta"};

                for (int i = 0; i < 50; i++) {
                    Producto p = new Producto();
                    // Crea nombres aleatorios tipo "Manzana Premium", "Pera Dulce"...
                    String nombreAleatorio = nombresBase[i % nombresBase.length] + " " + apellidosBase[(i + 2) % apellidosBase.length];

                    p.setNombre(nombreAleatorio + " " + (i + 1));
                    p.setPrecio(Math.round((Math.random() * 20 + 1) * 100.0) / 100.0);
                    p.setStock((int) (Math.random() * 100 + 10));
                    productoRepository.save(p);
                }
                System.out.println("✅ ¡50 Productos creados! La paginación va a quedar genial.");
            }

            // 2. CREAR USUARIOS DE SEGURIDAD
            if (usuarioRepository.count() == 0) {
                // Crear ADMIN
                Usuario admin = new Usuario();
                admin.setNombre("admin");
                admin.setContrasena(passwordEncoder.encode("admin"));
                admin.setRol(Rol.ADMIN);
                usuarioRepository.save(admin);

                // Crear USUARIO normal
                Usuario user = new Usuario();
                user.setNombre("user");
                user.setContrasena(passwordEncoder.encode("user"));
                user.setRol(Rol.USUARIO);
                usuarioRepository.save(user);

                System.out.println("✅ Usuarios creados: admin/admin y user/user");
            }
        };
    }
}