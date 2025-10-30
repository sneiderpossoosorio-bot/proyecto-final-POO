/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;



import java.util.HashMap;
import java.util.Map;

/**
 * Reporte centraliza la recolección de datos para reportes diarios y mensuales.
 *
 * Estrategia:
 * - Cada vez que se cierra un pedido y se registra el pago, se debe llamar a registrarVentaEnReporte(pedido).
 * - Esa llamada suma unidades vendidas por producto y suma totales.
 *
 * Aquí se usan mapas (Producto.nombre -> cantidad vendida / total $) para simplificar la presentación.
 *
 * Nota: usamos el nombre del producto como clave para los mapas de reporte.
 */
public class Reporte {
    // acumulados del periodo (aquí tratamos estos acumulados como "mensuales")
    private Map<String, Integer> unidadesPorProducto = new HashMap<>();
    private Map<String, Double> totalPorProducto = new HashMap<>();
    private int totalPedidos = 0;
    private double totalVentas = 0.0;

    // registra las unidades y totales de un pedido (se usa tanto para diario como para mensual)
    public void registrarVenta(Pedido pedido) {
        for (Map.Entry<Producto, Integer> e : pedido.getItems().entrySet()) {
            String nombre = e.getKey().getNombre();
            int cant = e.getValue();
            double subtotal = e.getKey().getPrecio() * cant;

            unidadesPorProducto.put(nombre, unidadesPorProducto.getOrDefault(nombre, 0) + cant);
            totalPorProducto.put(nombre, totalPorProducto.getOrDefault(nombre, 0.0) + subtotal);
        }
        totalPedidos++;
        totalVentas += pedido.getTotal();
    }

    // Mostrar reporte con detalle: se puede usar para diario o mensual (aquí no diferenciamos por fecha)
    public void mostrarReporte(String titulo) {
        System.out.println("\n===== " + titulo + " =====");
        System.out.println("Total pedidos: " + totalPedidos);
        System.out.println("Total ventas: $" + (int)totalVentas);
        System.out.println("-------------------------------");
        System.out.printf("%-20s %8s %10s\n", "Producto", "Cant", "Total $");
        for (String nombre : unidadesPorProducto.keySet()) {
            System.out.printf("%-20s %8d %10.0f\n",
                    nombre,
                    unidadesPorProducto.get(nombre),
                    totalPorProducto.getOrDefault(nombre, 0.0));
        }
        // producto más y menos vendido
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

    // Reiniciar acumuladores (por ejemplo, pasar de día o al iniciar un mes nuevo).
    public void reiniciar() {
        unidadesPorProducto.clear();
        totalPorProducto.clear();
        totalPedidos = 0;
        totalVentas = 0.0;
    }
}