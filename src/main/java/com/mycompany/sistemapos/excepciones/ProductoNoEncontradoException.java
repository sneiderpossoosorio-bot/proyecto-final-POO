package com.mycompany.sistemapos.excepciones;

/**
 * Excepción lanzada cuando no se encuentra un producto en el inventario
 */
public class ProductoNoEncontradoException extends Exception {
    private String nombreProducto;
    
    public ProductoNoEncontradoException(String nombreProducto) {
        super(String.format("Producto '%s' no encontrado en el inventario", nombreProducto));
        this.nombreProducto = nombreProducto;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }
}

