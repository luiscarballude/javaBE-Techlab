package com.techlab.pedidos;

import com.techlab.productos.Producto;

// Representa un ítem dentro de un pedido
public class LineaPedido {
  private Producto producto;
  private int cantidad;

  public LineaPedido(Producto producto, int cantidad) {
    this.producto = producto;
    this.cantidad = cantidad;
  }

  public Producto getProducto() {
    return producto;
  }

  public int getCantidad() {
    return cantidad;
  }

  // Método para calcular el subtotal de esta línea
  public double calcularSubtotal() {
    return producto.getPrecio() * cantidad;
  }

  @Override
  public String toString() {
    return String.format("  -> Producto: %s | Cantidad: %d | Subtotal: %.2f",
        producto.getNombre(), cantidad, calcularSubtotal());
  }
}