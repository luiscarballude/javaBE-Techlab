package com.techlab.productos;

public class Producto {

  private int id;
  private String nombre;
  private double precio;
  private int stock;

  // Para generar IDs unicos
  private static int contadorId = 1;

  // Constructor
  public Producto(String nombre, double precio, int stock) {
    this.id = contadorId++; // Asigna el ID y luego lo incrementa
    this.nombre = nombre;
    this.precio = precio;
    this.stock = stock;
  }

  // Getters y Setters
  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    if (stock < 0) {
      this.stock = 0;
    } else {
      this.stock = stock;
    }
  }

  public void descontarStock(int cantidad) {
    this.stock -= cantidad;
  }

  public void reponerStock(int cantidad) {
    this.stock += cantidad;
  }

  // Sobreescribe toString para una representación amigable
  @Override
  public String toString() {
    return String.format("ID: %d | Nombre: %s | Precio: %.2f | Stock: %d",
        id, nombre, precio, stock);
  }

}
