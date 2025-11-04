/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 * CLASE BASE: Persona
 * 
 * Esta es la clase padre que representa a cualquier persona en el sistema.
 * Implementa el patrón de HERENCIA, siendo la superclase de Cliente y Empleado.
 * 
 * RELACIONES:
 * - HERENCIA: Cliente y Empleado extienden esta clase
 * 
 * @author janny
 */
public class Persona {
    /**
     * Nombre de la persona (protected para que las clases hijas puedan acceder)
     */
    protected String nombre;

    /**
     * Constructor de Persona
     * @param nombre El nombre de la persona
     */
    public Persona(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre de la persona
     * @return El nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }
}
