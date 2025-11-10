package com.mycompany.sistemapos.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mycompany.sistemapos.Inventario;
import com.mycompany.sistemapos.Producto;
import com.mycompany.sistemapos.utilidades.Logger;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de persistencia de datos usando archivos JSON
 * Maneja la carga y guardado de datos del sistema
 */
public class DataManager {
    private static final String DIRECTORIO_DATOS = "data/";
    private static final String ARCHIVO_PRODUCTOS = DIRECTORIO_DATOS + "productos.json";
    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    
    /**
     * Inicializa el directorio de datos si no existe
     */
    public static void inicializarDirectorio() {
        java.io.File directorio = new java.io.File(DIRECTORIO_DATOS);
        if (!directorio.exists()) {
            directorio.mkdirs();
            Logger.info("Directorio de datos creado: " + DIRECTORIO_DATOS);
        }
    }
    
    /**
     * Guarda la lista de productos en un archivo JSON
     */
    public static void guardarProductos(List<Producto> productos) {
        inicializarDirectorio();
        try (FileWriter writer = new FileWriter(ARCHIVO_PRODUCTOS)) {
            gson.toJson(productos, writer);
            Logger.info("Productos guardados: " + productos.size() + " productos");
        } catch (IOException e) {
            Logger.error(e, "Error al guardar productos");
            throw new RuntimeException("No se pudo guardar los productos", e);
        }
    }
    
    /**
     * Carga la lista de productos desde un archivo JSON
     */
    public static List<Producto> cargarProductos() {
        inicializarDirectorio();
        java.io.File archivo = new java.io.File(ARCHIVO_PRODUCTOS);
        
        if (!archivo.exists()) {
            Logger.info("Archivo de productos no existe. Se creará uno nuevo.");
            return new ArrayList<>();
        }
        
        try (FileReader reader = new FileReader(ARCHIVO_PRODUCTOS)) {
            Type tipoLista = new TypeToken<List<Producto>>(){}.getType();
            List<Producto> productos = gson.fromJson(reader, tipoLista);
            
            if (productos == null) {
                productos = new ArrayList<>();
            }
            
            Logger.info("Productos cargados: " + productos.size() + " productos");
            return productos;
        } catch (IOException e) {
            Logger.error(e, "Error al cargar productos");
            Logger.warning("Se usará una lista vacía de productos");
            return new ArrayList<>();
        }
    }
    
    /**
     * Guarda el inventario completo
     */
    public static void guardarInventario(Inventario inventario) {
        guardarProductos(inventario.getProductos());
    }
    
    /**
     * Carga el inventario desde archivo
     */
    public static Inventario cargarInventario() {
        Inventario inventario = new Inventario();
        List<Producto> productos = cargarProductos();
        
        for (Producto producto : productos) {
            inventario.agregarProducto(producto);
        }
        
        return inventario;
    }
    
    /**
     * Verifica si existe un archivo de datos
     */
    public static boolean existeArchivoDatos() {
        return new java.io.File(ARCHIVO_PRODUCTOS).exists();
    }
}

