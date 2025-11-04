package com.mycompany.sistemapos.excepciones;

/**
 * Excepción lanzada cuando se intenta procesar un pedido sin productos
 */
public class PedidoVacioException extends Exception {
    public PedidoVacioException() {
        super("El pedido está vacío. Debe contener al menos un producto.");
    }
    
    public PedidoVacioException(String mensaje) {
        super(mensaje);
    }
}

