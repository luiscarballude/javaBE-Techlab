package com.techlab.demo.repository;

import com.techlab.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Método personalizado para buscar productos por nombre (búsqueda parcial)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Método personalizado para buscar productos con stock bajo
    List<Producto> findByStockLessThan(Integer stock);
}
