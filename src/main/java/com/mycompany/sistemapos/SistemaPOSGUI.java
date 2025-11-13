/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.persistencia.DataManager;
import com.mycompany.sistemapos.utilidades.Logger;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SistemaPOSGUI {
    
    private static Inventario inventario;
    private static Caja caja;
    private static Reporte reporteDiario;
    private static Reporte reporteMensual;
    private static Mesa[] mesas;
    private static Empleado empleado;
    
    private JFrame frame;
    private JPanel panelPrincipal;
    
   
     /* Método principal - Punto de entrada del programa con GUI*/
    public static void main(String[] args) {
        /* Inicializar componentes del */
        inicializarSistema();
        
        /* Crear y mostrar la interfaz gráfica*/
        SwingUtilities.invokeLater(() -> {
            new SistemaPOSGUI().crearYMostrarGUI();
        });
    }
    
    
     /* Inicializa todos los componentes del sistema*/
    private static void inicializarSistema() {
        Logger.info("=== INICIANDO SISTEMA POS (GUI) ===");
        
        /* Cargar inventario desde archivo*/
        try {
            inventario = DataManager.cargarInventario();
            Logger.info("Inventario cargado desde archivo");
            
            if (inventario.getProductos().isEmpty()) {
                Logger.info("No hay productos guardados. Cargando productos iniciales...");
                inventario.agregarProducto(new Producto("Cafe", 3000, 20));
                inventario.agregarProducto(new Producto("Capuchino", 5000, 12));
                inventario.agregarProducto(new Producto("Pan de queso", 2500, 15));
                inventario.agregarProducto(new Producto("Jugo natural", 4000, 8));
                DataManager.guardarInventario(inventario);
            } else {
                /* Restaurar stock de productos agotados al reiniciar*/
                inventario.restaurarStockAgotado();
            }
        } catch (Exception e) {
            Logger.error(e, "Error al cargar inventario");
            Logger.warning("Inicializando inventario vacío");
            inventario = new Inventario();
            inventario.agregarProducto(new Producto("Cafe", 3000, 20));
            inventario.agregarProducto(new Producto("Capuchino", 5000, 12));
            inventario.agregarProducto(new Producto("Pan de queso", 2500, 15));
            inventario.agregarProducto(new Producto("Jugo natural", 4000, 8));
        }
        
        /* Crear componentes*/
        caja = new Caja();
        reporteDiario = new Reporte();
        reporteMensual = new Reporte();
        
        /* Crear */
        final int NUM_MESAS = 10;
        mesas = new Mesa[NUM_MESAS];
        for (int i = 0; i < NUM_MESAS; i++) {
            mesas[i] = new Mesa(i + 1);
        }
        
        /* Crear empleado (se usará cuando se implemente tomar pedido*/
        empleado = new Empleado("Carlos", 101);
    }
    
    
     /* Crea y muestra la interfaz gráfica del menú principal*/
    private void crearYMostrarGUI() {
        // Crear ventana principal
        frame = new JFrame("Sistema POS - Cafetería");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        
        // Crear panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(20, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Título
        JLabel titulo = new JLabel("POS CAFETERÍA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 102, 204)); // Color azul (puedes cambiarlo)
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(7, 1, 10, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Crear botones del menú
        JButton btnMesas = crearBoton("1. Gestión de Mesas", new Color(52, 152, 219));
        JButton btnPedido = crearBoton("2. Tomar Pedido", new Color(46, 204, 113));
        JButton btnInventario = crearBoton("3. Ver Inventario", new Color(241, 196, 15));
        JButton btnCaja = crearBoton("4. Ver Total en Caja", new Color(155, 89, 182));
        JButton btnReporteDiario = crearBoton("5. Reporte Diario", new Color(230, 126, 34));
        JButton btnReporteMensual = crearBoton("6. Reporte Mensual", new Color(231, 76, 60));
        JButton btnSalir = crearBoton("7. Salir", new Color(149, 165, 166));
        
        /* Agregar listeners a los botones*/
        btnMesas.addActionListener(e -> abrirGestionMesas());
        btnPedido.addActionListener(e -> abrirTomarPedido());
        btnInventario.addActionListener(e -> mostrarInventario());
        btnCaja.addActionListener(e -> mostrarTotalCaja());
        btnReporteDiario.addActionListener(e -> mostrarReporteDiario());
        btnReporteMensual.addActionListener(e -> mostrarReporteMensual());
        btnSalir.addActionListener(e -> salir());
        
        /* Agregar botones al panel*/
        panelBotones.add(btnMesas);
        panelBotones.add(btnPedido);
        panelBotones.add(btnInventario);
        panelBotones.add(btnCaja);
        panelBotones.add(btnReporteDiario);
        panelBotones.add(btnReporteMensual);
        panelBotones.add(btnSalir);
        
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        
        /* Agregar panel a la */
        frame.add(panelPrincipal);
        
        /* Mostrar ventana*/
        frame.setVisible(true);
    }
    
    /* Crea un botón estilizado*/
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setPreferredSize(new Dimension(300, 50));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        /* Efecto hover*/
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
   
     /* Abre la gestión de mesas */
    private void abrirGestionMesas() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADO DE MESAS ===\n\n");
        
        for (Mesa m : mesas) {
            sb.append(String.format("Mesa %d - Estado: %s\n", 
                    m.getNumero(), m.getEstado()));
        }
        
        sb.append("\n--- Opciones ---\n");
        sb.append("Para reservar, ocupar o liberar mesas,\n");
        sb.append("use la versión de consola del sistema.");
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(frame, scrollPane, 
                "Gestión de Mesas", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*evento */
    private void abrirTomarPedido() {
        JOptionPane.showMessageDialog(frame,
                "Para tomar pedidos, use la versión de consola del sistema.\n" +
                "Esta funcionalidad estará disponible mas adelante.",
                "Tomar Pedido",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*Muestra el inventario */
  
    private void mostrarInventario() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INVENTARIO ===\n\n");
        
        int i = 1;
        for (Producto p : inventario.getProductos()) {
            String estado = p.getStock() > 0 ? ("Stock: " + p.getStock()) : "AGOTADO";
            sb.append(String.format("%d. %s - $%d - %s (Vendidos: %d)\n",
                    i++, p.getNombre(), (int)p.getPrecio(), estado, p.getVendidos()));
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(frame, scrollPane, "Inventario", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
     /* Muestra el total en caja*/
    private void mostrarTotalCaja() {
        double total = caja.getTotalDia();
        JOptionPane.showMessageDialog(frame,
                "Total en caja (hoy): $" + (int)total,
                "Total en Caja",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
 
     /* Muestra el reporte diario*/
    private void mostrarReporteDiario() {
        mostrarReporte(reporteDiario, "REPORTE DIARIO");
    }
    
     /* Muestra el reporte mensual */
    private void mostrarReporteMensual() {
        mostrarReporte(reporteMensual, "REPORTE MENSUAL");
    }
    
    
     /* Muestra un reporte */
    private void mostrarReporte(Reporte reporte, String titulo) {
        // Crear un StringBuilder para construir el texto del reporte
        StringBuilder sb = new StringBuilder();
        sb.append("===== ").append(titulo).append(" =====\n\n");
        
        /* Obtener información del reporte usando reflexión o métodos públicos*/
        /* Por ahora, usaremos un método auxiliar para obtener el texto*/
        String textoReporte = obtenerTextoReporte(reporte, titulo);
        
        JTextArea textArea = new JTextArea(textoReporte);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(frame, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
 
     /* Obtiene el texto del reporte */
    private String obtenerTextoReporte(Reporte reporte, String titulo) {
        return "===== " + titulo + " =====\n\n" +
               "Esta funcion todavia la estoy desarrollo.\n" +
               "Para ver reportes completos, use la versión de consola.\n\n" +
               "(Gracias por su comprension)";
    }
    
   
     /* Cierra la aplicación*/
    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(frame,
                "¿Desea guardar los datos antes de salir?",
                "Salir",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION || opcion == JOptionPane.NO_OPTION) {
            if (opcion == JOptionPane.YES_OPTION) {
                try {
                    DataManager.guardarInventario(inventario);
                    JOptionPane.showMessageDialog(frame, "Datos guardados correctamente.");
                    Logger.info("Datos guardados correctamente");
                } catch (Exception e) {
                    Logger.error(e, "Error al guardar datos");
                    JOptionPane.showMessageDialog(frame,
                            "Error al guardar datos. Verifique el archivo de log.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            Logger.info("=== SISTEMA CERRADO ===");
            System.exit(0);
        }
    }
}

