/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

/**
 * CLASE: Mesa
 * 
 * Representa una mesa del restaurante con su estado actual.
 * Utiliza un enum para manejar los diferentes estados posibles.
 * 
 * RELACIONES:
 * - AGREGACIÓN: Es referenciada por Pedido (una mesa puede ser usada por múltiples pedidos a lo largo del tiempo)
 * 
 * ESTADOS:
 * - DISPONIBLE: La mesa está libre y lista para ser usada
 * - RESERVADA: La mesa ha sido reservada pero aún no está ocupada
 * - OCUPADA: La mesa está siendo usada por un pedido activo
 */
public class Mesa {
    /**
     * Enum que define los estados posibles de una mesa
     */
    public enum Estado { 
        DISPONIBLE,  // Mesa libre y disponible
        RESERVADA,   // Mesa reservada pero aún no ocupada
        OCUPADA      // Mesa en uso con un pedido activo
    }

    /**
     * Número identificador de la mesa (final porque no cambia)
     */
    private final int numero;
    
    /**
     * Estado actual de la mesa
     */
    private Estado estado;

    /**
     * Constructor de Mesa
     * @param numero Número identificador de la mesa
     */
    public Mesa(int numero) {
        this.numero = numero;
        this.estado = Estado.DISPONIBLE; // Todas las mesas inician disponibles
    }

    // Getters
    public int getNumero() { return numero; }
    public Estado getEstado() { return estado; }

    /**
     * Reserva la mesa (solo si está disponible).
     * Cambia el estado a RESERVADA si la mesa está DISPONIBLE.
     */
    public void reservar() {
        if (estado == Estado.DISPONIBLE) estado = Estado.RESERVADA;
    }

    /**
     * Ocupa la mesa (solo si está disponible).
     * Este método es llamado por Pedido cuando se crea un nuevo pedido.
     * Cambia el estado a OCUPADA si la mesa está DISPONIBLE.
     */
    public void ocupar() {
        if (estado == Estado.DISPONIBLE) estado = Estado.OCUPADA;
    }

    /**
     * Libera la mesa, cambiando su estado a DISPONIBLE.
     * Este método es llamado por Pedido.cerrarPedido() cuando se completa un pedido.
     */
    public void liberar() {
        estado = Estado.DISPONIBLE;
    }

    /**
     * Muestra la información de la mesa en consola.
     * Incluye el número de mesa y su estado actual.
     */
    public void mostrarMesa() {
        System.out.println("Mesa " + numero + " - " + estado);
    }
}
