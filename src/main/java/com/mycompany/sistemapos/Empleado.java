/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */


public class Empleado extends Persona {
    private String cargo;

    public Empleado(int id, String nombre, String cargo) {
        super(id, nombre);
        this.cargo = cargo;
    }

    public String getCargo() { return cargo; }

    @Override
    public String toString() {
        return super.toString() + " | Cargo: " + cargo;
    }
}
