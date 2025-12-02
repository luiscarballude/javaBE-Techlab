package com.techlab.demo.service;

import com.techlab.demo.exception.ProductoNoEncontradoException;
import com.techlab.demo.model.Producto;
import com.techlab.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // === LÓGICA CRUD ===

    public Producto agregarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado."));
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto productoExistente = buscarProductoPorId(id);

        if (productoActualizado.getNombre() != null && !productoActualizado.getNombre().trim().isEmpty()) {
            productoExistente.setNombre(productoActualizado.getNombre());
        }
        if (productoActualizado.getPrecio() != null && productoActualizado.getPrecio() > 0) {
            productoExistente.setPrecio(productoActualizado.getPrecio());
        }
        if (productoActualizado.getStock() != null && productoActualizado.getStock() >= 0) {
            productoExistente.setStock(productoActualizado.getStock());
        }

        return productoRepository.save(productoExistente);
    }

    public void eliminarProducto(Long id) {
        Producto producto = buscarProductoPorId(id);
        productoRepository.delete(producto);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarConStockBajo(Integer stockMinimo) {
        return productoRepository.findByStockLessThan(stockMinimo);
    }
}
