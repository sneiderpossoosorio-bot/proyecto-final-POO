/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos;

/**
 *
 * @author janny
 */
import java.util.ArrayList;
import java.util.List;

// Clase Caja: controla el dinero y las ventas registradas
public class Caja {
    private double saldoInicial;
    private double saldoActual;
    private List<Double> ventas;

    public Caja(double saldoInicial) {
        this.saldoInicial = saldoInicial;
        this.saldoActual = saldoInicial;
        this.ventas = new ArrayList<>();
    }

    public void registrarVenta(double monto) {
        ventas.add(monto);
        saldoActual += monto;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getTotalVentas() {
        return ventas.stream().mapToDouble(Double::doubleValue).sum();
    }

    public void mostrarEstado() {
        System.out.println("\n ESTADO DE CAJA:");
        System.out.println("Saldo inicial: $" + saldoInicial);
        System.out.println("Ventas totales: $" + getTotalVentas());
        System.out.println("Saldo actual: $" + saldoActual);
    }
}
