/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.PagoInvalidoException;
import com.mycompany.sistemapos.utilidades.Logger;


public class Caja {
    /**
     * Total acumulado de ingresos del día actual
     */
    private double totalDia = 0.0;

    /**
     * Registra un pago en la caja.
     * Suma el monto al total del día si el monto es válido.*/
    
    public void registrarPago(double monto) throws PagoInvalidoException {
        if (monto <= 0) {
            throw new PagoInvalidoException(monto);
        }
        if (Double.isNaN(monto) || Double.isInfinite(monto)) {
            throw new PagoInvalidoException("El monto no es un numero valido");
        }
        
        totalDia += monto;
        Logger.info(String.format("Pago registrado: $%.2f - Total del dia: $%.2f", monto, totalDia));
    }

    /* total de ingresos registrados en el día*/
    public double getTotalDia() { return totalDia; }

    
    /* Reinicia el contador de la caja (útil para cambiar de día).*/
    public void reiniciarDia() { totalDia = 0; }
}