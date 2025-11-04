/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.StockInsuficienteException;

/**
 * CLASE: Producto
 * 
 * Representa un producto que se vende en el restaurante (comida, bebida, etc.).
 * Gestiona su propio stock y contador de unidades vendidas.
 * 
 * RELACIONES:
 * - COMPOSICIÓN: Está contenido en Inventario (los productos son gestionados por el inventario)
 * - AGREGACIÓN: Es referenciado por Pedido (un producto puede estar en múltiples pedidos)
 * 
 * RESPONSABILIDADES:
 * - Controlar su propio inventario (stock disponible)
 * - Llevar registro de unidades vendidas
 * - Validar disponibilidad antes de vender
 */
public class Producto {
    /**
     * Nombre del producto
     */
    private String nombre;
    
    /**
     * Precio unitario del producto
     */
    private double precio;
    
    /**
     * Cantidad disponible en stock
     */
    private int stock;
    
    /**
     * Contador de unidades vendidas (para reportes)
     */
    private int vendidos;

    /**
     * Constructor de Producto
     * @param nombre Nombre del producto
     * @param precio Precio unitario
     * @param stock Cantidad inicial en stock
     */
    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.vendidos = 0; // Inicialmente no se ha vendido nada
    }

    // Getters
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public int getVendidos() { return vendidos; }

    /**
     * Intenta descontar 'cantidad' del stock.
     * Lanza una excepción si no hay suficiente stock.
     * También incrementa 'vendidos' cuando la venta es exitosa.
     * 
     * Este método es llamado por Pedido.agregarProducto() cuando se intenta agregar
     * un producto a un pedido.
     * 
     * @param cantidad Cantidad a descontar del stock
     * @throws StockInsuficienteException Si no hay suficiente stock
     * @throws IllegalArgumentException Si la cantidad es inválida (<= 0)
     */
    public void descontarStock(int cantidad) throws StockInsuficienteException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (cantidad > stock) {
            throw new StockInsuficienteException(this, cantidad);
        }
        stock -= cantidad;        // Reduce el stock disponible
        vendidos += cantidad;     // Incrementa el contador de vendidos
    }
    
    /**
     * Versión compatible que retorna boolean (para compatibilidad con código existente)
     * @param cantidad Cantidad a descontar
     * @return true si se pudo descontar, false si no hay stock suficiente
     * @deprecated Usar descontarStock(int) que lanza excepción
     */
    @Deprecated
    public boolean descontarStockLegacy(int cantidad) {
        try {
            descontarStock(cantidad);
            return true;
        } catch (StockInsuficienteException e) {
            return false;
        }
    }

    /**
     * Repone stock del producto (útil para administración).
     * Permite agregar más unidades al inventario.
     * 
     * @param cantidad Cantidad a reponer (debe ser positiva)
     */
    public void reponer(int cantidad) {
        if (cantidad > 0) stock += cantidad;
    }

    /**
     * Muestra la información del producto en consola.
     * Incluye nombre, precio, estado del stock y unidades vendidas.
     */
    public void mostrarProducto() {
        String estado = stock > 0 ? ("Stock: " + stock) : "AGOTADO";
        System.out.println(nombre + " - $" + (int)precio + " - " + estado + " (Vendidos: " + vendidos + ")");
    }
}
