/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;


/**
 * Producto con stock (inventario en tiempo real) y contador de vendidos.
 * - stock: cantidad disponible en tiempo real
 * - vendidos: acumulado (usado para reportes)
 *
 * El método descontarStock hace la validación: no permite vender más de lo disponible.
 */
public class Producto {
    private String nombre;
    private double precio;
    private int stock;
    private int vendidos;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.vendidos = 0;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public int getVendidos() { return vendidos; }

    /**
     * Intenta descontar 'cantidad' del stock.
     * Devuelve true si se pudo descontar, false si no hay suficiente stock.
     * También incrementa 'vendidos' cuando la venta es exitosa.
     */
    public boolean descontarStock(int cantidad) {
        if (cantidad <= 0) return false;
        if (cantidad <= stock) {
            stock -= cantidad;
            vendidos += cantidad;
            return true;
        }
        return false;
    }

    /**
     * Reponer stock (útil para administración).
     */
    public void reponer(int cantidad) {
        if (cantidad > 0) stock += cantidad;
    }

    public void mostrarProducto() {
        String estado = stock > 0 ? ("Stock: " + stock) : "AGOTADO";
        System.out.println(nombre + " - $" + (int)precio + " - " + estado + " (Vendidos: " + vendidos + ")");
    }
}
