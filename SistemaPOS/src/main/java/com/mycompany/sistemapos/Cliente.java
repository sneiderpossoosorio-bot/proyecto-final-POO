/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 * Cliente: hereda de Persona.
 * Solo tiene nombre por ahora, pero en la práctica puede tener tel, dni, etc.
 */
public class Cliente extends Persona {
    public Cliente(String nombre) {
        super(nombre);
    }
}