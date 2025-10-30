/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    /*
     * Relaciones OO en Pedido:
     * - Composición: Pedido está compuesto por LineaPedido. Las líneas sólo existen mientras existe el Pedido.
     * - Asociación: Pedido se asocia a Cliente, Empleado y Mesa (no es dueño de su ciclo de vida).
     */
    private final int id;
    private final Cliente cliente;   // asociación
    private final Empleado empleado; // asociación
    private final Mesa mesa;         // asociación
    private final List<LineaPedido> lineas = new ArrayList<>(); // composición

    public Pedido(int id, Cliente cliente, Empleado empleado, Mesa mesa) {
        if (id <= 0) throw new IllegalArgumentException("Id de pedido debe ser > 0");
        if (cliente == null) throw new IllegalArgumentException("Cliente requerido");
        if (empleado == null) throw new IllegalArgumentException("Empleado requerido");
        if (mesa == null) throw new IllegalArgumentException("Mesa requerida");
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.mesa = mesa;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Empleado getEmpleado() { return empleado; }
    public Mesa getMesa() { return mesa; }
    public List<LineaPedido> getLineas() { return Collections.unmodifiableList(lineas); }

    public boolean agregarProducto(Producto p, int cantidad) {
        if (p == null || cantidad <= 0) return false;
        if (!p.disminuirStock(cantidad)) {
            return false; // no hay stock suficiente
        }
        lineas.add(new LineaPedido(p, cantidad));
        return true;
    }

    public double getTotal() {
        double total = 0.0;
        for (LineaPedido lp : lineas) {
            total += lp.getSubtotal();
        }
        return total;
    }

    public String detalleComoTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id)
          .append(" | Cliente: ").append(cliente.getNombre())
          .append(" | Empleado: ").append(empleado.getNombre())
          .append(" | Mesa: ").append(mesa.getNumero())
          .append("\nProductos:\n");

        for (LineaPedido lp : lineas) {
            sb.append(" - ").append(lp.getProducto().getNombre())
              .append(" x").append(lp.getCantidad())
              .append(" = $").append(lp.getSubtotal())
              .append("\n");
        }

        sb.append("Total: $").append(getTotal());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " (" + lineas.size() + " items)";
    }
}

