/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos;

/**
 *
 * @author janny
 */
import java.time.LocalDateTime;

// Clase Factura: genera el comprobante de venta del pedido
public class Factura {
    private Cliente cliente;
    private Pedido pedido;
    private LocalDateTime fecha;

    public Factura(Cliente cliente, Pedido pedido) {
        this.cliente = cliente;
        this.pedido = pedido;
        this.fecha = LocalDateTime.now();
    }

    public void mostrarFactura() {
        System.out.println("\n======= FACTURA =======");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Mesa: " + cliente.getMesaAsignada());
        System.out.println("Fecha: " + fecha);
        System.out.println(pedido);
        System.out.println("=======================");
    }

    public double getTotal() {
        return pedido.getTotal();
    }
}

