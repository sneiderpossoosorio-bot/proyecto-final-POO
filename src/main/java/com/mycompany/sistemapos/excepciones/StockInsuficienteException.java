package com.mycompany.sistemapos.excepciones;

import com.mycompany.sistemapos.Producto;

/**
 * Excepción lanzada cuando no hay suficiente stock de un producto
 * para realizar una operación (venta, pedido, etc.)
 */
public class StockInsuficienteException extends Exception {
    private Producto producto;
    private int stockDisponible;
    private int cantidadSolicitada;
    
    public StockInsuficienteException(Producto producto, int cantidadSolicitada) {
        super(String.format("Stock insuficiente para '%s'. Disponible: %d, Solicitado: %d", 
            producto.getNombre(), producto.getStock(), cantidadSolicitada));
        this.producto = producto;
        this.stockDisponible = producto.getStock();
        this.cantidadSolicitada = cantidadSolicitada;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public int getStockDisponible() {
        return stockDisponible;
    }
    
    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }
}




