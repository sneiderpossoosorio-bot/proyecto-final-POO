/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemapos;


import java.util.*;

import java.util.InputMismatchException;

import java.util.InputMismatchException;

/**
 * Main: menú por consola que integra todo.
 * Incluye:
 * - Gestión de mesas (ver, reservar, ocupar, liberar)
 * - Tomar pedido (asigna cliente, mesa, empleado; valida inventario)
 * - Mostrar inventario
 * - Mostrar reportes (diario y mensual) — en este ejemplo ambos usan el mismo Reporte en memoria
 * - Caja (total del día)
 *
 * Nota: por simplicidad inicial usamos 10 mesas. Cambia NUM_MESAS si quieres otro número.
 */
public class SistemaPOS {
    private static final Scanner sc = new Scanner(System.in);
    private static final int NUM_MESAS = 10;

    public static void main(String[] args) {
        // Inicialización de componentes
        Inventario inventario = new Inventario();
        Caja caja = new Caja();
        Reporte reporteDiario = new Reporte();   // reporte del día
        Reporte reporteMensual = new Reporte();  // reporte del mes (en memoria)

        // Crear algunos productos iniciales (puedes agregar/reponer desde el código o extender con opción)
        inventario.agregarProducto(new Producto("Cafe", 3000, 20));
        inventario.agregarProducto(new Producto("Capuchino", 5000, 12));
        inventario.agregarProducto(new Producto("Pan de queso", 2500, 15));
        inventario.agregarProducto(new Producto("Jugo natural", 4000, 8));

        // Crear mesas
        Mesa[] mesas = new Mesa[NUM_MESAS];
        for (int i = 0; i < NUM_MESAS; i++) mesas[i] = new Mesa(i + 1);

        // Empleado "logueado" por defecto (puedes ampliar para login)
        Empleado empleado = new Empleado("Carlos", 101);

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    gestionarMesas(mesas);
                    break;
                case 2:
                    tomarPedido(mesas, inventario, caja, reporteDiario, reporteMensual, empleado);
                    break;
                case 3:
                    inventario.mostrarInventario();
                    break;
                case 4:
                    System.out.println("Total en caja (hoy): $" + (int)caja.getTotalDia());
                    break;
                case 5:
                    reporteDiario.mostrarReporte("REPORTE DIARIO");
                    break;
                case 6:
                    reporteMensual.mostrarReporte("REPORTE MENSUAL");
                    break;
                case 7:
                    System.out.println("Saliendo... ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 7);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== POS CAFETERIA  =====");
        System.out.println("1. Gestion de mesas");
        System.out.println("2. Tomar pedido");
        System.out.println("3. Ver inventario");
        System.out.println("4. Ver total en caja (dia)");
        System.out.println("5. Reporte diario");
        System.out.println("6. Reporte mensual");
        System.out.println("7. Salir");
        System.out.print("Seleccione opcion: ");
    }

    // Menú para gestionar mesas
    private static void gestionarMesas(Mesa[] mesas) {
        int op;
        do {
            System.out.println("\n--- GESTION DE MESAS ---");
            System.out.println("1. Ver estado de mesas");
            System.out.println("2. Reservar mesa");
            System.out.println("3. Ocupar mesa");
            System.out.println("4. Liberar mesa");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    for (Mesa m : mesas) m.mostrarMesa();
                    break;
                case 2:
                    System.out.print("Ingrese numero de mesa a reservar: ");
                    int r = sc.nextInt(); sc.nextLine();
                    if (validarIndiceMesa(r, mesas)) {
                        Mesa mr = mesas[r - 1];
                        if (mr.getEstado() == Mesa.Estado.DISPONIBLE) {
                            mr.reservar();
                            System.out.println("Mesa " + r + " reservada.");
                        } else System.out.println("No se puede reservar. Estado actual: " + mr.getEstado());
                    } else System.out.println("Mesa inválida.");
                    break;
                case 3:
                    System.out.print("Ingrese número de mesa a ocupar: ");
                    int o = sc.nextInt(); sc.nextLine();
                    if (validarIndiceMesa(o, mesas)) {
                        Mesa mo = mesas[o - 1];
                        if (mo.getEstado() == Mesa.Estado.DISPONIBLE) {
                            mo.ocupar();
                            System.out.println("Mesa " + o + " ocupada.");
                        } else System.out.println("No se puede ocupar. Estado actual: " + mo.getEstado());
                    } else System.out.println("Mesa inválida.");
                    break;
                case 4:
                    System.out.print("Ingrese número de mesa a liberar: ");
                    int l = sc.nextInt(); sc.nextLine();
                    if (validarIndiceMesa(l, mesas)) {
                        mesas[l - 1].liberar();
                        System.out.println("Mesa " + l + " liberada (disponible).");
                    } else System.out.println("Mesa inválida.");
                    break;
                case 5:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (op != 5);
    }

    private static boolean validarIndiceMesa(int n, Mesa[] mesas) {
        return n >= 1 && n <= mesas.length;
    }

    // Tomar pedido: flujo completo por consola
    private static void tomarPedido(Mesa[] mesas,
                                    Inventario inventario,
                                    Caja caja,
                                    Reporte reporteDiario,
                                    Reporte reporteMensual,
                                    Empleado empleado) {

        System.out.println("\n--- TOMAR PEDIDO ---");
        // Mostrar mesas con estado
        for (Mesa m : mesas) m.mostrarMesa();

        System.out.print("Seleccione numero de mesa para atender: ");
        int numMesa = sc.nextInt();
        sc.nextLine();

        if (!validarIndiceMesa(numMesa, mesas)) {
            System.out.println("Mesa invalida.");
            return;
        }
        Mesa mesa = mesas[numMesa - 1];

        // No permitir tomar pedido si mesa está ocupada o reservada
        if (mesa.getEstado() == Mesa.Estado.OCUPADA) {
            System.out.println(" La mesa esta ocupada. No se puede tomar otro pedido.");
            return;
        }
        if (mesa.getEstado() == Mesa.Estado.RESERVADA) {
            System.out.println(" La mesa esta reservada. Confirma si quieres usarla (s/n): ");
            String resp = sc.nextLine();
            if (!resp.equalsIgnoreCase("s")) {
                System.out.println("Pedido cancelado por reserva.");
                return;
            } else {
                // Si confirma, la ocupamos y seguimos
                mesa.ocupar();
            }
        }

        System.out.print("Nombre del cliente: ");
        String nombreCliente = sc.nextLine();
        Cliente cliente = new Cliente(nombreCliente);

        Pedido pedido = new Pedido(cliente, empleado, mesa);

        // Bucle para agregar productos
        boolean seguir = true;
        while (seguir) {
            inventario.mostrarInventarioDisponibles();
            System.out.print("Ingrese el numero del producto (0 para terminar): ");
            int opcionProd;
            opcionProd = sc.nextInt(); // limpiar
            sc.nextLine();

            if (opcionProd == 0) break;

            Producto seleccionado = inventario.getProductoPorIndice(opcionProd);
            if (seleccionado == null) {
                System.out.println("Producto invalido.");
                continue;
            }

            System.out.print("Cantidad: ");
            int cantidad;
            try {
                cantidad = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Cantidad invalida.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            // Intentar agregar al pedido (esto validará y descontará stock)
            if (pedido.agregarProducto(seleccionado, cantidad)) {
                System.out.println("✔ " + cantidad + " x " + seleccionado.getNombre() + " agregado al pedido.");
            } else {
                System.out.println(" Inventario insuficiente para " + seleccionado.getNombre() + " (Stock: " + seleccionado.getStock() + ")");
            }

            System.out.print("Desea agregar otro producto? (s/n): ");
            String r = sc.nextLine();
            if (!r.equalsIgnoreCase("s")) seguir = false;
        }

        // Si no se agregaron productos, cancelar el pedido y dejar mesa disponible
        if (pedido.getItems().isEmpty()) {
            System.out.println("Pedido vacio. No se creo factura.");
            mesa.liberar();
            return;
        }

        // Mostrar y cobrar
        Factura.imprimir(pedido);
        double total = pedido.getTotal();
        System.out.println("Total a pagar: $" + (int)total);
        System.out.print("Confirmar pago? (s/n): ");
        String pagar = sc.nextLine();
        if (pagar.equalsIgnoreCase("s")) {
            caja.registrarPago(total);                      // registrar en caja
            reporteDiario.registrarVenta(pedido);           // acumular en reporte diario
            reporteMensual.registrarVenta(pedido);          // acumular en reporte mensual
            System.out.println("Pago registrado. Gracias.");
            pedido.cerrarPedido();                          // libera la mesa
        } else {
            System.out.println("Pago no confirmado. Cancelando pedido y reponiendo stock...");
            // Si no se paga, debemos revertir el stock y vendidos (podemos implementar revert)
            // Para simplificar por ahora: no revertimos automáticamente; en una versión mejor se implementa revertir.
            // Liberamos la mesa:
            pedido.cerrarPedido();
        }
    }
}
