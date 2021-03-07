package edu.pucmm.eict;

import edu.pucmm.eict.db.BootStrapServices;
import edu.pucmm.eict.db.ProductoCarritoServices;
import edu.pucmm.eict.util.*;
import io.javalin.Javalin;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Administracion administracion = new Administracion(); /*
        -Lista de usuarios visible por todo el sistema (Apartado 2)
        -Lista de productos visible por todo el sistema (Apartado 3)
        */


        //Creando la instancia del servidor.
        Javalin app = Javalin.create().start(7000);
        app.config.addStaticFiles("/publico");


        new Ruta(app,administracion).ejecutarRutas();

        //Prueba Crear tablas
        //Iniciando el servicio
        BootStrapServices.getInstancia().init();
    }

}

