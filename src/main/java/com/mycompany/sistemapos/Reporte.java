/*
 * Clase Reporte (refactorizada)
 *
 * Cambios clave:
 * - Separación de responsabilidades: no imprime, retorna un String con el reporte.
 * - Uso de BigDecimal para totales.
 *
 * Relaciones OO:
 * - Agregación: Reporte agrega Facturas emitidas; no controla el ciclo de vida de las entidades de dominio.
 */
package com.mycompany.sistemapos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Reporte {
    private final List<Factura> facturas;

    public Reporte() {
        this.facturas = new ArrayList<>();
    }

    public void agregarFactura(Factura factura) {
        if (factura != null) facturas.add(factura);
    }

    public String comoTexto() {
        if (facturas.isEmpty()) {
            return " No hay facturas registradas todavía.";
        }

        StringBuilder sb = new StringBuilder();
        BigDecimal totalGeneral = BigDecimal.ZERO;

        sb.append("\n============================================\n");
        sb.append("║            REPORTE DE VENTAS - DÍA           ║\n");
        sb.append("============================================\n");
        sb.append(String.format("║ %-8s %-12s %-10s %-10s ║%n", "Factura", "Cliente", "Mesa", "Total"));
        sb.append("============================================\n");

        for (Factura f : facturas) {
            BigDecimal totalFactura = f.getPedido().getTotal();
            totalGeneral = totalGeneral.add(totalFactura);
            sb.append(String.format("║ %-8d %-12s %-10d $%-9.0f ║%n",
                    f.getId(),
                    f.getPedido().getCliente().getNombre(),
                    f.getPedido().getMesa().getNumero(),
                    totalFactura));
        }

        sb.append("╠══════════════════════════════════════════════╣\n");
        sb.append(String.format("║ TOTAL GENERAL DEL DÍA:             $%-9.0f ║%n", totalGeneral));
        sb.append("╚══════════════════════════════════════════════╝\n");
        return sb.toString();
    }
}
