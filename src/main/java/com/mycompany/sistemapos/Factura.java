/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;


public class Factura {
    
     /* RELACIÓN: ASOCIACIÓN - La factura usa el pedido pero no lo almacena ni modifica.*/

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
