package edu.pucmm.eict;

import edu.pucmm.eict.util.Administracion;
import edu.pucmm.eict.util.Producto;
import edu.pucmm.eict.util.Ruta;
import edu.pucmm.eict.util.Usuario;
import io.javalin.Javalin;

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
        //app.get("/", ctx -> ctx.result("Hello World"));

        new Ruta(app,administracion).ejecutarRutas();
    }
}
