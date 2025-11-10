package com.mycompany.sistemapos.excepciones;

import com.mycompany.sistemapos.Mesa;

/**
 * Excepción lanzada cuando se intenta usar una mesa que ya está ocupada
 */
public class MesaOcupadaException extends Exception {
    private Mesa mesa;
    
    public MesaOcupadaException(Mesa mesa) {
        super(String.format("La mesa %d está %s y no puede ser utilizada", 
            mesa.getNumero(), mesa.getEstado()));
        this.mesa = mesa;
    }
    
    public Mesa getMesa() {
        return mesa;
    }
}




