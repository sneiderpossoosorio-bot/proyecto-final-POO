/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.sistemapos;

import com.mycompany.sistemapos.excepciones.*;
import com.mycompany.sistemapos.persistencia.DataManager;
import com.mycompany.sistemapos.utilidades.Logger;
import com.mycompany.sistemapos.utilidades.IconoCafeteria;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import java.util.Map;
import java.io.IOException;


public class SistemaPOSGUI {
    
    /* ============================================
     * PALETA DE COLORES PROFESIONAL PARA CAFETERÍA/RESTAURANTE
     * Colores elegantes y sofisticados que transmiten calidez y profesionalismo
     * ============================================ */
    
    // Colores principales - Tonos café elegantes y profesionales
    private static final Color CAFE_OSCURO = new Color(89, 60, 42);           // #593C2A - Café oscuro elegante
    private static final Color CAFE_MEDIO = new Color(120, 80, 57);           // #785039 - Café medio rico
    private static final Color CAFE_CLARO = new Color(150, 100, 70);          // #966446 - Café claro cálido
    private static final Color CAFE_SUAVE = new Color(170, 130, 95);          // #AA825F - Café suave
    
    // Colores de acento - Beiges y cremas sofisticados
    private static final Color BEIGE_OSCURO = new Color(200, 175, 145);       // #C8AF91 - Beige oscuro elegante
    private static final Color BEIGE_CLARO = new Color(240, 225, 200);        // #F0E1C8 - Beige claro suave
    private static final Color CREMA = new Color(252, 245, 230);              // #FCF5E6 - Crema pura
    private static final Color MARFIL = new Color(255, 250, 240);            // #FFFAF0 - Marfil cálido
    
    // Colores de fondo y contraste
    private static final Color FONDO_PRINCIPAL = new Color(248, 242, 232);    // #F8F2E8 - Fondo principal suave
    private static final Color FONDO_SECUNDARIO = new Color(255, 252, 247);   // #FFFCF7 - Fondo secundario
    private static final Color BORDE_SUAVE = new Color(220, 200, 175);       // #DCC8AF - Borde suave
    
    // Colores de botones - Gradientes profesionales
    private static final Color BOTON_PRIMARIO = new Color(101, 67, 33);      // #654321 - Botón principal (café oscuro)
    private static final Color BOTON_SECUNDARIO = new Color(139, 90, 60);   // #8B5A3C - Botón secundario
    private static final Color BOTON_ACCION = new Color(120, 80, 57);       // #785039 - Botón de acción
    private static final Color BOTON_PELIGRO = new Color(150, 70, 50);      // #964632 - Botón de salida
    
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
        
        /* Crear mesas */
        final int NUM_MESAS = 10;
        mesas = new Mesa[NUM_MESAS];
        for (int i = 0; i < NUM_MESAS; i++) {
            mesas[i] = new Mesa(i + 1);
        }
        
        /* Crear empleado (se usará cuando se implemente tomar pedido*/
        empleado = new Empleado("Carlos", 101);
    }
    
    
     /* ============================================
      * Crea y muestra la interfaz gráfica del menú principal
      * Interfaz profesional con icono de cafetería y diseño elegante
      * ============================================ */
    private void crearYMostrarGUI() {
        // Crear ventana principal con título profesional
        frame = new JFrame("Sistema POS - Cafetería");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 750);
        frame.setLocationRelativeTo(null);
        
        // Agregar icono a la ventana (icono de la cafetería)
        try {
            ImageIcon iconoVentana = IconoCafeteria.crearIcono(64);
            frame.setIconImage(iconoVentana.getImage());
        } catch (Exception e) {
            Logger.warning("No se pudo cargar el icono de la ventana: " + e.getMessage());
        }
        
        // Crear panel principal con fondo elegante y gradiente sutil
        panelPrincipal = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gradiente sutil de fondo para dar profundidad
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, FONDO_PRINCIPAL,
                    0, getHeight(), FONDO_SECUNDARIO
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDE_SUAVE, 2, true),
            BorderFactory.createEmptyBorder(35, 35, 35, 35)
        ));
        panelPrincipal.setOpaque(false);
        
        // Panel del encabezado con icono y título
        JPanel panelEncabezado = new JPanel(new BorderLayout(15, 0));
        panelEncabezado.setOpaque(false);
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        
        // Agregar icono de la cafetería al lado del título
        ImageIcon iconoGrande = IconoCafeteria.crearIcono(80);
        JLabel labelIcono = new JLabel(iconoGrande);
        labelIcono.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Título profesional con mejor tipografía
        JLabel titulo = new JLabel("POS CAFETERÍA", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(CAFE_OSCURO);
        
        // Subtítulo elegante
        JLabel subtitulo = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitulo.setForeground(CAFE_MEDIO);
        subtitulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        // Panel para título y subtítulo
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);
        
        panelEncabezado.add(labelIcono, BorderLayout.WEST);
        panelEncabezado.add(panelTitulo, BorderLayout.CENTER);
        
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(7, 1, 10, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelBotones.setOpaque(false);
        
        // Crear botones del menú con colores profesionales y jerarquía visual
        // Botones principales (operaciones más usadas) con colores más destacados
        JButton btnPedido = crearBoton("2. Tomar Pedido", BOTON_PRIMARIO);
        JButton btnMesas = crearBoton("1. Gestión de Mesas", BOTON_SECUNDARIO);
        JButton btnInventario = crearBoton("3. Ver Inventario", BOTON_ACCION);
        JButton btnCaja = crearBoton("4. Ver Total en Caja", CAFE_SUAVE);
        JButton btnReporteDiario = crearBoton("5. Reporte Diario", BEIGE_OSCURO);
        JButton btnReporteMensual = crearBoton("6. Reporte Mensual", CAFE_CLARO);
        JButton btnSalir = crearBoton("7. Salir", BOTON_PELIGRO);
        
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
        
        /* Agregar panel a la ventana */
        frame.add(panelPrincipal);
        
        /* Mostrar ventana*/
        frame.setVisible(true);
    }
    
    /* ============================================
     * Crea un botón estilizado profesional con efectos visuales mejorados
     * Incluye gradientes, sombras y efectos hover elegantes
     * ============================================ */
    private JButton crearBoton(String texto, Color colorBase) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                // Crear gradiente para el botón
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradiente sutil de arriba a abajo
                Color colorClaro = new Color(
                    Math.min(255, colorBase.getRed() + 20),
                    Math.min(255, colorBase.getGreen() + 15),
                    Math.min(255, colorBase.getBlue() + 10)
                );
                GradientPaint gradient = new GradientPaint(
                    0, 0, colorClaro,
                    0, getHeight(), colorBase
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Dibujar borde redondeado
                g2d.setColor(colorBase.darker().darker());
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 12, 12);
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        // Configuración de fuente y tamaño profesional
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(320, 60));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover mejorado con cambio de color suave
        boton.addMouseListener(new MouseAdapter() {
            private Color colorOriginal = colorBase;
            
            @Override
            public void mouseEntered(MouseEvent e) {
                colorOriginal = colorBase;
                // Iluminar el botón al pasar el mouse
                boton.setBackground(new Color(
                    Math.min(255, colorBase.getRed() + 15),
                    Math.min(255, colorBase.getGreen() + 10),
                    Math.min(255, colorBase.getBlue() + 5)
                ));
                boton.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorOriginal);
                boton.repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                // Efecto de presión
                boton.setBackground(colorBase.darker());
                boton.repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setBackground(colorOriginal);
                boton.repaint();
            }
        });
        
        return boton;
    }
    
   
     /* Abre la gestión de mesas con interfaz completa */
    private void abrirGestionMesas() {
        JDialog dialog = new JDialog(frame, "Gestión de Mesas", true);
        dialog.setSize(700, 600);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(FONDO_PRINCIPAL);
        
        // Título
        JLabel titulo = new JLabel("Estado de Mesas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(CAFE_OSCURO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
        // Panel de mesas con grid
        JPanel panelMesas = new JPanel(new GridLayout(5, 2, 15, 15));
        panelMesas.setOpaque(false);
        
        for (Mesa mesa : mesas) {
            JPanel panelMesa = crearPanelMesa(mesa);
            panelMesas.add(panelMesa);
        }
        
        JScrollPane scrollPane = new JScrollPane(panelMesas);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones de acción
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelAcciones.setOpaque(false);
        
        JButton btnReservar = crearBotonAccion("Reservar", CAFE_MEDIO);
        JButton btnOcupar = crearBotonAccion("Ocupar", CAFE_CLARO);
        JButton btnLiberar = crearBotonAccion("Liberar", BEIGE_OSCURO);
        JButton btnCerrar = crearBotonAccion("Cerrar", new Color(139, 90, 60));
        
        btnReservar.addActionListener(e -> reservarMesa(dialog));
        btnOcupar.addActionListener(e -> ocuparMesa(dialog));
        btnLiberar.addActionListener(e -> liberarMesa(dialog));
        btnCerrar.addActionListener(e -> dialog.dispose());
        
        panelAcciones.add(btnReservar);
        panelAcciones.add(btnOcupar);
        panelAcciones.add(btnLiberar);
        panelAcciones.add(btnCerrar);
        
        panel.add(panelAcciones, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private JPanel crearPanelMesa(Mesa mesa) {
        JPanel panelMesa = new JPanel(new BorderLayout(5, 5));
        panelMesa.setBorder(new LineBorder(CAFE_OSCURO, 2, true));
        panelMesa.setBackground(FONDO_SECUNDARIO);
        panelMesa.setPreferredSize(new Dimension(200, 80));
        
        JLabel labelNumero = new JLabel("Mesa " + mesa.getNumero(), SwingConstants.CENTER);
        labelNumero.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelNumero.setForeground(CAFE_OSCURO);
        
        Color colorEstado = Color.GREEN;
        String textoEstado = "DISPONIBLE";
        if (mesa.getEstado() == Mesa.Estado.RESERVADA) {
            colorEstado = Color.ORANGE;
            textoEstado = "RESERVADA";
        } else if (mesa.getEstado() == Mesa.Estado.OCUPADA) {
            colorEstado = Color.RED;
            textoEstado = "OCUPADA";
        }
        
        JLabel labelEstado = new JLabel(textoEstado, SwingConstants.CENTER);
        labelEstado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelEstado.setForeground(colorEstado);
        
        panelMesa.add(labelNumero, BorderLayout.CENTER);
        panelMesa.add(labelEstado, BorderLayout.SOUTH);
        
        return panelMesa;
    }
    
    private JButton crearBotonAccion(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setPreferredSize(new Dimension(100, 35));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new LineBorder(CAFE_OSCURO, 1));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
    
    private void reservarMesa(JDialog parent) {
        String input = JOptionPane.showInputDialog(parent, 
            "Ingrese el número de mesa a reservar (1-10):", 
            "Reservar Mesa", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                int numMesa = Integer.parseInt(input.trim());
                if (numMesa >= 1 && numMesa <= mesas.length) {
                    Mesa mesa = mesas[numMesa - 1];
                    if (mesa.getEstado() == Mesa.Estado.DISPONIBLE) {
                        mesa.reservar();
                        JOptionPane.showMessageDialog(parent, 
                            "Mesa " + numMesa + " reservada correctamente.",
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE);
                        abrirGestionMesas(); // Refrescar
                    } else {
                        JOptionPane.showMessageDialog(parent, 
                            "La mesa " + numMesa + " no está disponible. Estado actual: " + mesa.getEstado(),
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(parent, 
                        "Número de mesa inválido. Debe estar entre 1 y " + mesas.length,
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, 
                    "Por favor ingrese un número válido.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void ocuparMesa(JDialog parent) {
        String input = JOptionPane.showInputDialog(parent, 
            "Ingrese el número de mesa a ocupar (1-10):", 
            "Ocupar Mesa", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                int numMesa = Integer.parseInt(input.trim());
                if (numMesa >= 1 && numMesa <= mesas.length) {
                    Mesa mesa = mesas[numMesa - 1];
                    if (mesa.getEstado() == Mesa.Estado.DISPONIBLE) {
                        mesa.ocupar();
                        JOptionPane.showMessageDialog(parent, 
                            "Mesa " + numMesa + " ocupada correctamente.",
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE);
                        abrirGestionMesas(); // Refrescar
                    } else {
                        JOptionPane.showMessageDialog(parent, 
                            "La mesa " + numMesa + " no está disponible. Estado actual: " + mesa.getEstado(),
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(parent, 
                        "Número de mesa inválido. Debe estar entre 1 y " + mesas.length,
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, 
                    "Por favor ingrese un número válido.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void liberarMesa(JDialog parent) {
        String input = JOptionPane.showInputDialog(parent, 
            "Ingrese el número de mesa a liberar (1-10):", 
            "Liberar Mesa", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                int numMesa = Integer.parseInt(input.trim());
                if (numMesa >= 1 && numMesa <= mesas.length) {
                    mesas[numMesa - 1].liberar();
                    JOptionPane.showMessageDialog(parent, 
                        "Mesa " + numMesa + " liberada correctamente.",
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    abrirGestionMesas(); // Refrescar
                } else {
                    JOptionPane.showMessageDialog(parent, 
                        "Número de mesa inválido. Debe estar entre 1 y " + mesas.length,
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, 
                    "Por favor ingrese un número válido.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /*evento para tomar pedido completo */
    private void abrirTomarPedido() {
        JDialog dialog = new JDialog(frame, "Tomar Pedido", true);
        dialog.setSize(800, 700);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(FONDO_PRINCIPAL);
        
        // Título
        JLabel titulo = new JLabel("Nuevo Pedido", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(CAFE_OSCURO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
        // Panel central con dos columnas
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 15, 0));
        panelCentral.setOpaque(false);
        
        // Panel izquierdo: Selección de mesa y cliente
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(CAFE_OSCURO, 2), "Información del Pedido"));
        
        JPanel panelInfo = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInfo.setOpaque(false);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel labelMesa = new JLabel("Mesa:");
        labelMesa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JComboBox<Integer> comboMesa = new JComboBox<>();
        for (int i = 1; i <= mesas.length; i++) {
            if (mesas[i-1].getEstado() != Mesa.Estado.OCUPADA) {
                comboMesa.addItem(i);
            }
        }
        if (comboMesa.getItemCount() == 0) {
            JOptionPane.showMessageDialog(dialog, 
                "No hay mesas disponibles. Por favor libere una mesa primero.",
                "Sin mesas disponibles", 
                JOptionPane.WARNING_MESSAGE);
            dialog.dispose();
            return;
        }
        
        JLabel labelCliente = new JLabel("Cliente:");
        labelCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField textCliente = new JTextField();
        textCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        panelInfo.add(labelMesa);
        panelInfo.add(comboMesa);
        panelInfo.add(labelCliente);
        panelInfo.add(textCliente);
        panelInfo.add(new JLabel()); // Espacio
        panelInfo.add(new JLabel());
        
        panelIzquierdo.add(panelInfo, BorderLayout.CENTER);
        
        // Panel derecho: Productos disponibles
        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setOpaque(false);
        panelDerecho.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(CAFE_OSCURO, 2), "Productos Disponibles"));
        
        DefaultListModel<String> modeloProductos = new DefaultListModel<>();
        for (Producto p : inventario.getProductos()) {
            String estado = p.getStock() > 0 ? ("Stock: " + p.getStock()) : "AGOTADO";
            modeloProductos.addElement(String.format("%s - $%d - %s", 
                p.getNombre(), (int)p.getPrecio(), estado));
        }
        
        JList<String> listaProductos = new JList<>(modeloProductos);
        listaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaProductos.setBackground(FONDO_SECUNDARIO);
        listaProductos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollProductos = new JScrollPane(listaProductos);
        scrollProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelDerecho.add(scrollProductos, BorderLayout.CENTER);
        
        // Panel de cantidad
        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCantidad.setOpaque(false);
        JLabel labelCantidad = new JLabel("Cantidad:");
        labelCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinnerCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCantidad.add(labelCantidad);
        panelCantidad.add(spinnerCantidad);
        
        panelDerecho.add(panelCantidad, BorderLayout.SOUTH);
        
        panelCentral.add(panelIzquierdo);
        panelCentral.add(panelDerecho);
        
        panel.add(panelCentral, BorderLayout.CENTER);
        
        // Panel de pedido actual
        JPanel panelPedido = new JPanel(new BorderLayout(10, 10));
        panelPedido.setOpaque(false);
        panelPedido.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(CAFE_OSCURO, 2), "Pedido Actual"));
        
        DefaultListModel<String> modeloPedido = new DefaultListModel<>();
        JList<String> listaPedido = new JList<>(modeloPedido);
        listaPedido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        listaPedido.setBackground(FONDO_SECUNDARIO);
        listaPedido.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPedido = new JScrollPane(listaPedido);
        scrollPedido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel labelTotal = new JLabel("Total: $0", SwingConstants.RIGHT);
        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelTotal.setForeground(CAFE_OSCURO);
        labelTotal.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        panelPedido.add(scrollPedido, BorderLayout.CENTER);
        panelPedido.add(labelTotal, BorderLayout.SOUTH);
        
        panel.add(panelPedido, BorderLayout.SOUTH);
        
        // Variables para el pedido
        final double[] totalPedido = {0.0};
        final Pedido[] pedidoActual = {null};
        
        // Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setOpaque(false);
        
        JButton btnAgregar = crearBotonAccion("Agregar Producto", CAFE_MEDIO);
        JButton btnEliminar = crearBotonAccion("Eliminar", CAFE_CLARO);
        JButton btnFinalizar = crearBotonAccion("Finalizar Pedido", BEIGE_OSCURO);
        JButton btnCancelar = crearBotonAccion("Cancelar", new Color(139, 90, 60));
        
        btnAgregar.addActionListener(e -> {
            int indice = listaProductos.getSelectedIndex();
            if (indice == -1) {
                JOptionPane.showMessageDialog(dialog, 
                    "Por favor seleccione un producto.",
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (textCliente.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Por favor ingrese el nombre del cliente.",
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Producto producto = inventario.getProductoPorIndice(indice + 1);
            if (producto == null || producto.getStock() == 0) {
                JOptionPane.showMessageDialog(dialog, 
                    "El producto seleccionado no está disponible.",
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int cantidad = (Integer) spinnerCantidad.getValue();
            
            if (pedidoActual[0] == null) {
                int numMesa = (Integer) comboMesa.getSelectedItem();
                Mesa mesa = mesas[numMesa - 1];
                if (mesa.getEstado() == Mesa.Estado.RESERVADA) {
                    int confirmar = JOptionPane.showConfirmDialog(dialog,
                        "La mesa está reservada. ¿Desea ocuparla?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                    if (confirmar != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                Cliente cliente = new Cliente(textCliente.getText().trim());
                pedidoActual[0] = new Pedido(cliente, empleado, mesa);
            }
            
            try {
                pedidoActual[0].agregarProducto(producto, cantidad);
                totalPedido[0] = pedidoActual[0].getTotal();
                modeloPedido.addElement(String.format("%s x%d - $%d", 
                    producto.getNombre(), cantidad, (int)(producto.getPrecio() * cantidad)));
                labelTotal.setText("Total: $" + (int)totalPedido[0]);
                
                // Actualizar lista de productos
                modeloProductos.clear();
                for (Producto p : inventario.getProductos()) {
                    String estado = p.getStock() > 0 ? ("Stock: " + p.getStock()) : "AGOTADO";
                    modeloProductos.addElement(String.format("%s - $%d - %s", 
                        p.getNombre(), (int)p.getPrecio(), estado));
                }
                
                JOptionPane.showMessageDialog(dialog, 
                    cantidad + " x " + producto.getNombre() + " agregado al pedido.",
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (StockInsuficienteException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Error: " + ex.getMessage(),
                    "Stock Insuficiente", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int indice = listaPedido.getSelectedIndex();
            if (indice == -1) {
                JOptionPane.showMessageDialog(dialog, 
                    "Por favor seleccione un item del pedido para eliminar.",
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Nota: En una implementación completa, se debería revertir el stock
            modeloPedido.remove(indice);
            // Recalcular total (simplificado)
            totalPedido[0] = pedidoActual[0] != null ? pedidoActual[0].getTotal() : 0;
            labelTotal.setText("Total: $" + (int)totalPedido[0]);
        });
        
        btnFinalizar.addActionListener(e -> {
            if (pedidoActual[0] == null || modeloPedido.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "El pedido está vacío. Agregue productos antes de finalizar.",
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                pedidoActual[0].validarPedido();
            } catch (PedidoVacioException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "El pedido está vacío.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Mostrar factura
            Factura.imprimir(pedidoActual[0]);
            double total = pedidoActual[0].getTotal();
            
            int confirmar = JOptionPane.showConfirmDialog(dialog,
                String.format("Total a pagar: $%d\n\n¿Confirmar pago?", (int)total),
                "Confirmar Pago",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirmar == JOptionPane.YES_OPTION) {
                try {
                    caja.registrarPago(total);
                    reporteDiario.registrarVenta(pedidoActual[0]);
                    reporteMensual.registrarVenta(pedidoActual[0]);
                    
                    try {
                        DataManager.guardarInventario(inventario);
                    } catch (Exception ex) {
                        Logger.error(ex, "Error al guardar inventario después de venta");
                    }
                    
                    try {
                        String rutaPDF = Factura.generarPDF(pedidoActual[0]);
                        JOptionPane.showMessageDialog(dialog, 
                            "Pago registrado correctamente.\nPDF de factura generado: " + rutaPDF,
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        Logger.error(ex, "Error al generar PDF de factura");
                        JOptionPane.showMessageDialog(dialog, 
                            "Pago registrado correctamente.\nNo se pudo generar el PDF de la factura.",
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    pedidoActual[0].cerrarPedido();
                    dialog.dispose();
                } catch (PagoInvalidoException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Error en el pago: " + ex.getMessage(),
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    if (pedidoActual[0] != null) {
                        pedidoActual[0].cerrarPedido();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(dialog, 
                    "Pago cancelado. El pedido ha sido cancelado.",
                    "Cancelado", 
                JOptionPane.INFORMATION_MESSAGE);
                if (pedidoActual[0] != null) {
                    pedidoActual[0].cerrarPedido();
                }
                dialog.dispose();
            }
        });
        
        btnCancelar.addActionListener(e -> {
            if (pedidoActual[0] != null) {
                pedidoActual[0].cerrarPedido();
            }
            dialog.dispose();
        });
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnFinalizar);
        panelBotones.add(btnCancelar);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    /*Muestra el inventario completo */
    private void mostrarInventario() {
        JDialog dialog = new JDialog(frame, "Inventario", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(FONDO_PRINCIPAL);
        
        JLabel titulo = new JLabel("Inventario de Productos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(CAFE_OSCURO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
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
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        textArea.setBackground(FONDO_SECUNDARIO);
        textArea.setForeground(CAFE_OSCURO);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(CAFE_OSCURO, 2));
        
        JButton btnCerrar = crearBotonAccion("Cerrar", new Color(139, 90, 60));
        btnCerrar.addActionListener(e -> dialog.dispose());
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.add(btnCerrar);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    
     /* Muestra el total en caja*/
    private void mostrarTotalCaja() {
        double total = caja.getTotalDia();
        JOptionPane.showMessageDialog(frame,
                String.format("<html><body style='text-align: center;'><h2 style='color: %s;'>Total en Caja</h2><p style='font-size: 18px;'>$%d</p></body></html>", 
                    String.format("#%02x%02x%02x", CAFE_OSCURO.getRed(), CAFE_OSCURO.getGreen(), CAFE_OSCURO.getBlue()),
                    (int)total),
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
    
    
     /* Muestra un reporte completo */
    private void mostrarReporte(Reporte reporte, String titulo) {
        JDialog dialog = new JDialog(frame, titulo, true);
        dialog.setSize(700, 600);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(FONDO_PRINCIPAL);
        
        JLabel labelTitulo = new JLabel(titulo, SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelTitulo.setForeground(CAFE_OSCURO);
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(labelTitulo, BorderLayout.NORTH);
        
        StringBuilder sb = new StringBuilder();
        sb.append("===== ").append(titulo).append(" =====\n\n");
        
        // Obtener datos del reporte usando reflexión o métodos públicos
        // Por ahora, construimos el texto manualmente
        sb.append("Total pedidos: ").append(obtenerTotalPedidos(reporte)).append("\n");
        sb.append("Total ventas: $").append((int)obtenerTotalVentas(reporte)).append("\n");
        sb.append("-------------------------------\n");
        sb.append(String.format("%-20s %8s %10s\n", "Producto", "Cant", "Total $"));
        
        Map<String, Integer> unidades = obtenerUnidadesPorProducto(reporte);
        Map<String, Double> totales = obtenerTotalPorProducto(reporte);
        
        for (String nombre : unidades.keySet()) {
            sb.append(String.format("%-20s %8d %10.0f\n",
                    nombre,
                    unidades.get(nombre),
                    totales.getOrDefault(nombre, 0.0)));
        }
        
        if (!unidades.isEmpty()) {
            String masVendido = null, menosVendido = null;
            for (String n : unidades.keySet()) {
                if (masVendido == null || unidades.get(n) > unidades.get(masVendido)) masVendido = n;
                if (menosVendido == null || unidades.get(n) < unidades.get(menosVendido)) menosVendido = n;
            }
            
            sb.append("-------------------------------\n");
            sb.append("Producto más vendido: ").append(masVendido)
              .append(" (").append(unidades.get(masVendido)).append(" uds)\n");
            sb.append("Producto menos vendido: ").append(menosVendido)
              .append(" (").append(unidades.get(menosVendido)).append(" uds)\n");
        } else {
            sb.append("\nNo hay ventas registradas aún.\n");
        }
        
        sb.append("===============================\n");
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setBackground(FONDO_SECUNDARIO);
        textArea.setForeground(CAFE_OSCURO);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(CAFE_OSCURO, 2));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setOpaque(false);
        
        JButton btnGenerarPDF = crearBotonAccion("Generar PDF", CAFE_MEDIO);
        JButton btnCerrar = crearBotonAccion("Cerrar", new Color(139, 90, 60));
        
        btnGenerarPDF.addActionListener(e -> {
            try {
                String rutaPDF = reporte.generarPDF(titulo);
                JOptionPane.showMessageDialog(dialog, 
                    "PDF del reporte generado:\n" + rutaPDF,
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.error(ex, "Error al generar PDF del reporte");
                JOptionPane.showMessageDialog(dialog, 
                    "No se pudo generar el PDF del reporte. Verifique el log.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCerrar.addActionListener(e -> dialog.dispose());
        
        panelBotones.add(btnGenerarPDF);
        panelBotones.add(btnCerrar);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    // Métodos auxiliares para acceder a los datos del reporte usando reflexión
    private int obtenerTotalPedidos(Reporte reporte) {
        try {
            java.lang.reflect.Field field = Reporte.class.getDeclaredField("totalPedidos");
            field.setAccessible(true);
            return (Integer) field.get(reporte);
        } catch (Exception e) {
            return 0;
        }
    }
    
    private double obtenerTotalVentas(Reporte reporte) {
        try {
            java.lang.reflect.Field field = Reporte.class.getDeclaredField("totalVentas");
            field.setAccessible(true);
            return (Double) field.get(reporte);
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Integer> obtenerUnidadesPorProducto(Reporte reporte) {
        try {
            java.lang.reflect.Field field = Reporte.class.getDeclaredField("unidadesPorProducto");
            field.setAccessible(true);
            return (Map<String, Integer>) field.get(reporte);
        } catch (Exception e) {
            return new java.util.HashMap<>();
        }
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Double> obtenerTotalPorProducto(Reporte reporte) {
        try {
            java.lang.reflect.Field field = Reporte.class.getDeclaredField("totalPorProducto");
            field.setAccessible(true);
            return (Map<String, Double>) field.get(reporte);
        } catch (Exception e) {
            return new java.util.HashMap<>();
        }
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
