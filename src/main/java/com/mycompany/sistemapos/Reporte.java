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
import java.util.HashMap;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class Reporte {
  
    //  acumula las unidades vendidas por producto
    private Map<String, Integer> unidadesPorProducto = new HashMap<>();
    
    // Mapa que acumula el total en dinero por producto
    private Map<String, Double> totalPorProducto = new HashMap<>();
    
    /*Contador total de pedidos registrados*/
    private int totalPedidos = 0;
    
     /* Total acumulado de todas las ventas en dinero*/
 private double totalVentas = 0.0;


    public void registrarVenta(Pedido pedido) {
        // Itera sobre todos los productos del pedido
        for (Map.Entry<Producto, Integer> e : pedido.getItems().entrySet()) {
            String nombre = e.getKey().getNombre();
            int cant = e.getValue();
            double subtotal = e.getKey().getPrecio() * cant;

            // Acumula unidades vendidas por producto
            unidadesPorProducto.put(nombre, unidadesPorProducto.getOrDefault(nombre, 0) + cant);
            // Acumula totales en dinero por producto
            totalPorProducto.put(nombre, totalPorProducto.getOrDefault(nombre, 0.0) + subtotal);
        }
        // Incrementa contadores globales
        totalPedidos++;
        totalVentas += pedido.getTotal();
    }

    //muestra el repote completo
    public void mostrarReporte(String titulo) {
        System.out.println("\n===== " + titulo + " =====");
        System.out.println("Total pedidos: " + totalPedidos);
        System.out.println("Total ventas: $" + (int)totalVentas);
        System.out.println("-------------------------------");
        System.out.printf("%-20s %8s %10s\n", "Producto", "Cant", "Total $");
        
        // Muestra el detalle por producto
        for (String nombre : unidadesPorProducto.keySet()) {
            System.out.printf("%-20s %8d %10.0f\n",
                    nombre,
                    unidadesPorProducto.get(nombre),
                    totalPorProducto.getOrDefault(nombre, 0.0));
        }
        
        // Calcula producto más y menos vendido
        String mas = null, menos = null;
        for (String n : unidadesPorProducto.keySet()) {
            if (mas == null || unidadesPorProducto.get(n) > unidadesPorProducto.get(mas)) mas = n;
            if (menos == null || unidadesPorProducto.get(n) < unidadesPorProducto.get(menos)) menos = n;
        }
        
        if (mas != null) {
            System.out.println("-------------------------------");
            System.out.println("Producto mas vendido: " + mas + " (" + unidadesPorProducto.get(mas) + " uds)");
            System.out.println("Producto menos vendido: " + menos + " (" + unidadesPorProducto.get(menos) + " uds)");
        } else {
            System.out.println("No hay ventas registradas aun.");
        }
        System.out.println("===============================\n");
    }

   //Reinicia todos los acumuladores del reporte.
     
    public void reiniciar() {
        unidadesPorProducto.clear();
        totalPorProducto.clear();
        totalPedidos = 0;
        totalVentas = 0.0;
    }
    
    /**
     * Genera un PDF del reporte
     * @param titulo El título del reporte
     * @return La ruta del archivo PDF generado
     * @throws IOException Si hay un error al crear el archivo PDF
     */
    public String generarPDF(String titulo) throws IOException {
        // Crear directorio de reportes si no existe
        Path reportesDir = Paths.get("reportes");
        if (!Files.exists(reportesDir)) {
            Files.createDirectories(reportesDir);
        }
        
        // Generar nombre de archivo con timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nombreArchivo = String.format("Reporte_%s_%s.pdf", 
            titulo.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", ""),
            timestamp);
        String rutaCompleta = reportesDir.resolve(nombreArchivo).toString();
        
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
                contentStream.showText(titulo.toUpperCase());
                contentStream.endText();
                
                yPosition -= 40;
                
                // Línea separadora
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(550, yPosition);
                contentStream.stroke();
                
                yPosition -= 30;
                
                // Información de la fecha
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String fechaHora = LocalDateTime.now().format(formatter);
                
                contentStream.beginText();
                contentStream.setFont(fontNormal, 10);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Fecha del reporte: " + fechaHora);
                contentStream.endText();
                
                yPosition -= 30;
                
                // Estadísticas generales
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 12);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Total de pedidos: " + totalPedidos);
                contentStream.endText();
                
                yPosition -= lineHeight;
                
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 12);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Total de ventas: $" + String.format("%.2f", totalVentas));
                contentStream.endText();
                
                yPosition -= 40;
                
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
                contentStream.newLineAtOffset(300, yPosition);
                contentStream.showText("Cantidad");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(fontNegrita, 10);
                contentStream.newLineAtOffset(420, yPosition);
                contentStream.showText("Total $");
                contentStream.endText();
                
                yPosition -= 20;
                
                // Línea separadora
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(550, yPosition);
                contentStream.stroke();
                
                yPosition -= 20;
                
                // Detalle por producto
                if (unidadesPorProducto.isEmpty()) {
                    contentStream.beginText();
                    contentStream.setFont(fontNormal, 10);
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("No hay ventas registradas aún.");
                    contentStream.endText();
                    yPosition -= lineHeight;
                } else {
                    for (String nombre : unidadesPorProducto.keySet()) {
                        // Si no hay espacio, continuamos en la misma página
                        if (yPosition < 100) {
                            yPosition = 750; // Reiniciar posición
                        }
                        
                        int cantidad = unidadesPorProducto.get(nombre);
                        double total = totalPorProducto.getOrDefault(nombre, 0.0);
                        
                        // Truncar nombre si es muy largo
                        String nombreProducto = nombre;
                        if (nombreProducto.length() > 35) {
                            nombreProducto = nombreProducto.substring(0, 32) + "...";
                        }
                        
                        contentStream.beginText();
                        contentStream.setFont(fontNormal, 10);
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText(nombreProducto);
                        contentStream.endText();
                        
                        contentStream.beginText();
                        contentStream.setFont(fontNormal, 10);
                        contentStream.newLineAtOffset(320, yPosition);
                        contentStream.showText(String.valueOf(cantidad));
                        contentStream.endText();
                        
                        contentStream.beginText();
                        contentStream.setFont(fontNormal, 10);
                        contentStream.newLineAtOffset(430, yPosition);
                        contentStream.showText(String.format("$%.2f", total));
                        contentStream.endText();
                        
                        yPosition -= lineHeight;
                    }
                }
                
                yPosition -= 30;
                
                // Línea separadora
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(550, yPosition);
                contentStream.stroke();
                
                yPosition -= 30;
                
                // Producto más y menos vendido
                if (!unidadesPorProducto.isEmpty()) {
                    String masVendido = null, menosVendido = null;
                    for (String n : unidadesPorProducto.keySet()) {
                        if (masVendido == null || unidadesPorProducto.get(n) > unidadesPorProducto.get(masVendido)) {
                            masVendido = n;
                        }
                        if (menosVendido == null || unidadesPorProducto.get(n) < unidadesPorProducto.get(menosVendido)) {
                            menosVendido = n;
                        }
                    }
                    
                    if (masVendido != null) {
                        // Truncar nombres si son muy largos
                        String masNombre = masVendido.length() > 30 ? masVendido.substring(0, 27) + "..." : masVendido;
                        String menosNombre = menosVendido.length() > 30 ? menosVendido.substring(0, 27) + "..." : menosVendido;
                        
                        contentStream.beginText();
                        contentStream.setFont(fontNegrita, 11);
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText("Producto más vendido: " + masNombre + 
                            " (" + unidadesPorProducto.get(masVendido) + " unidades)");
                        contentStream.endText();
                        
                        yPosition -= lineHeight;
                        
                        contentStream.beginText();
                        contentStream.setFont(fontNegrita, 11);
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText("Producto menos vendido: " + menosNombre + 
                            " (" + unidadesPorProducto.get(menosVendido) + " unidades)");
                        contentStream.endText();
                    }
                }
                
                yPosition -= 40;
                
                // Pie de página
                contentStream.beginText();
                contentStream.setFont(fontNormal, 8);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Reporte generado automáticamente por Sistema POS");
                contentStream.endText();
            }
            
            // Guardar documento
            document.save(rutaCompleta);
        }
        
        System.out.println("Reporte PDF generado: " + rutaCompleta);
        return rutaCompleta;
    }
}