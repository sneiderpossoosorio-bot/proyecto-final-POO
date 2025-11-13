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
    
    /**
     * Stock inicial del producto (para restaurar cuando se agota)
     */
    private int stockInicial;
    
     /* Contador de unidades vendidas (para reportes)*/
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
        this.stockInicial = stock; // Guarda el stock inicial
        this.vendidos = 0; // Inicialmente no se ha vendido nada
    }

    // Getters
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public int getStockInicial() { return stockInicial; }
    public int getVendidos() { return vendidos; }
    
    /**
     * Restaura el stock al valor inicial
     */
    public void restaurarStock() {
        this.stock = this.stockInicial;
    }
    
    /**
     * Establece el stock inicial (útil cuando se carga desde archivo)
     */
    public void setStockInicial(int stockInicial) {
        this.stockInicial = stockInicial;
    }
    
    /**
     * Establece el stock actual (útil para restaurar stock)
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * Restablece el stock al valor inicial si está agotado
     * Útil para reponer automáticamente al reiniciar el sistema
     */
    public void restaurarSiAgotado() {
        if (this.stock == 0 && this.stockInicial > 0) {
            this.stock = this.stockInicial;
        }
    }

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
