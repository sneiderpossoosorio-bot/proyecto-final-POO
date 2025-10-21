/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */

import java.util.ArrayList;

public class Reporte {
    private ArrayList<Factura> facturas;

    public Reporte() {
        this.facturas = new ArrayList<>();
    }

    public void agregarFactura(Factura factura) {
        facturas.add(factura);
    }

    public void mostrarReporte() {
        if (facturas.isEmpty()) {
            System.out.println(" No hay facturas registradas todavía.");
            return;
        }

        double totalGeneral = 0;

        System.out.println("\n============================================");
        System.out.println("║            REPORTE DE VENTAS - DÍA           ║");
        System.out.println("\n============================================");
        System.out.printf("║ %-8s %-12s %-10s %-10s ║%n", "Factura", "Cliente", "Mesa", "Total");
        System.out.println("\n============================================");

        for (Factura f : facturas) {
            double totalFactura = f.getPedido().getTotal();
            totalGeneral += totalFactura;
            System.out.printf("║ %-8d %-12s %-10d $%-9.0f ║%n",
                    f.getId(),
                    f.getPedido().getCliente().getNombre(),
                    f.getPedido().getMesa().getNumero(),
                    totalFactura);
        }

        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf("║ TOTAL GENERAL DEL DÍA:             $%-9.0f ║%n", totalGeneral);
        System.out.println("╚══════════════════════════════════════════════╝");
    }
}


