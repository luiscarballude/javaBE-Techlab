package com.techlab.main;

import com.techlab.productos.Producto;
import com.techlab.excepciones.ProductoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
  // Colección principal: ArrayList para manejar los productos
  private List<Producto> productos;

  public ProductoService() {
    this.productos = new ArrayList<>();
  }

  // === LÓGICA CRUD ===

  public void agregarProducto(Producto producto) {
    this.productos.add(producto);
  }

  public List<Producto> listarProductos() {
    return productos;
  }

  public Producto buscarProductoPorId(int id) throws ProductoNoEncontradoException {
    for (Producto p : productos) {
      if (p.getId() == id) {
        return p;
      }
    }
    throw new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado.");
  }

  public void actualizarProducto(int id, String nuevoNombre, double nuevoPrecio, int nuevoStock)
      throws ProductoNoEncontradoException {
    Producto productoAActualizar = buscarProductoPorId(id);

    if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
      productoAActualizar.setNombre(nuevoNombre);
    }
    if (nuevoPrecio > 0) {
      productoAActualizar.setPrecio(nuevoPrecio);
    }
    if (nuevoStock >= 0) {
      productoAActualizar.setStock(nuevoStock);
    }
  }

  public void eliminarProducto(int id) throws ProductoNoEncontradoException {
    Producto productoAEliminar = buscarProductoPorId(id);
    this.productos.remove(productoAEliminar);
  }
}