/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;


import java.util.ArrayList;
import java.util.List;

/**
 * Inventario administra la colección de productos.
 *
 * Observaciones de diseño:
 * - Inventario "posee" los Productos que contiene: es composición en la práctica,
 *   porque los productos son administrados por Inventario (creados/agregados allí).
 * - Provee búsqueda por nombre, mostrar lista y obtener producto por índice.
 */
public class Inventario {
    private List<Producto> productos = new ArrayList<>();

    // Agrega un producto al inventario (composición).
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    // Buscar por nombre (ignora mayúsculas).
    public Producto buscarProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }

    // Obtener producto por índice (1-based) para menús.
    public Producto getProductoPorIndice(int index) {
        if (index < 1 || index > productos.size()) return null;
        return productos.get(index - 1);
    }

    // Lista completa (incluye agotados).
    public void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ---");
        int i = 1;
        for (Producto p : productos) {
            System.out.print(i + ". ");
            p.mostrarProducto();
            i++;
        }
    }

    // Muestra solo los que tienen stock > 0 (para menú de pedido).
    public void mostrarInventarioDisponibles() {
        System.out.println("\n--- MENU (Productos disponibles) ---");
        int i = 1;
        for (Producto p : productos) {
            if (p.getStock() > 0) {
                System.out.println(i + ". " + p.getNombre() + " - $" + (int)p.getPrecio() + " (Stock: " + p.getStock() + ")");
            } else {
                System.out.println(i + ". " + p.getNombre() + " - AGOTADO");
            }
            i++;
        }
    }

    // Devuelve la lista (solo lectura)
    public List<Producto> getProductos() {
        return productos;
    }

    // Producto más vendido (por campo vendidos)
    public Producto getMasVendido() {
        Producto mejor = null;
        for (Producto p : productos) {
            if (mejor == null || p.getVendidos() > mejor.getVendidos()) mejor = p;
        }
        return mejor;
    }

    // Producto menos vendido (incluye 0 vendidos)
    public Producto getMenosVendido() {
        Producto peor = null;
        for (Producto p : productos) {
            if (peor == null || p.getVendidos() < peor.getVendidos()) peor = p;
        }
        return peor;
    }
}
