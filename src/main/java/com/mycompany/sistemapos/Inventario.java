/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */

import java.util.*;

public class Inventario {
    // Usamos Map para búsquedas O(1) por id (agregación: Inventario agrega productos existentes)
    private final Map<Integer, Producto> productos = new LinkedHashMap<>();

    public boolean agregarProducto(Producto p) {
        if (p == null) return false;
        if (productos.containsKey(p.getId())) return false; // evitar duplicados por id
        productos.put(p.getId(), p);
        return true;
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos.values()); // copia defensiva
    }

    public Producto buscarProductoPorId(int id) {
        return productos.get(id);
    }

    public String listarProductosComoTexto() {
        StringBuilder sb = new StringBuilder();
        for (Producto p : productos.values()) {
            sb.append(p).append(System.lineSeparator());
        }
        return sb.toString();
    }
}