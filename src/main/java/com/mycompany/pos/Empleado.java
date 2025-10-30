/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos;

/**
 *
 * @author janny
 */
// Clase Empleado: hereda de Persona
// Representa al trabajador que gestiona pedidos y mesas
public class Empleado extends Persona {
    private String cargo;

    public Empleado(String nombre, String documento, String cargo) {
        super(nombre, documento);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return "Empleado: " + nombre + " (" + cargo + ")";
    }

    
}
