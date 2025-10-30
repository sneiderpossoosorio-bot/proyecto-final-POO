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
    /*
     * Empleado hereda de Persona (herencia).
     * Cargo se modela con enum sencillo para evitar errores de texto.
     */
    private Cargo cargo;

    public Empleado(int id, String nombre, Cargo cargo) {
        super(id, nombre);
        if (cargo == null) throw new IllegalArgumentException("Cargo requerido");
        this.cargo = cargo;
    }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) {
        if (cargo == null) throw new IllegalArgumentException("Cargo requerido");
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return super.toString() + " | Cargo: " + cargo;
    }
}
