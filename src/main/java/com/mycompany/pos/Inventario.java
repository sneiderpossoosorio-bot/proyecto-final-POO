/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos;

/**
 *
 * @author janny
 */

import java.util.ArrayList;
import java.util.List;

// Clase Inventario: almacena los productos disponibles en el restaurante
public class Inventario {
    private List<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
        // Productos iniciales
        productos.add(new Producto("Cafe", 3000, 1));
        productos.add(new Producto("Sandwich", 7000, 2));
        productos.add(new Producto("Jugo", 5000, 3));
    }

    public List<Producto> getProductos() {
        return productos;
    }

    // Muestra todo el inventario
    public void mostrarInventario() {
        System.out.println("\n INVENTARIO:");
        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i));
        }
    }
}