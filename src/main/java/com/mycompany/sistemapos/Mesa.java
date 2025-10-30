/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;



/**
 * Mesa con tres estados: DISPONIBLE, RESERVADA, OCUPADA.
 * Métodos para reservar, ocupar y liberar.
 *
 * La lógica de negocio para no usar mesa ocupada/reservada se implementa en Main/Pedido.
 */
public class Mesa {
    public enum Estado { DISPONIBLE, RESERVADA, OCUPADA }

    private final int numero;
    private Estado estado;

    public Mesa(int numero) {
        this.numero = numero;
        this.estado = Estado.DISPONIBLE;
    }

    public int getNumero() { return numero; }
    public Estado getEstado() { return estado; }

    public void reservar() {
        if (estado == Estado.DISPONIBLE) estado = Estado.RESERVADA;
    }

    public void ocupar() {
        if (estado == Estado.DISPONIBLE) estado = Estado.OCUPADA;
    }

    public void liberar() {
        estado = Estado.DISPONIBLE;
    }

    public void mostrarMesa() {
        System.out.println("Mesa " + numero + " - " + estado);
    }
}
