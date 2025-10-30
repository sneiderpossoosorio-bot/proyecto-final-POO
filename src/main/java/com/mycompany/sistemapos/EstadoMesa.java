/*
 * Enum EstadoMesa
 *
 * Justificación del refactor:
 * - Reemplaza cadenas "DISPONIBLE", "OCUPADA", "RESERVADA" por un tipo seguro.
 * - Evita errores tipográficos y concentra estados válidos.
 */
package com.mycompany.sistemapos;

public enum EstadoMesa {
    DISPONIBLE,
    OCUPADA,
    RESERVADA
}
