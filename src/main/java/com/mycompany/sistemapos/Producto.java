/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */

public class Producto {
    private final int id;        // inmutable, identidad del producto
    private final String nombre; // asumimos nombre inmutable en este alcance
    private double precio;       // precio modificable con validación
    private int cantidad;        // stock en inventario

    public Producto(int id, String nombre, double precio, int cantidad) {
        if (id <= 0) throw new IllegalArgumentException("Id debe ser > 0");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido");
        if (precio < 0) throw new IllegalArgumentException("Precio no puede ser negativo");
        if (cantidad < 0) throw new IllegalArgumentException("Cantidad no puede ser negativa");
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    public void setPrecio(double nuevoPrecio) {
        if (nuevoPrecio < 0) throw new IllegalArgumentException("Precio no puede ser negativo");
        this.precio = nuevoPrecio;
    }

    public boolean disminuirStock(int cantidadVendida) {
        if (cantidadVendida <= 0) return false;
        if (cantidadVendida > this.cantidad) return false;
        this.cantidad -= cantidadVendida;
        return true;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0");
        this.cantidad += cantidad;
    }
s quitalos
    // Igualdad por id (identidad del producto en el inventario)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto that = (Producto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + " (Stock: " + cantidad + ")";
    }
}
