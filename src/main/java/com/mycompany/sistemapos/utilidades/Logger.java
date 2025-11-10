package com.mycompany.sistemapos.utilidades;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Sistema de logging simple para registrar eventos, errores y advertencias
 */
public class Logger {
    private static final String LOG_FILE = "sistema_pos.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public enum Nivel {
        INFO,    // Información general
        WARNING, // Advertencias
        ERROR,   // Errores
        SEVERE   // Errores críticos
    }
    
    /**
     * Registra un mensaje en el log
     */
    public static void log(Nivel nivel, String mensaje) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = String.format("[%s] [%s] %s", timestamp, nivel, mensaje);
        
        // Mostrar en consola
        System.out.println(logEntry);
        
        // Guardar en archivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }
    
    /**
     * Registra información general
     */
    public static void info(String mensaje) {
        log(Nivel.INFO, mensaje);
    }
    
    /**
     * Registra una advertencia
     */
    public static void warning(String mensaje) {
        log(Nivel.WARNING, mensaje);
    }
    
    /**
     * Registra un error
     */
    public static void error(String mensaje) {
        log(Nivel.ERROR, mensaje);
    }
    
    /**
     * Registra un error crítico
     */
    public static void severe(String mensaje) {
        log(Nivel.SEVERE, mensaje);
    }
    
    /**
     * Registra una excepción
     */
    public static void error(Exception e, String contexto) {
        error(String.format("%s: %s - %s", contexto, e.getClass().getSimpleName(), e.getMessage()));
        if (e.getCause() != null) {
            error("Causa: " + e.getCause().getMessage());
        }
    }
}




