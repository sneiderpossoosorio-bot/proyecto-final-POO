/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.StockInsuficienteException;


public class Producto {
   
     /* Nombre del producto*/
    private String nombre;
    
     /* Precio unitario del producto*/
    private double precio;
    
     /* Cantidad disponible en stock*/
    private int stock;
    
     /* Contador de unidades vendidas (para reportes)*/
    private int vendidos;

   
     /* Constructor de */
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

    /*
     *  cantidad Cantidad a descontar del stock
     *  StockInsuficienteException Si no hay suficiente stock
     *  IllegalArgumentException Si la cantidad es inválida (<= 0)
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
    
    
     /*  true si se pudo descontar, false si no hay stock suficiente
     *  Usar descontarStock(int) que lanza */
    
    @Deprecated
    public boolean descontarStockLegacy(int cantidad) {
        try {
            descontarStock(cantidad);
            return true;
        } catch (StockInsuficienteException e) {
            return false;
        }
    }

   
     /* Repone stock del producto (útil para administración).
     * Permite agregar más unidades al inventario.*/
  
    public void reponer(int cantidad) {
        if (cantidad > 0) stock += cantidad;
    }

   
     /* Muestra la información del producto en consola.
     * Incluye nombre, precio, estado del stock y unidades vendidas.*/
   
    public void mostrarProducto() {
        String estado = stock > 0 ? ("Stock: " + stock) : "AGOTADO";
        System.out.println(nombre + " - $" + (int)precio + " - " + estado + " (Vendidos: " + vendidos + ")");
    }
}
