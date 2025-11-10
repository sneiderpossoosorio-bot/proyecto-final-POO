/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.PedidoVacioException;
import com.mycompany.sistemapos.excepciones.StockInsuficienteException;
import com.mycompany.sistemapos.utilidades.Logger;
import java.util.HashMap;
import java.util.Map;


public class Pedido {
  
     /* Cliente que realiza el pedido (AGREGACIÓN)*/ 
    private Cliente cliente;
    
     /* Empleado que atiende el pedido (AGREGACIÓN)*/
    private Empleado empleado;
  
     /* Mesa donde se sirve el pedido (AGREGACIÓN)*/
   private Mesa mesa;
    
    /*
      Mapa que relaciona productos con sus cantidades (AGREGACIÓN)
      Clave: Producto, Valor: Cantidad solicitada*/
    private Map<Producto, Integer> items = new HashMap<>();
    
     /* Total calculado del pedido (suma de todos los productos)*/
    private double total = 0.0;

    /* Constructor de Pedido 
      Crea un nuevo pedido asociando cliente, empleado y mesa.*/
    public Pedido(Cliente cliente, Empleado empleado, Mesa mesa) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.mesa = mesa;
        // Al iniciar un pedido se ocupa la mesa (si está libre)
        // La validación previa de que la mesa esté disponible se hace en SistemaPOS
        this.mesa.ocupar();
    }

  
     /*  StockInsuficienteException Si no hay suficiente stock del producto
     *  IllegalArgumentException Si la cantidad es */
      public void agregarProducto(Producto producto, int cantidad) throws StockInsuficienteException {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser null");
        }
        
        // Intenta descontar stock (puede lanzar StockInsuficienteException)
        producto.descontarStock(cantidad);
        
        // Si llegamos aquí, hay stock suficiente
        items.put(producto, items.getOrDefault(producto, 0) + cantidad);
        total += producto.getPrecio() * cantidad;
        
        Logger.info(String.format("Producto agregado al pedido: %s x%d", producto.getNombre(), cantidad));
    }
    
   
    public boolean agregarProductoLegacy(Producto producto, int cantidad) {
        try {
            agregarProducto(producto, cantidad);
            return true;
        } catch (StockInsuficienteException e) {
            Logger.warning(e.getMessage());
            return false;
        }
    }
    
    /**
     * Valida que el pedido tenga al menos un producto
      PedidoVacioException Si el pedido está vacío
     */
    public void validarPedido() throws PedidoVacioException {
        if (items.isEmpty()) {
            throw new PedidoVacioException();
        }
    }

    // Getters
    public Map<Producto, Integer> getItems() { return items; }
    public double getTotal() { return total; }
    public Cliente getCliente() { return cliente; }
    public Empleado getEmpleado() { return empleado; }
    public Mesa getMesa() { return mesa; }

    /* imprime el pedido a detalle del de la consola*/
    public void mostrarDetalle() {
        System.out.println("\n--- Detalle del pedido ---");
        // Itera sobre todos los productos en el pedido
        for (Map.Entry<Producto, Integer> e : items.entrySet()) {
            Producto p = e.getKey();
            int cant = e.getValue();
            System.out.println(p.getNombre() + " x" + cant + " = $" + (int)(p.getPrecio()*cant));
        }
        System.out.println("TOTAL: $" + (int)total);
    }

    
     /* Cierra el pedido y libera la mesa.*/
    
    public void cerrarPedido() {
        mesa.liberar(); // La mesa vuelve a estar disponible
    }
}