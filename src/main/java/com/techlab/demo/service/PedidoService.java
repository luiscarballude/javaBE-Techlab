package com.techlab.demo.service;

import com.techlab.demo.dto.CrearPedidoRequest;
import com.techlab.demo.exception.StockInsuficienteException;
import com.techlab.demo.model.LineaPedido;
import com.techlab.demo.model.Pedido;
import com.techlab.demo.model.Producto;
import com.techlab.demo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ProductoService productoService) {
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
    }

    // Método para crear un nuevo pedido desde un DTO
    public Pedido crearPedidoDesdeRequest(CrearPedidoRequest request) {
        List<LineaPedido> lineas = new ArrayList<>();
        
        for (CrearPedidoRequest.LineaPedidoRequest lineaRequest : request.getLineas()) {
            Producto producto = productoService.buscarProductoPorId(lineaRequest.getProductoId());
            LineaPedido linea = new LineaPedido(producto, lineaRequest.getCantidad());
            lineas.add(linea);
        }
        
        return crearPedido(lineas);
    }

    // Método para crear un nuevo pedido
    public Pedido crearPedido(List<LineaPedido> lineas) {
        // 1. Validar Stock para todas las líneas
        for (LineaPedido linea : lineas) {
            Producto producto = productoService.buscarProductoPorId(linea.getProducto().getId());
            int cantidadSolicitada = linea.getCantidad();

            if (cantidadSolicitada > producto.getStock()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para " + producto.getNombre() + 
                        ". Stock disponible: " + producto.getStock()
                );
            }
        }

        // 2. Si el stock es suficiente, crear el pedido y descontar stock
        Pedido nuevoPedido = new Pedido();
        
        for (LineaPedido linea : lineas) {
            Producto producto = productoService.buscarProductoPorId(linea.getProducto().getId());
            int cantidadSolicitada = linea.getCantidad();

            // Descontar stock
            producto.descontarStock(cantidadSolicitada);
            productoService.agregarProducto(producto);

            // Actualizar la referencia del producto en la línea
            linea.setProducto(producto);
            
            // Agregar la línea al pedido
            nuevoPedido.agregarLinea(linea);
        }

        // 3. Finalizar y guardar el pedido
        nuevoPedido.calcularTotal();
        return pedidoRepository.save(nuevoPedido);
    }

    // Listar todos los pedidos
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAllByOrderByFechaCreacionDesc();
    }

    // Buscar pedido por ID
    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado."));
    }
}
