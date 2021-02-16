package edu.pucmm.eict;

import edu.pucmm.eict.util.Producto;
import edu.pucmm.eict.util.Ruta;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Producto> listaProductos = new ArrayList<Producto>(); // Lista de productos visible por todo el sistema (Apartado 6)


        //Creando la instancia del servidor.
        Javalin app = Javalin.create().start(7000);
        app.config.addStaticFiles("/publico");
        //app.get("/", ctx -> ctx.result("Hello World"));

        new Ruta(app,listaProductos).ejecutarRutas();
    }
}
