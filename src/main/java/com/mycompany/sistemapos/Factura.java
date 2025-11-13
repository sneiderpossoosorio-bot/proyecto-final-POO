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
                
                // Encabezado
                contentStream.beginText();
                contentStream.setFont(fontTitulo, 24);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("FACTURA");
                contentStream.endText();
                
                yPosition -= 40;
                
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
                
                // Pie de página
                contentStream.beginText();
                contentStream.setFont(fontNormal, 8);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Gracias por su compra!");
                contentStream.endText();
            }
            
            // Guardar documento
            document.save(rutaCompleta);
        }
        
        System.out.println("Factura PDF generada: " + rutaCompleta);
        return rutaCompleta;
    }
}
