/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.ProductoNoEncontradoException;
import com.mycompany.sistemapos.utilidades.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE: Inventario
 * 
 * Gestiona todos los productos disponibles en el sistema.
 * Es el único lugar donde se almacenan y gestionan los productos.
 * 
 * RELACIONES:
 * - COMPOSICIÓN con Producto: El inventario contiene una lista de productos.
 *   Los productos son gestionados por el inventario y sin él no tendrían sentido
 *   en el contexto del sistema. Si se elimina el inventario, se pierde la
 *   referencia a todos los productos.
 * 
 * RESPONSABILIDADES:
 * - Almacenar todos los productos del sistema
 * - Buscar productos por nombre o índice
 * - Mostrar el inventario completo o solo productos disponibles
 * - Calcular estadísticas (más vendido, menos vendido)
 * 
 * PATRÓN: Contenedor (Container pattern) - Agrupa objetos relacionados
 */
public class Inventario {
    /**
     * Lista que contiene todos los productos del inventario (COMPOSICIÓN)
     * Los productos son gestionados exclusivamente por esta clase.
     */
    private List<Producto> productos = new ArrayList<>();

    /**
     * Agrega un producto al inventario.
     * 
     * @param p Producto a agregar al inventario
     */
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    /**
     * Busca un producto por su nombre (búsqueda case-insensitive).
     * 
     * @param nombre Nombre del producto a buscar
     * @return El producto encontrado, o null si no existe
     */
    public Producto buscarProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }
    
    /**
     * Busca un producto por su nombre y lanza excepción si no se encuentra.
     * 
     * @param nombre Nombre del producto a buscar
     * @return El producto encontrado
     * @throws ProductoNoEncontradoException Si el producto no existe
     */
    public Producto buscarProductoOErro(String nombre) throws ProductoNoEncontradoException {
        Producto producto = buscarProducto(nombre);
        if (producto == null) {
            throw new ProductoNoEncontradoException(nombre);
        }
        return producto;
    }

    /**
     * Obtiene un producto por su índice en la lista (1-based para menús de usuario).
     * 
     * @param index Índice del producto (empieza en 1, no en 0)
     * @return El producto en esa posición, o null si el índice es inválido
     */
    public Producto getProductoPorIndice(int index) {
        if (index < 1 || index > productos.size()) {
            Logger.warning(String.format("Índice de producto inválido: %d (rango válido: 1-%d)", 
                index, productos.size()));
            return null;
        }
        return productos.get(index - 1); // Convierte a índice 0-based
    }
    
    /**
     * Obtiene un producto por índice y lanza excepción si no existe.
     * 
     * @param index Índice del producto (1-based)
     * @return El producto en esa posición
     * @throws ProductoNoEncontradoException Si el índice es inválido
     */
    public Producto getProductoPorIndiceOErro(int index) throws ProductoNoEncontradoException {
        if (index < 1 || index > productos.size()) {
            throw new ProductoNoEncontradoException("Índice " + index);
        }
        return productos.get(index - 1);
    }

    /**
     * Muestra el inventario completo con todos los productos.
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

    /**
     * Muestra solo los productos disponibles (con stock > 0) para el menú de pedidos.
     * También muestra los productos agotados marcados como "AGOTADO".
     * 
     * Este método es usado cuando se está tomando un pedido para mostrar
     * qué productos están disponibles para ordenar.
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

    /**
     * Devuelve la lista completa de productos (solo lectura).
     * 
     * @return Lista de todos los productos en el inventario
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Encuentra el producto más vendido basado en el campo 'vendidos' de cada producto.
     * 
     * @return El producto con más unidades vendidas, o null si no hay productos
     */
    public Producto getMasVendido() {
        Producto mejor = null;
        for (Producto p : productos) {
            if (mejor == null || p.getVendidos() > mejor.getVendidos()) mejor = p;
        }
        return mejor;
    }

    /**
     * Encuentra el producto menos vendido basado en el campo 'vendidos' de cada producto.
     * Incluye productos con 0 unidades vendidas.
     * 
     * @return El producto con menos unidades vendidas, o null si no hay productos
     */
    public Producto getMenosVendido() {
        Producto peor = null;
        for (Producto p : productos) {
            if (peor == null || p.getVendidos() < peor.getVendidos()) peor = p;
        }
        return peor;
    }
}
