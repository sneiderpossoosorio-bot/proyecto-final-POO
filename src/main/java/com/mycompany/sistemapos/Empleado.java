/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;


/**
 * Empleado: hereda de Persona.
 * Puede extenderse con id, rol, horario, etc.
 */
public class Empleado extends Persona {
    private int idEmpleado;

    public Empleado(String nombre, int idEmpleado) {
        super(nombre);
        this.idEmpleado = idEmpleado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }
}
