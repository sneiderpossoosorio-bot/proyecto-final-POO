/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import java.util.HashMap;
import java.util.Map;


public class Reporte {
  
    // Mapa que acumula las unidades vendidas por producto
    // Clave: Nombre del producto, Valor: Cantidad total vendid
    private Map<String, Integer> unidadesPorProducto = new HashMap<>();
    
    // Mapa que acumula el total en dinero por producto
    // Clave: Nombre del producto, Valor: Total en dinero vendido
    private Map<String, Double> totalPorProducto = new HashMap<>();
    
    /*Contador total de pedidos registrados*/
    private int totalPedidos = 0;
    
    
     /* Total acumulado de todas las ventas en dinero*/
 private double totalVentas = 0.0;


    public void registrarVenta(Pedido pedido) {
        // Itera sobre todos los productos del pedido
        for (Map.Entry<Producto, Integer> e : pedido.getItems().entrySet()) {
            String nombre = e.getKey().getNombre();
            int cant = e.getValue();
            double subtotal = e.getKey().getPrecio() * cant;

            // Acumula unidades vendidas por producto
            unidadesPorProducto.put(nombre, unidadesPorProducto.getOrDefault(nombre, 0) + cant);
            // Acumula totales en dinero por producto
            totalPorProducto.put(nombre, totalPorProducto.getOrDefault(nombre, 0.0) + subtotal);
        }
        // Incrementa contadores globales
        totalPedidos++;
        totalVentas += pedido.getTotal();
    }

    //muestra el repote completo
    public void mostrarReporte(String titulo) {
        System.out.println("\n===== " + titulo + " =====");
        System.out.println("Total pedidos: " + totalPedidos);
        System.out.println("Total ventas: $" + (int)totalVentas);
        System.out.println("-------------------------------");
        System.out.printf("%-20s %8s %10s\n", "Producto", "Cant", "Total $");
        
        // Muestra el detalle por producto
        for (String nombre : unidadesPorProducto.keySet()) {
            System.out.printf("%-20s %8d %10.0f\n",
                    nombre,
                    unidadesPorProducto.get(nombre),
                    totalPorProducto.getOrDefault(nombre, 0.0));
        }
        
        // Calcula producto más y menos vendido
        String mas = null, menos = null;
        for (String n : unidadesPorProducto.keySet()) {
            if (mas == null || unidadesPorProducto.get(n) > unidadesPorProducto.get(mas)) mas = n;
            if (menos == null || unidadesPorProducto.get(n) < unidadesPorProducto.get(menos)) menos = n;
        }
        
        if (mas != null) {
            System.out.println("-------------------------------");
            System.out.println("Producto mas vendido: " + mas + " (" + unidadesPorProducto.get(mas) + " uds)");
            System.out.println("Producto menos vendido: " + menos + " (" + unidadesPorProducto.get(menos) + " uds)");
        } else {
            System.out.println("No hay ventas registradas aun.");
        }
        System.out.println("===============================\n");
    }

   //Reinicia todos los acumuladores del reporte.
     
    public void reiniciar() {
        unidadesPorProducto.clear();
        totalPorProducto.clear();
        totalPedidos = 0;
        totalVentas = 0.0;
    }
}