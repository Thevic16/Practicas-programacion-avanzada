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
            CarroCompra carroCompra= new CarroCompra(); // Creando carrito de compra, aqui se almacenan los productos del usuario.

            ctx.sessionAttribute("carroCompra", carroCompra);

            ctx.result("Pagina principal");
        });

        app.get("/agreagar/:nombreProducto/:precioProducto/:cantidadProducto", ctx -> {

            String nombreProducto = ctx.pathParam("nombreProducto");
            BigDecimal precioProducto = ctx.pathParam("cantidadProducto",BigDecimal.class).get();
            int cantidadProducto = ctx.pathParam("cantidadProducto",Integer.class).get();


            ProductoCarrito productoCarrito = new ProductoCarrito(nombreProducto,precioProducto,cantidadProducto);

            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

            carroCompra.agregarProducto(productoCarrito);


            ctx.redirect("/index");
        });





    }


}
