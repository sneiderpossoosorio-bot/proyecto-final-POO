/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 * CLASE: Empleado
 * 
 * Representa a un empleado del restaurante que atiende pedidos.
 * 
 * RELACIONES:
 * - HERENCIA: Extiende de Persona (hereda el atributo 'nombre')
 * - AGREGACIÓN: Es referenciado por Pedido (un empleado puede atender múltiples pedidos)
 * 
 * PATRÓN: Herencia simple (is-a relationship: Empleado ES UNA Persona)
 */
public class Empleado extends Persona {
    /**
     * Identificador único del empleado
     */
    private int idEmpleado;

    /**
     * Constructor de Empleado
     * @param nombre Nombre del empleado (heredado de Persona)
     * @param idEmpleado Identificador único del empleado
     */
    public Empleado(String nombre, int idEmpleado) {
        super(nombre); // Llama al constructor de la clase padre (Persona)
        this.idEmpleado = idEmpleado;
    }

    /**
     * Obtiene el ID del empleado
     * @return El identificador del empleado
     */
    public int getIdEmpleado() {
        return idEmpleado;
    }
}
