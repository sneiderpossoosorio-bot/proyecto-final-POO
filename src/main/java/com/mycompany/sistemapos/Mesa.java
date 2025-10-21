/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */

public class Mesa {
    private int numero;
    private String estado;

    public Mesa(int numero, String estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public int getNumero() { return numero; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Mesa " + numero + " - Estado: " + estado;
    }
}