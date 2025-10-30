/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pos;

/**
 *
 * @author janny
 */


/**
 * 🖥 Clase principal del sistema POS.
 * Simula el proceso de ventas, caja y generación de reportes.
 */
import java.util.*;

// Clase principal que gestiona la interacción por consola
public class POS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Inicialización de componentes del sistema
        Inventario inventario = new Inventario();
        Caja caja = new Caja(100000);
        Reporte reporte = new Reporte();
        List<Mesa> mesas = new ArrayList<>();
        for (int i = 1; i <= 5; i++) mesas.add(new Mesa(i));

        Cliente cliente = new Cliente("Janny Sneider", "123456");
        Empleado empleado = new Empleado("Carlos", "987654", "Mesero");

        int opcion;
        do {
            System.out.println("\n=== SISTEMA POS CAFETERIA ===");
            System.out.println(" 1. Ver mesas");
            System.out.println(" 2. Reservar mesa");
            System.out.println(" 3.Ocupar mesa");
            System.out.println(" 4.Agregar pedido");
            System.out.println(" 5.Cerrar cuenta");
            System.out.println(" 6.Ver caja");
            System.out.println(" 7.Generar reporte");
            System.out.println(" 8.Ver inventario");
            System.out.println(" 9.salir");
            System.out.print(" Opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> mesas.forEach(System.out::println);
                case 2 -> {
                    System.out.print("Numero de mesa: ");
                    mesas.get(sc.nextInt() - 1).reservar();
                }
                case 3 -> {
                    System.out.print("Numero de mesa: ");
                    Mesa mesa = mesas.get(sc.nextInt() - 1);
                    mesa.ocupar();
                    cliente.asignarMesa(mesa.getNumero());
                }
                case 4 -> {
                    System.out.print("Numero de mesa: ");
                    Mesa mesa = mesas.get(sc.nextInt() - 1);
                    if (mesa.getPedidoActual() == null) {
                        System.out.println(" La mesa no esta ocupada");
                        break;
                    }
                    inventario.mostrarInventario();
                    System.out.print("Elige producto: ");
                    int prod = sc.nextInt() - 1;
                    System.out.print("Cantidad: ");
                    int cant = sc.nextInt();
                    mesa.getPedidoActual().agregarProducto(inventario.getProductos().get(prod), cant);
                }
                case 5 -> {
                    System.out.print("Numero de mesa a cerrar: ");
                    Mesa mesa = mesas.get(sc.nextInt() - 1);
                    if (mesa.getPedidoActual() == null) {
                        System.out.println(" No hay pedido para esta mesa.");
                        break;
                    }
                    Factura factura = new Factura(cliente, mesa.getPedidoActual());
                    factura.mostrarFactura();
                    caja.registrarVenta(factura.getTotal());
                    mesa.liberar();
                }
                case 6 -> caja.mostrarEstado();
                case 7 -> reporte.generar(caja);
                case 8 -> inventario.mostrarInventario();
                case 9 -> System.out.println(" Saliendo del sistema");
                default -> System.out.println(" Opcion invalida");
            }
        } while (opcion != 9);
    }
}