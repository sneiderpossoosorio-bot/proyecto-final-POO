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

/**
 * CLASE: Pedido
 * 
 * Representa un pedido completo realizado por un cliente en una mesa.
 * Es el núcleo central del sistema, ya que agrupa todas las entidades relacionadas
 * con una transacción de venta.
 * 
 * RELACIONES (TODAS SON AGREGACIÓN):
 * - AGREGACIÓN con Cliente: El pedido tiene una referencia al cliente que lo realiza
 * - AGREGACIÓN con Empleado: El pedido tiene una referencia al empleado que lo atiende
 * - AGREGACIÓN con Mesa: El pedido tiene una referencia a la mesa donde se sirve
 * - AGREGACIÓN con Producto: El pedido contiene productos (Map<Producto, Integer>)
 * 
 * RESPONSABILIDADES:
 * - Agregar productos al pedido (descontando stock automáticamente)
 * - Calcular el total del pedido
 * - Gestionar el estado de la mesa (ocupar al crear, liberar al cerrar)
 * - Mostrar el detalle del pedido
 * 
 * FLUJO:
 * 1. Se crea el pedido con Cliente, Empleado y Mesa → la mesa se ocupa automáticamente
 * 2. Se agregan productos (descontando stock)
 * 3. Se calcula el total
 * 4. Se genera la factura
 * 5. Se registra el pago
 * 6. Se cierra el pedido → la mesa se libera
 */
public class Pedido {
    /**
     * Cliente que realiza el pedido (AGREGACIÓN)
     */
    private Cliente cliente;
    
    /**
     * Empleado que atiende el pedido (AGREGACIÓN)
     */
    private Empleado empleado;
    
    /**
     * Mesa donde se sirve el pedido (AGREGACIÓN)
     */
    private Mesa mesa;
    
    /**
     * Mapa que relaciona productos con sus cantidades (AGREGACIÓN)
     * Clave: Producto, Valor: Cantidad solicitada
     */
    private Map<Producto, Integer> items = new HashMap<>();
    
    /**
     * Total calculado del pedido (suma de todos los productos)
     */
    private double total = 0.0;

    /**
     * Constructor de Pedido
     * 
     * Crea un nuevo pedido asociando cliente, empleado y mesa.
     * Al crear el pedido, automáticamente ocupa la mesa.
     * 
     * @param cliente Cliente que realiza el pedido
     * @param empleado Empleado que atiende el pedido
     * @param mesa Mesa donde se sirve el pedido
     */
    public Pedido(Cliente cliente, Empleado empleado, Mesa mesa) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.mesa = mesa;
        // Al iniciar un pedido se ocupa la mesa (si está libre)
        // La validación previa de que la mesa esté disponible se hace en SistemaPOS
        this.mesa.ocupar();
    }

    /**
     * Agrega una cantidad de un producto al pedido.
     * 
     * PROCESO:
     * 1. Valida y descuenta stock del producto (llama a producto.descontarStock())
     * 2. Si hay stock suficiente, agrega el producto al Map de items
     * 3. Actualiza el total del pedido
     * 
     * @param producto Producto a agregar
     * @param cantidad Cantidad a agregar
     * @throws StockInsuficienteException Si no hay suficiente stock del producto
     * @throws IllegalArgumentException Si la cantidad es inválida
     */
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
    
    /**
     * Versión compatible que retorna boolean (para compatibilidad)
     * @param producto Producto a agregar
     * @param cantidad Cantidad a agregar
     * @return true si se pudo agregar, false si no hay stock suficiente
     * @deprecated Usar agregarProducto(Producto, int) que lanza excepción
     */
    @Deprecated
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
     * @throws PedidoVacioException Si el pedido está vacío
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

    /**
     * Imprime un resumen detallado del pedido en consola.
     * Muestra todos los productos con sus cantidades y precios,
     * y el total del pedido.
     * 
     * Este método es usado por Factura.imprimir() para mostrar el detalle.
     */
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

    /**
     * Cierra el pedido y libera la mesa.
     * 
     * Este método debe llamarse después de registrar el pago.
     * Libera la mesa para que pueda ser usada por otro pedido.
     * 
     * NOTA: En el flujo actual, el cobro se maneja en SistemaPOS con Caja.registrarPago(),
     * pero este método se encarga de la limpieza final (liberar la mesa).
     */
    public void cerrarPedido() {
        mesa.liberar(); // La mesa vuelve a estar disponible
    }
}