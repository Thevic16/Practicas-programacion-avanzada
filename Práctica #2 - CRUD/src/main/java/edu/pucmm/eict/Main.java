package edu.pucmm.eict;

import edu.pucmm.eict.util.*;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Administracion administracion = new Administracion(); /*
        -Lista de usuarios visible por todo el sistema (Apartado 2)
        -Lista de productos visible por todo el sistema (Apartado 3)
        */


        //Creando la instancia del servidor.
        Javalin app = Javalin.create().start(7000);
        app.config.addStaticFiles("/publico");


        /*Prueba agregando productos */
        ProductoMostrador producto1 = new ProductoMostrador("Memoria",new BigDecimal("500.25"));
        ProductoMostrador producto2 = new ProductoMostrador("Monitor",new BigDecimal("1000.25"));
        ProductoMostrador producto3 = new ProductoMostrador("Mouse",new BigDecimal("200.25"));

        administracion.agregarProducto(producto1);
        administracion.agregarProducto(producto2);
        administracion.agregarProducto(producto3);

        //administracion.


        new Ruta(app,administracion).ejecutarRutas();
    }


}

