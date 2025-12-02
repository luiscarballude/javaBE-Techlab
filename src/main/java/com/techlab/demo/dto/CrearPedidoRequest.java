package com.techlab.demo.dto;

import java.util.List;

public class CrearPedidoRequest {
    
    private List<LineaPedidoRequest> lineas;

    public CrearPedidoRequest() {
    }

    public CrearPedidoRequest(List<LineaPedidoRequest> lineas) {
        this.lineas = lineas;
    }

    public List<LineaPedidoRequest> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaPedidoRequest> lineas) {
        this.lineas = lineas;
    }

    public static class LineaPedidoRequest {
        private Long productoId;
        private Integer cantidad;

        public LineaPedidoRequest() {
        }

        public LineaPedidoRequest(Long productoId, Integer cantidad) {
            this.productoId = productoId;
            this.cantidad = cantidad;
        }

        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }
}
