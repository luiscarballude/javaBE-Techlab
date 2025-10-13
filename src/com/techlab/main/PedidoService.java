package com.techlab.main;

import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.LineaPedido;
import com.techlab.productos.Producto;
import com.techlab.excepciones.StockInsuficienteException;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
  private List<Pedido> pedidos;
  private ProductoService productoService;

  public PedidoService(ProductoService productoService) {
    this.pedidos = new ArrayList<>();
    this.productoService = productoService;
  }

  // Método para crear un nuevo pedido
  public Pedido crearPedido(List<LineaPedido> lineas) throws StockInsuficienteException {
    // 1. Validar Stock para todas las líneas
    for (LineaPedido linea : lineas) {
      Producto producto = linea.getProducto();
      int cantidadSolicitada = linea.getCantidad();

      if (cantidadSolicitada > producto.getStock()) {
        // Lanza la excepción personalizada si el stock es insuficiente
        throw new StockInsuficienteException(
            "Stock insuficiente para " + producto.getNombre() + ". Stock disponible: " + producto.getStock()
        );
      }
    }

    // 2. Si el stock es suficiente, crear el pedido y descontar stock
    Pedido nuevoPedido = new Pedido();
    for (LineaPedido linea : lineas) {
      Producto producto = linea.getProducto();
      int cantidadSolicitada = linea.getCantidad();

      producto.descontarStock(cantidadSolicitada);

      nuevoPedido.agregarLinea(linea);
    }

    // 3. Finalizar y guardar el pedido
    nuevoPedido.calcularTotal();
    this.pedidos.add(nuevoPedido);

    return nuevoPedido;
  }

  // Listar pedidos
  public List<Pedido> listarPedidos() {
    return pedidos;
  }
}