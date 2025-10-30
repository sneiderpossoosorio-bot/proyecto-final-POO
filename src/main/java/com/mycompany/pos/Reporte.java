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

// Clase Reporte: genera un resumen del día con información de la caja
public class Reporte {
    public void generar(Caja caja) {
        System.out.println("\n REPORTE DEL DIA (" + LocalDateTime.now() + ")");
        System.out.println("Total en ventas: " + caja.getTotalVentas());
        System.out.println("Saldo en caja: " + caja.getSaldoActual());
    }
}