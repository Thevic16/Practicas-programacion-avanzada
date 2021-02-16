package edu.pucmm.eict.util;

import io.javalin.Javalin;

import java.util.List;

public class Ruta {
    private Javalin app;
    private List<Producto> listaProductos;

    public Ruta (Javalin app, List<Producto> listaProductos){
        this.app = app;
        this.listaProductos = listaProductos;
    }

    public void ejecutarRutas(){

        app.get("/", ctx -> {
            // some code
            ctx.redirect("/index");
        });

        app.get("/index", ctx -> {
            // some code
            //CarroCompra carroCompra= new CarroCompra();

            ctx.result("Pagina principal");
        });

    }


}
