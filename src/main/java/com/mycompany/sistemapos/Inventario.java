/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.ProductoNoEncontradoException;
import com.mycompany.sistemapos.utilidades.Logger;
import java.util.ArrayList;
import java.util.List;


public class Inventario {
    
    /*Lista que contiene todos los productos del inventario (COMPOSICIÓN)   */
     private List<Producto> productos = new ArrayList<>();

    /*Producto a agregar al inventario   */
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    /**
     * Busca un producto por su nombre (búsqueda case-insensitive).*/
    public Producto buscarProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }
   /*Busca un producto por su nombre y lanza excepción si no se encuentra.*/
  
    public Producto buscarProductoOErro(String nombre) throws ProductoNoEncontradoException {
        Producto producto = buscarProducto(nombre);
        if (producto == null) {
            throw new ProductoNoEncontradoException(nombre);
        }
        return producto;
    }

    
     /* Obtiene un producto por su índice en la lista (1-based para menús de usuario).*/
    
    public Producto getProductoPorIndice(int index) {
        if (index < 1 || index > productos.size()) {
            Logger.warning(String.format("Índice de producto inválido: %d (rango válido: 1-%d)", 
                index, productos.size()));
            return null;
        }
        return productos.get(index - 1); // Convierte a índice 0-based
    }
    
   
     /* lanza un mensaje de error si el producto no existe.*/
   
    public Producto getProductoPorIndiceOErro(int index) throws ProductoNoEncontradoException {
        if (index < 1 || index > productos.size()) {
            throw new ProductoNoEncontradoException("Índice " + index);
        }
        return productos.get(index - 1);
    }

    
    /* Muestra el inventario completo con todos los productos.
     * Incluye información de stock, precio y unidades vendidas.
     */
    public void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ---");
        int i = 1;
        for (Producto p : productos) {
            System.out.print(i + ". ");
            p.mostrarProducto(); // Delega la visualización al producto
            i++;
        }
    }

    /*
     * Muestra solo los productos disponibles  para el menú de pedidos.
     * También muestra los productos "AGOTADO".
         */
    public void mostrarInventarioDisponibles() {
        System.out.println("\n--- MENU (Productos disponibles) ---");
        int i = 1;
        for (Producto p : productos) {
            if (p.getStock() > 0) {
                System.out.println(i + ". " + p.getNombre() + " - $" + (int)p.getPrecio() + " (Stock: " + p.getStock() + ")");
            } else {
                System.out.println(i + ". " + p.getNombre() + " - AGOTADO");
            }
            i++;
        }
    }

    /*Lista de todos los productos en el inventario*/
     
    public List<Producto> getProductos() {
        return productos;
    }

  
     
    /* El producto con más unidades vendidas, o null si no hay productos*/
   
    public Producto getMasVendido() {
        Producto mejor = null;
        for (Producto p : productos) {
            if (mejor == null || p.getVendidos() > mejor.getVendidos()) mejor = p;
        }
        return mejor;
    }

  /*El producto con menos unidades vendidas, o null si no hay productos*/
    
    public Producto getMenosVendido() {
        Producto peor = null;
        for (Producto p : productos) {
            if (peor == null || p.getVendidos() < peor.getVendidos()) peor = p;
        }
        return peor;
    }
    
   
     /* Restaura el stock de productos agotados a sus valores iniciales
     * Útil para reponer automáticamente al reiniciar el sistema*/
    
    public void restaurarStockAgotado() {
        // Mapa con los stocks iniciales por nombre de producto
        java.util.Map<String, Integer> stocksIniciales = new java.util.HashMap<>();
        stocksIniciales.put("Cafe", 20);
        stocksIniciales.put("Capuchino", 12);
        stocksIniciales.put("Pan de queso", 15);
        stocksIniciales.put("Jugo natural", 8);
        
        for (Producto producto : productos) {
            String nombreProducto = producto.getNombre();
            Integer stockInicialDefinido = stocksIniciales.get(nombreProducto);
            
            if (stockInicialDefinido != null) {
                // Establecer el stock inicial si no está configurado
                if (producto.getStockInicial() == 0) {
                    producto.setStockInicial(stockInicialDefinido);
                }
                
                // Si el producto está agotado, restaurar su stock
                if (producto.getStock() == 0) {
                    producto.setStock(stockInicialDefinido);
                    Logger.info("Stock restaurado automaticamente para " + nombreProducto + ": " + stockInicialDefinido);
                }
            }
        }
    }
}
