/*
 * Clase Factura (refactorizada)
 *
 * Cambios clave:
 * - Separación de responsabilidades: ya no imprime directamente; expone comoTexto() para que la capa de UI decida imprimir.
 * - Agrega fecha/hora de emisión.
 *
 * Relaciones OO:
 * - Agregación: Factura agrega un Pedido existente (no controla su ciclo de vida).
 */
package com.mycompany.sistemapos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Factura {
    private final int id;
    private final Pedido pedido; // agregación
    private final LocalDateTime emitidaEn = LocalDateTime.now();

    public Factura(int id, Pedido pedido) {
        if (id <= 0) throw new IllegalArgumentException("Id de factura debe ser > 0");
        if (pedido == null) throw new IllegalArgumentException("Pedido requerido");
        this.id = id;
        this.pedido = pedido;
    }

    public int getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public LocalDateTime getEmitidaEn() { return emitidaEn; }

    public String comoTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=====================================\n");
        sb.append(String.format("        FACTURA %d - CAFETERÍA         %n", id));
        sb.append("=====================================\n");
        sb.append(String.format(" Cliente: %-28s %n", pedido.getCliente().getNombre()));
        sb.append(String.format(" Empleado: %-27s %n", pedido.getEmpleado().getNombre()));
        sb.append(String.format(" Mesa: %-31s %n", pedido.getMesa().getNumero()));
        sb.append("=======================================\n");
        sb.append(" Producto               Cant.     Total \n");

        pedido.getLineas().forEach(lp -> {
            sb.append(String.format(" %-22s %6d %10.0f %n",
                    lp.getProducto().getNombre(), lp.getCantidad(), lp.getSubtotal()));
        });

        sb.append("\n====================================\n");
        sb.append(String.format(" TOTAL A PAGAR: %26.0f %n", pedido.getTotal()));
        sb.append("====================================\n");
        sb.append("Emitida: ").append(emitidaEn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append('\n');
        return sb.toString();
    }
}
