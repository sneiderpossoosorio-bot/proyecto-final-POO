package com.mycompany.sistemapos.excepciones;

/**
 * Excepción lanzada cuando se intenta procesar un pago inválido
 */
public class PagoInvalidoException extends Exception {
    private double monto;
    
    public PagoInvalidoException(double monto) {
        super(String.format("El monto de pago es invalido: $%.2f", monto));
        this.monto = monto;
    }
    
    public PagoInvalidoException(String mensaje) {
        super(mensaje);
    }
    
    public double getMonto() {
        return monto;
    }
}




