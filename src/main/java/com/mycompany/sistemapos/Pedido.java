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

public class Pedido {
    private int id;
    private Cliente cliente;
    private Empleado empleado;
    private Mesa mesa;
    private ArrayList<Producto> productos;
    private double total;

    public Pedido(int id, Cliente cliente, Empleado empleado, Mesa mesa) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.mesa = mesa;
        this.productos = new ArrayList<>();
        this.total = 0;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Empleado getEmpleado() { return empleado; }
    public Mesa getMesa() { return mesa; }
    public ArrayList<Producto> getProductos() { return productos; }
    public double getTotal() { return total; }

    public void agregarProducto(Producto p, int cantidad) {
        if (p.getCantidad() >= cantidad) {
            Producto productoPedido = new Producto(
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                cantidad
            );
            productos.add(productoPedido);
            total += p.getPrecio() * cantidad;
            p.restarCantidad(cantidad);
            System.out.println("Producto agregado: " + p.getNombre() + " x" + cantidad);
        } else {
            System.out.println("❌ No hay suficiente stock para " + p.getNombre());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id)
          .append(" | Cliente: ").append(cliente.getNombre())
          .append(" | Empleado: ").append(empleado.getNombre())
          .append(" | Mesa: ").append(mesa.getNumero())
          .append("\nProductos:\n");

        for (Producto p : productos) {
            sb.append(" - ").append(p.getNombre())
              .append(" x").append(p.getCantidad())
              .append(" = $").append(p.getPrecio() * p.getCantidad())
              .append("\n");
        }

        sb.append("Total: $").append(total);
        return sb.toString();
    }
}

