/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.PagoInvalidoException;
import com.mycompany.sistemapos.utilidades.Logger;

/**
 * CLASE: Caja
 * 
 * Representa la caja registradora del restaurante.
 * Registra y acumula los ingresos del día.
 * 
 * RELACIONES:
 * - ASOCIACIÓN con Pedido: Recibe información de pagos (montos) pero no almacena
 *   ni referencia a los pedidos directamente. Solo acumula los totales.
 * 
 * RESPONSABILIDADES:
 * - Registrar pagos realizados
 * - Mantener el total acumulado del día
 * - Permitir reiniciar el contador (para cambio de día)
 * 
 * FUTURAS MEJORAS:
 * - Manejar arqueos de caja
 * - Registrar movimientos individuales
 * - Manejar diferentes métodos de pago
 * - Generar reportes de caja
 */
public class Caja {
    /**
     * Total acumulado de ingresos del día actual
     */
    private double totalDia = 0.0;

    /**
     * Registra un pago en la caja.
     * Suma el monto al total del día si el monto es válido.
     * 
     * Este método es llamado desde SistemaPOS cuando se confirma un pago.
     * 
     * @param monto Monto del pago a registrar
     * @throws PagoInvalidoException Si el monto es inválido (<= 0 o NaN)
     */
    public void registrarPago(double monto) throws PagoInvalidoException {
        if (monto <= 0) {
            throw new PagoInvalidoException(monto);
        }
        if (Double.isNaN(monto) || Double.isInfinite(monto)) {
            throw new PagoInvalidoException("El monto no es un número válido");
        }
        
        totalDia += monto;
        Logger.info(String.format("Pago registrado: $%.2f - Total del día: $%.2f", monto, totalDia));
    }

    /**
     * Obtiene el total acumulado del día.
     * 
     * @return El total de ingresos registrados en el día
     */
    public double getTotalDia() { return totalDia; }

    /**
     * Reinicia el contador de la caja (útil para cambiar de día).
     * Pone el total del día en cero.
     */
    public void reiniciarDia() { totalDia = 0; }
}