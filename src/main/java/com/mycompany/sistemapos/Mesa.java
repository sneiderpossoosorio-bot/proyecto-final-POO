/*
 * Clase Mesa (refactorizada)
 *
 * Cambios clave:
 * - Reemplazo del tipo String por enum EstadoMesa (tipo seguro) para el estado.
 * - Validaciones básicas del constructor.
 *
 * Relaciones OO:
 * - No tiene composición aquí; participa en asociaciones con Pedido (un Pedido se asocia a una Mesa).
 */
package com.mycompany.sistemapos;

public class Mesa {
    private final int numero;
    private EstadoMesa estado;

    public Mesa(int numero, EstadoMesa estado) {
        if (numero <= 0) throw new IllegalArgumentException("El número de mesa debe ser > 0");
        if (estado == null) throw new IllegalArgumentException("El estado no puede ser nulo");
        this.numero = numero;
        this.estado = estado;
    }

    public int getNumero() { return numero; }
    public EstadoMesa getEstado() { return estado; }
    public void setEstado(EstadoMesa estado) {
        if (estado == null) throw new IllegalArgumentException("El estado no puede ser nulo");
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Mesa " + numero + " - Estado: " + estado;
    }
}