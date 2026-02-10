package com.alejandro.crudspring;

import com.alejandro.crudspring.entity.Producto;
import com.alejandro.crudspring.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ProductoRepository repository) {
        return (args) -> {

            if (repository.count() == 0) {

                String[] nombres = {
                        "Manzanas Golden", "Plátanos de Canarias", "Peras Conferencia",
                        "Fresas de Huelva", "Naranjas de Valencia", "Lechuga Iceberg",
                        "Tomates Raf", "Zanahorias", "Patatas Gallegas", "Cebollas",
                        "Leche Entera", "Huevos L", "Pan de Molde", "Arroz Redondo",
                        "Pasta Macarrones", "Aceite de Oliva", "Vinagre Balsámico",
                        "Sal Yodada", "Azúcar Moreno", "Café Molido"
                };


                for (String nombre : nombres) {
                    Producto p = new Producto();
                    p.setNombre(nombre);
                    p.setPrecio(Math.round((Math.random() * 10 + 1) * 100.0) / 100.0);
                    p.setStock((int) (Math.random() * 100 + 10));

                    repository.save(p);
                }

                System.out.println("✅ ¡20 productos creados !");
            }
        };
    }

}
