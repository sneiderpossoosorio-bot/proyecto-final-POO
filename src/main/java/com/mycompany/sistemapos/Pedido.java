/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;



import java.util.HashMap;
import java.util.Map;

/**
 * Pedido representa una venta en curso:
 * - Tiene asociación con Cliente, Empleado y Mesa (no los crea, solo los referencia).
 * - Mantiene un mapa (Producto -> cantidad) con los ítems del pedido.
 * - Al agregar un producto validamos y descontamos stock (inventario en tiempo real).
 *
 * Notas de diseño:
 * - Relación con Producto: agregación/uso (el Producto existe en Inventario).
 * - Cuando se agrega un producto llamamos a Producto.descontarStock(cant) (ese método actualiza stock y vendidos).
 */
public class Pedido {
    private Cliente cliente;
    private Empleado empleado;
    private Mesa mesa;
    private Map<Producto, Integer> items = new HashMap<>();
    private double total = 0.0;

    public Pedido(Cliente cliente, Empleado empleado, Mesa mesa) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.mesa = mesa;
        // Al iniciar un pedido se ocupa la mesa (si está libre) — ver validación en Main.
        this.mesa.ocupar();
    }

    /**
     * Intenta agregar 'cantidad' del producto al pedido.
     * Devuelve true si se pudo (stock suficiente), false si no hay stock.
     * Actualiza el total del pedido.
     */
    public boolean agregarProducto(Producto producto, int cantidad) {
        if (producto.descontarStock(cantidad)) {                    // valida y descuenta stock y aumenta vendidos
            items.put(producto, items.getOrDefault(producto, 0) + cantidad);
            total += producto.getPrecio() * cantidad;
            return true;
        }
        return false;
    }

    public Map<Producto, Integer> getItems() { return items; }
    public double getTotal() { return total; }
    public Cliente getCliente() { return cliente; }
    public Empleado getEmpleado() { return empleado; }
    public Mesa getMesa() { return mesa; }

    /**
     * Imprime un resumen del pedido (usado por Factura).
     */
    public void mostrarDetalle() {
        System.out.println("\n--- Detalle del pedido ---");
        for (Map.Entry<Producto, Integer> e : items.entrySet()) {
            Producto p = e.getKey();
            int cant = e.getValue();
            System.out.println(p.getNombre() + " x" + cant + " = $" + (int)(p.getPrecio()*cant));
        }
        System.out.println("TOTAL: $" + (int)total);
    }

    /**
     * Cierra el pedido y libera la mesa.
     * (En la práctica cobrar se hace en Main con Caja.cobrar).
     */
    public void cerrarPedido() {
        mesa.liberar();
    }
}