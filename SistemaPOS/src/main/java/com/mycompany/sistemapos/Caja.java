/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;


/**
 * Caja registra los ingresos del día (en memoria).
 * - registrarPago suma el monto al total del día.
 * - Se puede extender para manejar arqueos, movimientos, etc.
 */
public class Caja {
    private double totalDia = 0.0;

    public void registrarPago(double monto) {
        if (monto > 0) totalDia += monto;
    }

    public double getTotalDia() { return totalDia; }

    // Reiniciar caja (por ejemplo, al cambiar de día) si se quisiera:
    public void reiniciarDia() { totalDia = 0; }
}