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

        mesas.add(new Mesa(1, EstadoMesa.DISPONIBLE));
        mesas.add(new Mesa(2, EstadoMesa.DISPONIBLE));
        mesas.add(new Mesa(3, EstadoMesa.RESERVADA));

        empleados.add(new Empleado(1, "Laura", Cargo.CAJERA));
        empleados.add(new Empleado(2, "Carlos", Cargo.MESERO));

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
                    System.out.print(inventario.listarProductosComoTexto());
                    break;

                case 3:
                    System.out.print("Nombre del cliente: ");
                    String nombreC = sc.next();
                    Cliente cliente = new Cliente(clientes.size() + 1, nombreC);
                    clientes.add(cliente);

                    System.out.print("ID del empleado (1-2): ");
                    int idEmp = sc.nextInt();
                    Empleado emp = empleados.get(idEmp - 1);

                    Mesa mesa = null;
                    while (true) {
                        System.out.print("Número de mesa (1-3): ");
                        int numMesa = sc.nextInt();
                        mesa = mesas.get(numMesa - 1);
                        // Validación básica para no asignar mesa ocupada o reservada
                        if (mesa.getEstado() == EstadoMesa.DISPONIBLE) {
                            mesa.setEstado(EstadoMesa.OCUPADA);
                            break;
                        } else {
                            System.out.println("La mesa seleccionada no está disponible. Elija otra.");
                        }
                    }

                    Pedido pedido = new Pedido(idPedido++, cliente, emp, mesa);

                    while (true) {
                        System.out.print(inventario.listarProductosComoTexto());
                        System.out.print("Ingrese ID del producto (0 para terminar): ");
                        int idProd = sc.nextInt();
                        if (idProd == 0) break;
                        System.out.print("Cantidad: ");
                        int cantidad = sc.nextInt();

                        Producto p = inventario.buscarProductoPorId(idProd);
                        if (p != null) {
                            boolean ok = pedido.agregarProducto(p, cantidad);
                            if (!ok) System.out.println("❌ No hay suficiente stock para " + p.getNombre());
                            else System.out.println("Producto agregado: " + p.getNombre() + " x" + cantidad);
                        }
                    }

                    Factura factura = new Factura(idFactura++, pedido);
                    System.out.print(factura.comoTexto());
                    reporte.agregarFactura(factura);
                    mesa.setEstado(EstadoMesa.DISPONIBLE);
                    break;

                case 4:
                    System.out.print(reporte.comoTexto());
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
