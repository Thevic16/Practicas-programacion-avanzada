package edu.pucmm.eict.util;

import io.javalin.Javalin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Ruta {
    private Javalin app;
    private Administracion administracion;

    public Ruta (Javalin app, Administracion administracion){
        this.app = app;
        this.administracion = administracion;
    }

    public void ejecutarRutas(){

        app.get("/", ctx -> {
            CarroCompra carroCompra= new CarroCompra(); // Creando carrito de compra, aqui se almacenan los productos del usuario.
            ctx.sessionAttribute("carroCompra", carroCompra);

            ctx.redirect("/index");
        });

        //index
        app.get("/index", ctx -> {
            ctx.result("Pagina index");
        });

        app.get("/index/agreagar/:nombreProducto/:precioProducto/:cantidadProducto", ctx -> {

            String nombreProducto = ctx.pathParam("nombreProducto");
            BigDecimal precioProducto = ctx.pathParam("cantidadProducto",BigDecimal.class).get();
            int cantidadProducto = ctx.pathParam("cantidadProducto",Integer.class).get();

            ProductoCarrito productoCarrito = new ProductoCarrito(nombreProducto,precioProducto,cantidadProducto);

            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

            carroCompra.agregarProducto(productoCarrito);

            ctx.redirect("/index");
        });

        //carrito de compas
        app.get("/carritoDeCompra", ctx -> {
            ctx.result("Pagina carrito de compra");
        });

        app.get("/carritoDeCompra/eliminar/:nombreProducto/:precioProducto/:cantidadProducto", ctx -> {

            String nombreProducto = ctx.pathParam("nombreProducto");
            BigDecimal precioProducto = ctx.pathParam("cantidadProducto",BigDecimal.class).get();
            int cantidadProducto = ctx.pathParam("cantidadProducto",Integer.class).get();

            ProductoCarrito productoCarrito = new ProductoCarrito(nombreProducto,precioProducto,cantidadProducto);

            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

            carroCompra.eliminarProducto(productoCarrito);

            ctx.redirect("/carritoDeCompra");
        });

        //login ListarProductos
        app.get("/listarProductos/login", ctx -> {

            ctx.result("login");
        });

        app.post("/listarProductos/login", ctx -> {
            String usuario = ctx.formParam("usuario");
            String nombre = ctx.formParam("nombre");
            String password = ctx.formParam("password");

            ctx.redirect("/listarProductos");
        });

        // Listar productos
        app.get("/listarProductos", ctx -> {

            ctx.result("Listar productos");
        });

        //Crear Producto
        app.get("/listarProductos/crearProducto", ctx -> {

            ctx.result("Crear Producto");
        });

        //Editar Producto
        app.get("/listarProductos/editarProducto/:nombre/:precio", ctx -> {
            String nombreProducto = ctx.pathParam("nombreProducto");
            BigDecimal precioProducto = ctx.pathParam("cantidadProducto",BigDecimal.class).get();

            ctx.result("Editar Producto");
        });

        //Eliminar Producto
        app.get("/listarProductos/editarProducto/:idProductoMostrador", ctx -> {

            int idProductoMostrador = ctx.pathParam("cantidadProducto",Integer.class).get();

            administracion.eliminarProducto(idProductoMostrador);

            ctx.redirect("/listarProductos");
        });

        //Login ventas realizadas
        app.get("/ventasRealizadas/login", ctx -> {

            ctx.result("login");
        });

        app.post("/ventasRealizadas/login", ctx -> {
            String usuario = ctx.formParam("usuario");
            String nombre = ctx.formParam("nombre");
            String password = ctx.formParam("password");

            ctx.redirect("/ventasRealizadas");
        });

        //Ventas Realizadas
        app.get("/ventasRealizadas", ctx -> {

            ctx.result("Ventas Realizadas");
        });

        //Carrito de compra
        app.get("/carritoDeCompra", ctx -> {

            ctx.result("Carrito de compra");
        });

        app.get("/carritoDeCompra/ProcesarCompra/:nombreCliente/", ctx -> {

            String nombreCliente = ctx.pathParam("nombreCliente");
            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

            VentasProductos ventasProductos = new VentasProductos(new Date(),nombreCliente,carroCompra.getListaProductos());

            CarroCompra carroCompraNuevo= new CarroCompra(); // Creando carrito de compra nuevo, aqui se almacenan los productos del usuario.
            ctx.sessionAttribute("carroCompra", carroCompraNuevo);

            ctx.result("Carrito de compra");
        });


    }


}
