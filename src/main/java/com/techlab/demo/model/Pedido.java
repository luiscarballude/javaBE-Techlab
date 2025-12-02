package com.techlab.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LineaPedido> lineasPedido;

    @Column(nullable = false)
    private Double total;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // Constructor vacío requerido por JPA
    public Pedido() {
        this.lineasPedido = new ArrayList<>();
        this.total = 0.0;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Método para agregar una línea al pedido
    public void agregarLinea(LineaPedido linea) {
        this.lineasPedido.add(linea);
        linea.setPedido(this);
    }

    // Método para calcular el total del pedido
    public Double calcularTotal() {
        double suma = 0.0;
        for (LineaPedido linea : lineasPedido) {
            suma += linea.calcularSubtotal();
        }
        this.total = suma;
        return total;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        calcularTotal();
    }

    @PreUpdate
    protected void onUpdate() {
        calcularTotal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=============== PEDIDO #%d ===============\n", id));
        sb.append(String.format("Fecha: %s\n", fechaCreacion));

        for (LineaPedido linea : lineasPedido) {
            sb.append(linea.toString()).append("\n");
        }

        sb.append(String.format("=========================================\n"));
        sb.append(String.format("TOTAL FINAL DEL PEDIDO: $%.2f\n", total));
        sb.append(String.format("=========================================\n"));
        return sb.toString();
    }
}
