/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */

public class Cliente extends Persona {

    public Cliente(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public String toString() {
        return "Cliente - " + super.toString();
    }
}
