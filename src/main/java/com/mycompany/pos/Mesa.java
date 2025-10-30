/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos;

/**
 *
 * @author janny
 */
// Clase Mesa: controla el estado y pedidos asociados
public class Mesa {
    private int numero;
    private String estado; // Libre, Ocupada, Reservada
    private Pedido pedidoActual;

    public Mesa(int numero) {
        this.numero = numero;
        this.estado = "Libre";
    }

    public int getNumero() { return numero; }
    public String getEstado() { return estado; }

    public void reservar() {
        if (estado.equals("Libre")) estado = "Reservada";
        else System.out.println(" Mesa " + numero + " no disponible.");
    }

    public void ocupar() {
        if (estado.equals("Libre") || estado.equals("Reservada")) {
            estado = "Ocupada";
            pedidoActual = new Pedido();
        } else {
            System.out.println(" Mesa ya ocupada.");
        }
    }

    public void liberar() {
        estado = "Libre";
        pedidoActual = null;
    }

    public Pedido getPedidoActual() {
        return pedidoActual;
    }

    @Override
    public String toString() {
        return "Mesa " + numero + " - Estado: " + estado;
    }
}