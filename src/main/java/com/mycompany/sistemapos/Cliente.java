/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 * CLASE: Cliente
 * 
 * Representa a un cliente del restaurante que realiza pedidos.
 * 
 * RELACIONES:
 * - HERENCIA: Extiende de Persona (hereda el atributo 'nombre')
 * - AGREGACIÓN: Es referenciado por Pedido (un cliente puede realizar múltiples pedidos)
 * 
 * PATRÓN: Herencia simple (is-a relationship: Cliente ES UNA Persona)
 */
public class Cliente extends Persona {
    /**
     * Constructor de Cliente
     * @param nombre Nombre del cliente (heredado de Persona)
     */
    public Cliente(String nombre) {
        super(nombre); // Llama al constructor de la clase padre (Persona)
    }
}