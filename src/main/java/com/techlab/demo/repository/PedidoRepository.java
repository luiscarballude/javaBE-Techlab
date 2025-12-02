package com.techlab.demo.repository;

import com.techlab.demo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Método personalizado para buscar pedidos por rango de fechas
    List<Pedido> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Método personalizado para buscar pedidos ordenados por fecha descendente
    List<Pedido> findAllByOrderByFechaCreacionDesc();
}
