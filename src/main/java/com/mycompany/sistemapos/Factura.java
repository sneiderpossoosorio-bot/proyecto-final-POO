/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;



/**
 * Factura: utilidad estática para imprimir la factura por consola.
 * Aquí formateamos la salida para que se vea como una factura sencilla.
 */
public class Factura {
    public static void imprimir(Pedido pedido) {
        System.out.println("\n===== FACTURA =====");
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Mesa: " + pedido.getMesa().getNumero());
        System.out.println("Atendido por: " + pedido.getEmpleado().getNombre());
        pedido.mostrarDetalle();
        System.out.println("===================\n");
    }
}
