package com.techlab.demo;

import com.techlab.demo.model.Producto;
import com.techlab.demo.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository) {
        return args -> {
            System.out.println("🚀 Inicializando datos de prueba...");
            
            // Cargar los productos iniciales
            productoRepository.save(new Producto("Laptop IBM", 1200.0, 5));
            productoRepository.save(new Producto("Laptop HP", 1200.0, 5));
            productoRepository.save(new Producto("Teclado Mecanico", 80.0, 15));
            productoRepository.save(new Producto("Teclado Bluethooth", 120.0, 10));
            productoRepository.save(new Producto("Teclado Inalambrico", 80.0, 15));
            
            System.out.println("✅ Datos iniciales cargados correctamente");
            System.out.println("📊 Total de productos: " + productoRepository.count());
            System.out.println("🌐 API disponible en: http://localhost:8083");
            System.out.println("💾 Consola H2 disponible en: http://localhost:8083/h2-console");
            System.out.println("   JDBC URL: jdbc:h2:mem:techlab_db");
            System.out.println("   Username: sa");
            System.out.println("   Password: (dejar vacío)");
        };
    }
}
