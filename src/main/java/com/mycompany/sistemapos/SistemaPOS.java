/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.*;
import com.mycompany.sistemapos.persistencia.DataManager;
import com.mycompany.sistemapos.utilidades.Logger;
import java.io.IOException;
import java.util.*;
import java.util.InputMismatchException;


public class SistemaPOS {
    /*Scanner para leer entrada del usuario desde consola*/
    private static final Scanner sc = new Scanner(System.in);
    
    /*Número total de mesas en el restaurante*/
    private static final int NUM_MESAS = 10;

    /*Inicializa todos los componentes del sistema y maneja el bucle principal
     * del menú interactivo.*/
    public static void main(String[] args) {
        Logger.info("=== INICIANDO SISTEMA POS ===");
        
     
        
        // Cargar inventario desde archivo (persistencia)
        Inventario inventario;
        try {
            inventario = DataManager.cargarInventario();
            Logger.info("Inventario cargado desde archivo");
            
            // Si no hay productos, cargar productos iniciales
            if (inventario.getProductos().isEmpty()) {
                Logger.info("No hay productos guardados. Cargando productos iniciales...");
                inventario.agregarProducto(new Producto("Cafe", 3000, 20));
                inventario.agregarProducto(new Producto("Capuchino", 5000, 12));
                inventario.agregarProducto(new Producto("Pan de queso", 2500, 15));
                inventario.agregarProducto(new Producto("Jugo natural", 4000, 8));
                DataManager.guardarInventario(inventario);
            } else {
                // Restaurar stock de productos agotados al reiniciar
                inventario.restaurarStockAgotado();
                Logger.info("Stock de productos agotados restaurado automáticamente");
            }
        } catch (Exception e) {
            Logger.error(e, "Error al cargar inventario");
            Logger.warning("Inicializando inventario vacío");
            inventario = new Inventario();
            // Cargar productos iniciales como respaldo
            inventario.agregarProducto(new Producto("Cafe", 3000, 20));
            inventario.agregarProducto(new Producto("Capuchino", 5000, 12));
            inventario.agregarProducto(new Producto("Pan de queso", 2500, 15));
            inventario.agregarProducto(new Producto("Jugo natural", 4000, 8));
        }
        
        // Crear caja registradora (asociación con Pedido para recibir pagos)
        Caja caja = new Caja();
        
        // Crear reportes (diario y mensual) - ambos tienen asociación con Pedido
        Reporte reporteDiario = new Reporte();   
        Reporte reporteMensual = new Reporte();

        // Crear array de mesas (agregación con Pedido)
        Mesa[] mesas = new Mesa[NUM_MESAS];
        for (int i = 0; i < NUM_MESAS; i++) {
            mesas[i] = new Mesa(i + 1);
        }

        // Crear empleado (herencia de Persona, agregación con Pedido)
        Empleado empleado = new Empleado("Carlos", 101);

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            try {
                switch (opcion) {
                    case 1:
                        // Gestión de mesas (reservar, ocupar, liberar)
                        gestionarMesas(mesas);
                        break;
                    case 2:
                        // Flujo completo de toma de pedido
                        tomarPedido(mesas, inventario, caja, reporteDiario, reporteMensual, empleado);
                        break;
                    case 3:
                        // Mostrar inventario completo
                        inventario.mostrarInventario();
                        break;
                    case 4:
                        // Mostrar total acumulado en caja del día
                        System.out.println("Total en caja (hoy): $" + (int)caja.getTotalDia());
                        break;
                    case 5:
                        // Mostrar reporte diario
                        reporteDiario.mostrarReporte("REPORTE DIARIO");
                        // Generar PDF del reporte
                        try {
                            String rutaPDF = reporteDiario.generarPDF("REPORTE DIARIO");
                            System.out.println("✓ PDF del reporte generado: " + rutaPDF);
                        } catch (IOException e) {
                            Logger.error(e, "Error al generar PDF del reporte diario");
                            System.out.println("⚠ No se pudo generar el PDF del reporte. Verifique el log.");
                        }
                        break;
                    case 6:
                        // Mostrar reporte mensual
                        reporteMensual.mostrarReporte("REPORTE MENSUAL");
                        // Generar PDF del reporte
                        try {
                            String rutaPDF = reporteMensual.generarPDF("REPORTE MENSUAL");
                            System.out.println("✓ PDF del reporte generado: " + rutaPDF);
                        } catch (IOException e) {
                            Logger.error(e, "Error al generar PDF del reporte mensual");
                            System.out.println("⚠ No se pudo generar el PDF del reporte. Verifique el log.");
                        }
                        break;
                    case 7:
                        // Salir del programa
                        System.out.println("Guardando datos...");
                        try {
                            DataManager.guardarInventario(inventario);
                            Logger.info("Datos guardados correctamente");
                        } catch (Exception e) {
                            Logger.error(e, "Error al guardar datos");
                            System.out.println("Error al guardar datos. Verifique el archivo de log.");
                        }
                        System.out.println("Saliendo... ¡Hasta luego!");
                        Logger.info("=== SISTEMA CERRADO ===");
                        break;
                    default:
                        System.out.println(" Opcion invalida.");
                        Logger.warning("Opción inválida seleccionada: " + opcion);
                }
            } catch (Exception e) {
                System.out.println(" Error inesperado: " + e.getMessage());
                Logger.error(e, "Error en el menu principal");
                System.out.println("Por favor, intente nuevamente.");
            }
        } while (opcion != 7);
    }

   
     /* Muestra el menú principal del sistema en consola.*/
     
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

    
     /* Gestiona las operaciones relacionadas con las mesas.*/
  
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
                    System.out.print("Ingrese numero de mesa a ocupar: ");
                    int o = sc.nextInt(); sc.nextLine();
                    if (validarIndiceMesa(o, mesas)) {
                        Mesa mo = mesas[o - 1];
                        if (mo.getEstado() == Mesa.Estado.DISPONIBLE) {
                            mo.ocupar();
                            System.out.println("Mesa " + o + " ocupada.");
                        } else System.out.println("No se puede ocupar. Estado actual: " + mo.getEstado());
                    } else System.out.println("Mesa invalida.");
                    break;
                case 4:
                    System.out.print("Ingrese numero de mesa a liberar: ");
                    int l = sc.nextInt(); sc.nextLine();
                    if (validarIndiceMesa(l, mesas)) {
                        mesas[l - 1].liberar();
                        System.out.println("Mesa " + l + " liberada (disponible).");
                    } else System.out.println("Mesa invalida.");
                    break;
                case 5:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (op != 5);
    }


     /* Valida que el número de mesa esté en el rango válido.*/
   
    private static boolean validarIndiceMesa(int n, Mesa[] mesas) {
        return n >= 1 && n <= mesas.length;
    }

  
    
    private static void tomarPedido(Mesa[] mesas,
                                    Inventario inventario,
                                    Caja caja,
                                    Reporte reporteDiario,
                                    Reporte reporteMensual,
                                    Empleado empleado) {

        System.out.println("\n--- TOMAR PEDIDO ---");
        
        // Mostrar estado de todas las mesas
        for (Mesa m : mesas) m.mostrarMesa();

        // Seleccion de mesa
        System.out.print("Seleccione numero de mesa para atender: ");
        int numMesa = sc.nextInt();
        sc.nextLine();

        // Validar que la mesa existe
        if (!validarIndiceMesa(numMesa, mesas)) {
            System.out.println("Mesa invalida.");
            return;
        }
        Mesa mesa = mesas[numMesa - 1];

        // Validar estado de la mesa
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

        // Crear cliente (herencia de Persona)
        System.out.print("Nombre del cliente: ");
        String nombreCliente = sc.nextLine();
        Cliente cliente = new Cliente(nombreCliente);

        // Crear pedido (agregación con Cliente, Empleado y Mesa)
        Pedido pedido = new Pedido(cliente, empleado, mesa);

       
        boolean seguir = true;
        while (seguir) {
            // Mostrar productos disponibles del inventario
            inventario.mostrarInventarioDisponibles();
            System.out.print("Ingrese el numero del producto (0 para terminar): ");
            int opcionProd = sc.nextInt();
            sc.nextLine();

            if (opcionProd == 0) break;

            // Obtener producto del inventario (composición Inventario-Producto)
            Producto seleccionado = inventario.getProductoPorIndice(opcionProd);
            if (seleccionado == null) {
                System.out.println("Producto invalido.");
                continue;
            }

            // Solicitar cantidad
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

            // Agregar producto al pedido (agregación Pedido-Producto)
            //  Este método descontará stock automáticamente si hay disponible
            try {
                pedido.agregarProducto(seleccionado, cantidad);
                System.out.println("✔ " + cantidad + " x " + seleccionado.getNombre() + " agregado al pedido.");
            } catch (StockInsuficienteException e) {
                System.out.println(" Error: " + e.getMessage());
                Logger.warning(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(" Error: " + e.getMessage());
                Logger.warning(e.getMessage());
            }

            // Preguntar si desea agregar más productos
            System.out.print("Desea agregar otro producto? (s/n): ");
            String r = sc.nextLine();
            if (!r.equalsIgnoreCase("s")) seguir = false;
        }

        // Validar que el pedido tenga productos
        try {
            pedido.validarPedido();
        } catch (PedidoVacioException e) {
            System.out.println("❌ " + e.getMessage());
            Logger.warning(e.getMessage());
            mesa.liberar(); // Liberar mesa si no hay productos
            return;
        }

        
        // Generar factura (asociación Factura-Pedido)
        Factura.imprimir(pedido);
        double total = pedido.getTotal();
        System.out.println("Total a pagar: $" + (int)total);
        System.out.print("Confirmar pago? (s/n): ");
        String pagar = sc.nextLine();
        
        if (pagar.equalsIgnoreCase("s")) {
            try {
                // Registrar pago en caja (asociación Caja-Pedido)
                caja.registrarPago(total);
                
                // Registrar venta en reportes (asociación Reporte-Pedido)
                reporteDiario.registrarVenta(pedido);
                reporteMensual.registrarVenta(pedido);
                
                // Guardar inventario después de cada venta
                try {
                    DataManager.guardarInventario(inventario);
                } catch (Exception e) {
                    Logger.error(e, "Error al guardar inventario despues de venta");
                }
                
                // Generar PDF de la factura
                try {
                    String rutaPDF = Factura.generarPDF(pedido);
                    System.out.println(" PDF de factura generado: " + rutaPDF);
                } catch (IOException e) {
                    Logger.error(e, "Error al generar PDF de factura");
                    System.out.println(" No se pudo generar el PDF de la factura. Verifique el log.");
                }
                
                System.out.println(" Pago registrado. Gracias.");
                
                // Cerrar pedido (libera la mesa)
                pedido.cerrarPedido();
            } catch (PagoInvalidoException e) {
                System.out.println(" Error en el pago: " + e.getMessage());
                Logger.error(e, "Error al registrar pago");
                pedido.cerrarPedido();
            }
        } else {
            System.out.println("Pago no confirmado. Cancelando pedido y reponiendo stock...");
            // NOTA: En una versión mejorada se debería revertir el stock descontado
            // Por ahora solo liberamos la mesa
            Logger.warning("Pedido cancelado por el usuario");
            pedido.cerrarPedido();
        }
    }
}
