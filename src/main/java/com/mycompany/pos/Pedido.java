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

// Clase Pedido: contiene los productos que un cliente solicita
public class Pedido {
    private List<Producto> productos;
    private double total;

    public Pedido() {
        productos = new ArrayList<>();
        total = 0;
    }

    // Agrega productos al pedido
    public void agregarProducto(Producto p, int cantidad) {
        p.vender(cantidad);
        total += p.getPrecio() * cantidad;
        productos.add(p);
    }

    public double getTotal() {
        return total;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(" Pedido:\n");
        for (Producto p : productos) {
            sb.append(" - ").append(p).append("\n");
        }
        sb.append("Total: $").append(total);
        return sb.toString();
    }
}

