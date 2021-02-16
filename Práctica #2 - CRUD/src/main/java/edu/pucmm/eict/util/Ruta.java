package edu.pucmm.eict.util;

import io.javalin.Javalin;

import java.math.BigDecimal;
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

        app.get("/agreagar/:idProducto/:nombreProducto/:precioProducto/:cantidadProducto", ctx -> {

            int idProducto = ctx.pathParam("idProducto",Integer.class).get();
            String nombreProducto = ctx.pathParam("nombreProducto");
            BigDecimal precioProducto = ctx.pathParam("cantidadProducto",BigDecimal.class).get();
            int cantidadProducto = ctx.pathParam("cantidadProducto",Integer.class).get();


            ctx.result("Pagina principal");
        });



    }


}
