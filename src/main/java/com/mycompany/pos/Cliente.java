/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos;

/**
 *
 * @author janny
 */

// Clase Cliente: hereda de Persona
// Representa a un cliente que realiza pedidos en el restaurante
public class Cliente extends Persona {
    private int mesaAsignada;

    public Cliente(String nombre, String documento) {
        super(nombre, documento);
        this.mesaAsignada = -1; // -1 = sin asignar
    }

    public void asignarMesa(int numeroMesa) {
        this.mesaAsignada = numeroMesa;
    }

    public int getMesaAsignada() {
        return mesaAsignada;
    }

    @Override
    public String toString() {
        String documento = null;
        return "Cliente: " + nombre + " | Documento: " + documento + " | Mesa: " + mesaAsignada;
    }

    
}
