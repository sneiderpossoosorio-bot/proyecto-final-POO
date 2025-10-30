/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos;

/**
 *
 * @author janny
 */
// Clase Producto: representa los artículos del menú
public class Producto {
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    // Resta unidades del inventario
    public void vender(int cantidad) {
        if (cantidad <= stock) {
            stock -= cantidad;
        } else {
            System.out.println(" No hay suficiente stock para " + nombre);
        }
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + " (" + stock + " uds)";
    }
}

