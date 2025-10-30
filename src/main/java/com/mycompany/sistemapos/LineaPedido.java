/*
 * Clase LineaPedido
 *
 * Propósito y patrones:
 * - Reemplaza el uso de Producto clonado dentro de Pedido.
 * - Mantiene una asociación fuerte entre un Producto (del inventario) y la cantidad pedida.
 * - Facilita el cálculo de subtotales y el total del pedido sin duplicar información.
 *
 * Relaciones OO:
 * - Asociación: LineaPedido se asocia a un Producto existente (no es dueño del ciclo de vida del Producto).
 * - Composición: LineaPedido forma parte de Pedido. Si el Pedido deja de existir, sus líneas también
 *   (esto se documenta en Pedido).
 */
package com.mycompany.sistemapos;

import java.util.Objects;

public class LineaPedido {
    private final Producto producto;   // referencia al producto del inventario (asociación)
    private final int cantidad;        // cantidad pedida, inmutable para simplificar

    public LineaPedido(Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("Producto no puede ser nulo");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser > 0");
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public double getSubtotal() {
        // Simplicidad para proyecto básico: usamos double
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " x" + cantidad + " = $" + getSubtotal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineaPedido)) return false;
        LineaPedido that = (LineaPedido) o;
        return cantidad == that.cantidad && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, cantidad);
    }
}
