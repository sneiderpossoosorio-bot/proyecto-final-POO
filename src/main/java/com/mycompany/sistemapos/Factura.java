/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import com.mycompany.sistemapos.utilidades.IconoCafeteria;


public class Factura {
    
     /* RELACIÓN: ASOCIACIÓN - La factura usa el pedido pero no lo almacena ni modifica.*/

    public static void imprimir(Pedido pedido) {
        System.out.println("\n===== FACTURA =====");
        // Extrae información del pedido (relación de asociación)
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Mesa: " + pedido.getMesa().getNumero());
        System.out.println("Atendido por: " + pedido.getEmpleado().getNombre());
        // Muestra el detalle completo del pedido
        pedido.mostrarDetalle();
        System.out.println("===================\n");
    }
    
    /**
     * Genera un PDF de la factura del pedido
     * @param pedido El pedido del cual se genera la factura
     * @return La ruta del archivo PDF generado
     * @throws IOException Si hay un error al crear el archivo PDF
     */
    public static String generarPDF(Pedido pedido) throws IOException {
        // Crear directorio de facturas si no existe
        Path facturasDir = Paths.get("facturas");
        if (!Files.exists(facturasDir)) {
            Files.createDirectories(facturasDir);
        }
        
        // Generar nombre de archivo con timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nombreArchivo = String.format("Factura_%s_%s.pdf", 
            pedido.getCliente().getNombre().replaceAll("\\s+", "_"),
            timestamp);
        String rutaCompleta = facturasDir.resolve(nombreArchivo).toString();
        
        // Crear documento PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float yPosition = 750;
                float margin = 50;
                float lineHeight = 20;
                
                // Fuentes
                PDType1Font fontTitulo = PDType1Font.HELVETICA_BOLD;
                PDType1Font fontNormal = PDType1Font.HELVETICA;
                PDType1Font fontNegrita = PDType1Font.HELVETICA_BOLD;
                
                /* ============================================
                 * ENCABEZADO CON ICONO DE LA CAFETERÍA
                 * Dibuja el logo de la cafetería junto al título
                 * ============================================ */
                
                // Dibujar icono de la cafetería (taza de café) en el encabezado
                float iconoX = margin;
                float iconoY = yPosition - 5;
                float iconoTamaño = 35;
                dibujarIconoEnPDF(contentStream, iconoX, iconoY, iconoTamaño);
                
                // Título de la factura al lado del icono
                contentStream.beginText();
                contentStream.setFont(fontTitulo, 28);
                contentStream.newLineAtOffset(margin + iconoTamaño + 15, yPosition);
                contentStream.showText("FACTURA");
                contentStream.endText();
                
                // Subtítulo con nombre de la cafetería
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
                contentStream.newLineAtOffset(margin + iconoTamaño + 15, yPosition - 18);
                contentStream.showText("Cafetería - Sistema POS");
                contentStream.endText();
                
                yPosition -= 50;
                
                // Línea separadora
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(550, yPosition);
                contentStream.stroke();
                
                yPosition -= 30;
                
                // Información de la factura
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String fechaHora = LocalDateTime.now().format(formatter);
                
                contentStream.beginText();
                contentStream.setFont(fontNormal, 10);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Fecha: " + fechaHora);
                contentStream.endText();
                
                yPosition -= lineHeight;
                
                // Información del cliente
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 12);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Cliente: " + pedido.getCliente().getNombre());
                contentStream.endText();
                
                yPosition -= lineHeight;
                
                contentStream.beginText();
                contentStream.setFont(fontNormal, 10);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Mesa: " + pedido.getMesa().getNumero());
                contentStream.endText();
                
                yPosition -= lineHeight;
                
                contentStream.beginText();
                contentStream.setFont(fontNormal, 10);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Atendido por: " + pedido.getEmpleado().getNombre() + 
                    " (ID: " + pedido.getEmpleado().getIdEmpleado() + ")");
                contentStream.endText();
                
                yPosition -= 30;
                
                // Línea separadora
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(550, yPosition);
                contentStream.stroke();
                
                yPosition -= 25;
                
                // Encabezado de tabla
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 10);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Producto");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 10);
                contentStream.newLineAtOffset(250, yPosition);
                contentStream.showText("Cantidad");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 10);
                contentStream.newLineAtOffset(350, yPosition);
                contentStream.showText("Precio Unit.");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 10);
                contentStream.newLineAtOffset(480, yPosition);
                contentStream.showText("Subtotal");
                contentStream.endText();
                
                yPosition -= 20;
                
                // Línea separadora
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(550, yPosition);
                contentStream.stroke();
                
                yPosition -= 20;
                
                // Detalle de productos
                for (Map.Entry<Producto, Integer> entry : pedido.getItems().entrySet()) {
                    Producto producto = entry.getKey();
                    int cantidad = entry.getValue();
                    double precioUnitario = producto.getPrecio();
                    double subtotal = precioUnitario * cantidad;
                    
                    // Si no hay espacio, continuamos en la misma página
                    // (En una versión más avanzada se podría agregar paginación)
                    if (yPosition < 100) {
                        yPosition = 750; // Reiniciar posición (se truncará el contenido)
                    }
                    
                    // Nombre del producto (puede necesitar truncarse si es muy largo)
                    String nombreProducto = producto.getNombre();
                    if (nombreProducto.length() > 30) {
                        nombreProducto = nombreProducto.substring(0, 27) + "...";
                    }
                    
                    contentStream.beginText();
                    contentStream.setFont(fontNormal, 10);
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText(nombreProducto);
                    contentStream.endText();
                    
                    contentStream.beginText();
                    contentStream.setFont(fontNormal, 10);
                    contentStream.newLineAtOffset(280, yPosition);
                    contentStream.showText(String.valueOf(cantidad));
                    contentStream.endText();
                    
                    contentStream.beginText();
                    contentStream.setFont(fontNormal, 10);
                    contentStream.newLineAtOffset(370, yPosition);
                    contentStream.showText(String.format("$%.2f", precioUnitario));
                    contentStream.endText();
                    
                    contentStream.beginText();
                    contentStream.setFont(fontNormal, 10);
                    contentStream.newLineAtOffset(480, yPosition);
                    contentStream.showText(String.format("$%.2f", subtotal));
                    contentStream.endText();
                    
                    yPosition -= lineHeight;
                }
                
                yPosition -= 10;
                
                // Línea separadora
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(550, yPosition);
                contentStream.stroke();
                
                yPosition -= 25;
                
                // Total
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 14);
                contentStream.newLineAtOffset(350, yPosition);
                contentStream.showText("TOTAL: $" + String.format("%.2f", pedido.getTotal()));
                contentStream.endText();
                
                yPosition -= 40;
                
                // Pie de página con icono pequeño
                float iconoPieX = margin;
                float iconoPieY = yPosition - 5;
                float iconoPieTamaño = 12;
                dibujarIconoEnPDF(contentStream, iconoPieX, iconoPieY, iconoPieTamaño);
                
                contentStream.beginText();
                contentStream.setFont(fontNormal, 9);
                contentStream.newLineAtOffset(margin + iconoPieTamaño + 8, yPosition);
                contentStream.showText("Gracias por su compra! - Cafetería POS");
                contentStream.endText();
            }
            
            // Guardar documento
            document.save(rutaCompleta);
        }
        
        System.out.println("Factura PDF generada: " + rutaCompleta);
        return rutaCompleta;
    }
    
    /**
     * Dibuja el icono de la cafetería (taza de café) en el PDF
     * Usa primitivas gráficas de PDFBox para crear el icono
     * 
     * @param contentStream Stream de contenido del PDF donde se dibuja
     * @param x Posición X donde se dibuja el icono
     * @param y Posición Y donde se dibuja el icono
     * @param tamaño Tamaño del icono (ancho y alto)
     */
    private static void dibujarIconoEnPDF(PDPageContentStream contentStream, 
                                          float x, float y, float tamaño) throws IOException {
        // Guardar el estado actual del stream
        contentStream.saveGraphicsState();
        
        // Colores del icono (tonos café profesionales)
        float cafeR = 101.0f / 255.0f;
        float cafeG = 67.0f / 255.0f;
        float cafeB = 33.0f / 255.0f;
        
        float cremaR = 255.0f / 255.0f;
        float cremaG = 248.0f / 255.0f;
        float cremaB = 220.0f / 255.0f;
        
        // Calcular dimensiones proporcionales
        float anchoTaza = tamaño * 0.5f;
        float altoTaza = tamaño * 0.4f;
        float xTaza = x;
        float yTaza = y - altoTaza;
        
        // Dibujar el cuerpo de la taza (rectángulo redondeado)
        contentStream.setNonStrokingColor(cafeR, cafeG, cafeB);
        contentStream.addRect(xTaza, yTaza, anchoTaza, altoTaza);
        contentStream.fill();
        
        // Dibujar el asa de la taza (arco)
        contentStream.setStrokingColor(cafeR, cafeG, cafeB);
        contentStream.setLineWidth(2.0f);
        float radioAsa = anchoTaza * 0.3f;
        float xAsa = xTaza + anchoTaza - 2;
        float yAsa = yTaza + altoTaza * 0.25f;
        // Dibujar arco para el asa
        contentStream.moveTo(xAsa, yAsa);
        contentStream.curveTo(xAsa, yAsa, xAsa + radioAsa/2, yAsa + radioAsa/4, xAsa + radioAsa, yAsa);
        contentStream.stroke();
        
        // Dibujar el café dentro de la taza (línea horizontal)
        float cafeClaroR = (cafeR + 0.08f > 1.0f) ? 1.0f : cafeR + 0.08f;
        float cafeClaroG = (cafeG + 0.05f > 1.0f) ? 1.0f : cafeG + 0.05f;
        float cafeClaroB = (cafeB + 0.02f > 1.0f) ? 1.0f : cafeB + 0.02f;
        contentStream.setStrokingColor(cafeClaroR, cafeClaroG, cafeClaroB);
        contentStream.setLineWidth(1.5f);
        contentStream.moveTo(xTaza + 2, yTaza + altoTaza * 0.33f);
        contentStream.lineTo(xTaza + anchoTaza - 2, yTaza + altoTaza * 0.33f);
        contentStream.stroke();
        
        // Dibujar vapor (líneas curvas arriba de la taza)
        contentStream.setStrokingColor(cremaR, cremaG, cremaB);
        contentStream.setLineWidth(1.5f);
        
        float yVapor = yTaza - 3;
        float xVapor1 = xTaza + anchoTaza * 0.25f;
        float xVapor2 = xTaza + anchoTaza * 0.5f;
        float xVapor3 = xTaza + anchoTaza * 0.75f;
        float altoVapor = tamaño * 0.15f;
        
        // Vapor izquierdo
        contentStream.moveTo(xVapor1, yVapor);
        contentStream.curveTo(xVapor1 - 2, yVapor - altoVapor/2, xVapor1 + 2, yVapor - altoVapor, xVapor1, yVapor - altoVapor);
        contentStream.stroke();
        
        // Vapor central
        contentStream.moveTo(xVapor2, yVapor);
        contentStream.curveTo(xVapor2 - 2, yVapor - altoVapor/2, xVapor2 + 2, yVapor - altoVapor, xVapor2, yVapor - altoVapor);
        contentStream.stroke();
        
        // Vapor derecho
        contentStream.moveTo(xVapor3, yVapor);
        contentStream.curveTo(xVapor3 - 2, yVapor - altoVapor/2, xVapor3 + 2, yVapor - altoVapor, xVapor3, yVapor - altoVapor);
        contentStream.stroke();
        
        // Restaurar el estado del stream
        contentStream.restoreGraphicsState();
    }
}
