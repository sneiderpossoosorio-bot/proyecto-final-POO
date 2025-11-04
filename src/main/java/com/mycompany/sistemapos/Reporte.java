/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import java.util.HashMap;
import java.util.Map;

/**
 * CLASE: Reporte
 * 
 * Genera reportes de ventas acumulando información de los pedidos.
 * Puede usarse para reportes diarios o mensuales (se crean instancias separadas).
 * 
 * RELACIONES:
 * - ASOCIACIÓN con Pedido: Recibe pedidos como parámetros para extraer información
 *   y acumularla en mapas internos. No almacena los pedidos completos, solo
 *   extrae y procesa los datos necesarios para las estadísticas.
 * 
 * RESPONSABILIDADES:
 * - Acumular ventas por producto (unidades y totales)
 * - Contar total de pedidos y ventas
 * - Generar reportes con estadísticas detalladas
 * - Identificar productos más y menos vendidos
 * - Permitir reiniciar acumuladores para nuevos períodos
 * 
 * PATRÓN: Acumulador (Accumulator pattern) - Acumula datos de múltiples transacciones
 */
public class Reporte {
    /**
     * Mapa que acumula las unidades vendidas por producto
     * Clave: Nombre del producto, Valor: Cantidad total vendida
     */
    private Map<String, Integer> unidadesPorProducto = new HashMap<>();
    
    /**
     * Mapa que acumula el total en dinero por producto
     * Clave: Nombre del producto, Valor: Total en dinero vendido
     */
    private Map<String, Double> totalPorProducto = new HashMap<>();
    
    /**
     * Contador total de pedidos registrados
     */
    private int totalPedidos = 0;
    
    /**
     * Total acumulado de todas las ventas en dinero
     */
    private double totalVentas = 0.0;

    /**
     * Registra una venta (pedido) en el reporte.
     * 
     * Extrae información del pedido y la acumula en los mapas internos.
     * Este método es llamado cada vez que se completa un pago.
     * 
     * RELACIÓN: ASOCIACIÓN - Usa el pedido para extraer datos pero no lo almacena.
     * 
     * @param pedido El pedido cuya información se va a registrar
     */
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

    /**
     * Muestra un reporte completo con todas las estadísticas acumuladas.
     * 
     * Incluye:
     * - Total de pedidos
     * - Total de ventas en dinero
     * - Detalle por producto (cantidad y total)
     * - Producto más vendido
     * - Producto menos vendido
     * 
     * @param titulo Título del reporte (ej: "REPORTE DIARIO" o "REPORTE MENSUAL")
     */
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

    /**
     * Reinicia todos los acumuladores del reporte.
     * 
     * Útil para:
     * - Cambiar de día (reporte diario)
     * - Iniciar un nuevo mes (reporte mensual)
     * 
     * Limpia todos los mapas y contadores, dejando el reporte en estado inicial.
     */
    public void reiniciar() {
        unidadesPorProducto.clear();
        totalPorProducto.clear();
        totalPedidos = 0;
        totalVentas = 0.0;
    }
}