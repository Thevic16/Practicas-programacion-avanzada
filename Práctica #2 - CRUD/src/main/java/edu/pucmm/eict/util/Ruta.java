package edu.pucmm.eict.util;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ruta {
    private Javalin app;
    private Administracion administracion;

    public Ruta (Javalin app, Administracion administracion){
        this.app = app;
        this.administracion = administracion;
        registrandoPlantillas();
    }

    public void ejecutarRutas(){

        app.before(ctx -> {
            // runs before all requests
            if(ctx.sessionAttribute("carroCompra") == null){
                CarroCompra carroCompra= new CarroCompra(); // Creando carrito de compra, aqui se almacenan los productos del usuario.
                ctx.sessionAttribute("carroCompra", carroCompra);
            }
        });

        app.get("/", ctx -> {
            ctx.redirect("/index");
        });

        //index
        app.get("/index", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("productos",administracion.getListaProductos());

            ctx.render("/templates/index/index.html",modelo);
        });

        /*
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
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("accion","/ventasRealizadas/login");

            ctx.render("/templates/login/login.html",modelo);
        });

        app.post("/ventasRealizadas/login", ctx -> {
            String usuario = ctx.formParam("usuario");
            String password = ctx.formParam("password");

            if (Usuario.login(usuario,password)){
                ctx.redirect("/ventasRealizadas");
            }
            else{
                ctx.redirect("/ventasRealizadas/login");
            }

        });

        //Ventas Realizadas
        app.get("/ventasRealizadas", ctx -> {

            ctx.render("/templates/ventasRealizadas/ventasRealizadas.html");
        });

        //Carrito de compra
        app.get("/carritoDeCompra", ctx -> {

            ctx.result("Carrito de compra");
        });

        app.get("/carritoDeCompra/ProcesarCompra/:nombreCliente/", ctx -> {

            String nombreCliente = ctx.pathParam("nombreCliente");
            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

            VentasProductos ventasProductos = new VentasProductos(new Date(),nombreCliente,carroCompra.getListaProductos());

            ctx.sessionAttribute("carroCompra", null); // Limpiando el carrito de compras

            ctx.redirect("/index");
        });

        app.get("/carritoDeCompra/LimpiarCarroCompra/", ctx -> {
            ctx.sessionAttribute("carroCompra", null); // Limpiando el carrito de compras

            ctx.redirect("/index");
        });

         */

    }

    private void registrandoPlantillas(){
        //registrando los sistemas de plantilla.
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }


}
