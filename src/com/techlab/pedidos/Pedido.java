package com.techlab.pedidos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
  private int id;
  private List<LineaPedido> lineasPedido; // Colección de LineaPedido
  private double total;

  private static int contadorId = 1;

  public Pedido() {
    this.id = contadorId++;
    this.lineasPedido = new ArrayList<>();
    this.total = 0.0;
  }

  public int getId() {
    return id;
  }

  public void agregarLinea(LineaPedido linea) {
    this.lineasPedido.add(linea);
  }

  // Método para calcular el total del pedido
  public double calcularTotal() {
    double suma = 0.0;
    // Uso de bucle for-each
    for (LineaPedido linea : lineasPedido) {
      suma += linea.calcularSubtotal();
    }
    this.total = suma;
    return total;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("=============== PEDIDO #%d ===============\n", id));

    // Muestra los productos del pedido
    for (LineaPedido linea : lineasPedido) {
      sb.append(linea.toString()).append("\n");
    }

    // Cálculo y muestra del total
    sb.append(String.format("=========================================\n"));
    sb.append(String.format("TOTAL FINAL DEL PEDIDO: $%.2f\n", calcularTotal()));
    sb.append(String.format("=========================================\n"));
    return sb.toString();
  }
}