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
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
            config.enableCorsForAllOrigins();
        });
        app.config.addStaticFiles("/publico");
        app.start(getHerokuAssignedPort());


        new Ruta(app,administracion).ejecutarRutas();
    }

    /**
     * Metodo para indicar el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }
}

