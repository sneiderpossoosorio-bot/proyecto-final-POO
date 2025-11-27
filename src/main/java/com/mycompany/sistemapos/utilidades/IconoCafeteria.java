
package com.mycompany.sistemapos.utilidades;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class IconoCafeteria {
    
    // Colores del icono (tonos café profesionales)
    private static final Color CAFE_ICONO = new Color(101, 67, 33);      // Café oscuro para la taza
    private static final Color CREMA_ICONO = new Color(255, 248, 220);    // Crema para el vapor
    private static final Color FONDO_ICONO = new Color(245, 222, 179);   // Beige claro para el fondo
    
    /**
     * Crea un ImageIcon del logo de la cafetería
     * tamaño Tamaño del icono (ancho y alto)
     *  ImageIcon con el logo dibujado
     */
    public static ImageIcon crearIcono(int tamaño) {
        BufferedImage imagen = new BufferedImage(tamaño, tamaño, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagen.createGraphics();
        
        // Configurar renderizado suave
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Dibujar el icono
        dibujarIcono(g2d, 0, 0, tamaño, tamaño);
        
        g2d.dispose();
        return new ImageIcon(imagen);
    }
    
    /**
     * Dibuja el icono de la cafetería (taza de café) en un Graphics2D
     * @param g2d Graphics2D donde se dibujará el icono
     */
    public static void dibujarIcono(Graphics2D g2d, int x, int y, int ancho, int alto) {
        // Guardar configuración original
        Color colorOriginal = g2d.getColor();
        Stroke strokeOriginal = g2d.getStroke();
        
        // Calcular dimensiones proporcionales
        int centroX = x + ancho / 2;
        int centroY = y + alto / 2;
        
        // Dibujar fondo circular (opcional, para que se vea mejor)
        g2d.setColor(FONDO_ICONO);
        g2d.fillOval(x + ancho/4, y + alto/4, ancho/2, alto/2);
        
        // Dibujar la taza de café (cuerpo principal)
        g2d.setColor(CAFE_ICONO);
        g2d.setStroke(new BasicStroke(3.0f));
        
        // Cuerpo de la taza (rectángulo redondeado)
        int anchoTaza = (int)(ancho * 0.5);
        int altoTaza = (int)(alto * 0.4);
        int xTaza = centroX - anchoTaza / 2;
        int yTaza = centroY - altoTaza / 2;
        
        // Dibujar el cuerpo de la taza
        g2d.fillRoundRect(xTaza, yTaza, anchoTaza, altoTaza, 10, 10);
        
        // Dibujar el asa de la taza (arco)
        int radioAsa = (int)(anchoTaza * 0.3);
        g2d.setStroke(new BasicStroke(4.0f));
        g2d.drawArc(xTaza + anchoTaza - 5, yTaza + altoTaza / 4, radioAsa, radioAsa, 0, 180);
        
        // Dibujar el café dentro de la taza (línea horizontal)
        g2d.setColor(new Color(CAFE_ICONO.getRed() + 20, CAFE_ICONO.getGreen() + 10, CAFE_ICONO.getBlue() + 5));
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawLine(xTaza + 5, yTaza + altoTaza / 3, xTaza + anchoTaza - 5, yTaza + altoTaza / 3);
        
        // Dibujar vapor (líneas curvas arriba de la taza)
        g2d.setColor(CREMA_ICONO);
        g2d.setStroke(new BasicStroke(2.5f));
        
        int yVapor = yTaza - 8;
        int xVapor1 = centroX - anchoTaza / 4;
        int xVapor2 = centroX;
        int xVapor3 = centroX + anchoTaza / 4;
        
        // Vapor izquierdo
        dibujarVapor(g2d, xVapor1, yVapor, 6, 12);
        // Vapor central
        dibujarVapor(g2d, xVapor2, yVapor, 6, 15);
        // Vapor derecho
        dibujarVapor(g2d, xVapor3, yVapor, 6, 12);
        
        // Restaurar configuración original
        g2d.setColor(colorOriginal);
        g2d.setStroke(strokeOriginal);
    }
    
    /**
     * Dibuja una línea de vapor (curva suave)
     *g2d Graphics2D donde se dibuja
     *  x Posición X inicial
     *  y Posición Y inicial
     */
    private static void dibujarVapor(Graphics2D g2d, int x, int y, int ancho, int alto) {
        // Dibujar una curva suave usando un arco
        g2d.drawArc(x - ancho/2, y, ancho, alto, 0, 180);
    }
    
    /**
     * Obtiene el color principal del icono
     * @return Color café oscuro del icono
     */
    public static Color getColorIcono() {
        return CAFE_ICONO;
    }
}

