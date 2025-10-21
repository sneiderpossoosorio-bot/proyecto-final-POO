/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemapos;

/**
 *
 * @author janny
 */

import java.util.*;

public class SistemaPOS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Inventario inventario = new Inventario();
        ArrayList<Mesa> mesas = new ArrayList<>();
        ArrayList<Empleado> empleados = new ArrayList<>();
        ArrayList<Cliente> clientes = new ArrayList<>();
        Reporte reporte = new Reporte();

        inventario.agregarProducto(new Producto(1, "Café", 2500, 20));
        inventario.agregarProducto(new Producto(2, "Galleta", 1500, 15));
        inventario.agregarProducto(new Producto(3, "Sandwich", 7000, 10));

        mesas.add(new Mesa(1, "DISPONIBLE"));
        mesas.add(new Mesa(2, "DISPONIBLE"));
        mesas.add(new Mesa(3, "RESERVADA"));

        empleados.add(new Empleado(1, "Laura", "Cajera"));
        empleados.add(new Empleado(2, "Carlos", "Mesero"));

        int idPedido = 1, idFactura = 1;

        while (true) {
            System.out.println("\n=== SISTEMA POS CAFETERÍA ===");
            System.out.println("1. Ver mesas");
            System.out.println("2. Ver productos");
            System.out.println("3. Crear pedido");
            System.out.println("4. Ver reporte de ventas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    for (Mesa m : mesas) System.out.println(m);
                    break;

                case 2:
                    inventario.mostrarProductos();
                    break;

                case 3:
                    System.out.print("Nombre del cliente: ");
                    String nombreC = sc.next();
                    Cliente cliente = new Cliente(clientes.size() + 1, nombreC);
                    clientes.add(cliente);

                    System.out.print("ID del empleado (1-2): ");
                    int idEmp = sc.nextInt();
                    Empleado emp = empleados.get(idEmp - 1);

                    System.out.print("Número de mesa (1-3): ");
                    int numMesa = sc.nextInt();
                    Mesa mesa = mesas.get(numMesa - 1);
                    mesa.setEstado("OCUPADA");

                    Pedido pedido = new Pedido(idPedido++, cliente, emp, mesa);

                    while (true) {
                        inventario.mostrarProductos();
                        System.out.print("Ingrese ID del producto (0 para terminar): ");
                        int idProd = sc.nextInt();
                        if (idProd == 0) break;
                        System.out.print("Cantidad: ");
                        int cantidad = sc.nextInt();

                        Producto p = inventario.buscarProductoPorId(idProd);
                        if (p != null) pedido.agregarProducto(p, cantidad);
                    }

                    Factura factura = new Factura(idFactura++, pedido);
                    factura.imprimirFactura();
                    reporte.agregarFactura(factura);
                    mesa.setEstado("DISPONIBLE");
                    break;

                case 4:
                    reporte.mostrarReporte();
                    break;

                case 5:
                    System.out.println("Saliendo del sistema...");
                    sc.close();
                    return;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
