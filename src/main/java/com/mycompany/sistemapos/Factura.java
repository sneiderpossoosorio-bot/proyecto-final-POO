/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 * CLASE: Factura
 * 
 * Clase utilitaria para generar e imprimir facturas de pedidos.
 * Utiliza el patrón de método estático ya que no necesita mantener estado.
 * 
 * RELACIONES:
 * - ASOCIACIÓN con Pedido: Usa el pedido como parámetro para extraer información,
 *   pero no almacena el pedido ni lo modifica. Es una relación de uso temporal.
 * 
 * RESPONSABILIDADES:
 * - Formatear e imprimir la información del pedido como una factura
 * - Mostrar información del cliente, mesa, empleado y detalle de productos
 * 
 * PATRÓN: Clase utilitaria (utility class) con métodos estáticos
 */
public class Factura {
    /**
     * Imprime una factura completa basada en un pedido.
     * 
     * Este método es estático porque no necesita mantener estado interno.
     * Solo utiliza la información del pedido para generar la salida.
     * 
     * RELACIÓN: ASOCIACIÓN - La factura usa el pedido pero no lo almacena ni modifica.
     * 
     * @param pedido El pedido del cual se genera la factura
     */
    public static void imprimir(Pedido pedido) {
        System.out.println("\n===== FACTURA =====");
        // Extrae información del pedido (relación de asociación)
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Mesa: " + pedido.getMesa().getNumero());
        System.out.println("Atendido por: " + pedido.getEmpleado().getNombre());
        // Muestra el detalle completo del pedido
        pedido.mostrarDetalle();
        System.out.println("===================\n");
    }
}
