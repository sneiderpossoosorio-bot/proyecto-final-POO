/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;


public class Empleado extends Persona {
 
    private int idEmpleado;

   public Empleado(String nombre, int idEmpleado) {
        super(nombre); // Llama al constructor de la clase padre (Persona)
        this.idEmpleado = idEmpleado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }
}
