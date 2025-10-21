/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */

public class Factura {
    private int id;
    private Pedido pedido;

    public Factura(int id, Pedido pedido) {
        this.id = id;
        this.pedido = pedido;
    }

    public int getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }


    public void imprimirFactura() {
        System.out.println("\n=====================================");
        System.out.printf("        FACTURA %d - CAFETERÍA         %n", id);
        System.out.println("\n=====================================");
        System.out.printf(" Cliente: %-28s %n", pedido.getCliente().getNombre());
        System.out.printf(" Empleado: %-27s %n", pedido.getEmpleado().getNombre());
        System.out.printf(" Mesa: %-31s %n", pedido.getMesa().getNumero());
        System.out.println("=======================================");
        System.out.println(" Producto               Cant.     Total ");

        double total = 0;
        for (Producto p : pedido.getProductos()) {
            double subtotal = p.getPrecio() * p.getCantidad();
            total += subtotal;
            System.out.printf(" %-22s %6d %10.0f %n",
                    p.getNombre(), p.getCantidad(), subtotal);
        }

        System.out.println("\n====================================");
        System.out.printf(" TOTAL A PAGAR: %26.0f %n", total);
        System.out.println("\n====================================");
    }
}


