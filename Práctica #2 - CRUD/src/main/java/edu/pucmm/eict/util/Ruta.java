package edu.pucmm.eict.util;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ruta {
    private Javalin app;
    private Administracion administracion;
    private boolean login = false;

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
            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("productos",administracion.getListaProductosDisponibles(carroCompra));
            modelo.put("cantidadCarrito", (carroCompra.obtenerCantidadProductos()));

            this.login = false;

            ctx.render("/templates/index/index.html",modelo);
        });


        app.post("/index/agregar/", ctx -> {
            int id = ctx.formParam("id",Integer.class).get();
            int cantidadProducto = ctx.formParam("cantidad",Integer.class).get();
            String nombreProducto = administracion.encontrarProductoPorId(id).getNombre();
            BigDecimal precioProducto = administracion.encontrarProductoPorId(id).getPrecio();

            ProductoCarrito productoCarrito = new ProductoCarrito(id,nombreProducto,precioProducto,cantidadProducto);

            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

            carroCompra.agregarProducto(productoCarrito);

            ctx.redirect("/index");
        });


        //carrito de compas
        app.get("/carritoDeCompra", ctx -> {
            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("productos",carroCompra.getListaProductos());
            modelo.put("cantidadCarrito", (carroCompra.obtenerCantidadProductos()));

            ctx.render("/templates/CarritoDeCompra/CarritoDeCompra.html",modelo);
        });

        app.post("/carritoDeCompra/eliminar/", ctx -> {
            int id = ctx.formParam("id",Integer.class).get();

            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

            carroCompra.eliminarProducto(id);

            ctx.redirect("/carritoDeCompra");
        });

        app.post("/carritoDeCompra/ProcesarCompra/", ctx -> {

            String nombreCliente = ctx.formParam("nombreCliente");
            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

            String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            VentasProductos ventasProductos = new VentasProductos(simpleDateFormat.format(new Date()),nombreCliente,carroCompra.getListaProductos());
            administracion.agregarVentasProductos(ventasProductos);

            ctx.sessionAttribute("carroCompra", null); // Limpiando el carrito de compras

            ctx.redirect("/index");
        });

        app.get("/carritoDeCompra/LimpiarCarroCompra/", ctx -> {
            ctx.sessionAttribute("carroCompra", null); // Limpiando el carrito de compras

            ctx.redirect("/index");
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
                this.login = true;
            }
            else{
                ctx.redirect("/ventasRealizadas/login");
            }
        });

        //Ventas Realizadas
        app.get("/ventasRealizadas", ctx -> {
            if(this.login){
                CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("cantidadCarrito", (carroCompra.obtenerCantidadProductos()));
                modelo.put("ventasProductos", administracion.getListaVentasProductos());

                ctx.render("/templates/ventasRealizadas/ventasRealizadas.html",modelo);
            }
            else{
                ctx.redirect("/ventasRealizadas/login");
            }
        });

        // Listar productos
        app.get("/listarProductos", ctx -> {
            if(this.login){
                CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("cantidadCarrito", (carroCompra.obtenerCantidadProductos()));
                modelo.put("productos",administracion.getListaProductos());

                ctx.render("/templates/listasProductos/listarProductos.html",modelo);            }
            else{
                ctx.redirect("/listarProductos/login");
            }
        });

        //login ListarProductos
        app.get("/listarProductos/login", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("accion","/listarProductos/login");

            ctx.render("/templates/login/login.html",modelo);
        });

        app.post("/listarProductos/login", ctx -> {
            String usuario = ctx.formParam("usuario");
            String password = ctx.formParam("password");

            if (Usuario.login(usuario,password)){
                ctx.redirect("/listarProductos");
                this.login = true;
            }
            else{
                ctx.redirect("/listarProductos/login");
            }
        });

        //Eliminar Producto
        app.post("/listarProductos/eliminar/", ctx -> {
            int idProductoMostrador = ctx.formParam("id",Integer.class).get();

            administracion.eliminarProducto(idProductoMostrador);

            ctx.redirect("/listarProductos");
        });

        //Editar Producto
        app.post("/listarProductos/editar/", ctx -> {
            int idProductoMostrador = ctx.formParam("id",Integer.class).get();
            ctx.sessionAttribute("idProductoEditar",idProductoMostrador);


            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo","| Editar Producto");
            modelo.put("titulo2","Editar Producto");
            modelo.put("accion","/listarProductos/editar/procesar");


            ctx.render("/templates/Crear-EditarProducto/Crear-Editar.html",modelo);
        });

        app.post("/listarProductos/editar/procesar", ctx -> {
            int idProductoMostrador = ctx.sessionAttribute("idProductoEditar");
            String nombreProducto = ctx.formParam("nombre");
            BigDecimal precioProducto =  new BigDecimal(ctx.formParam("precio",Integer.class).get());

            administracion.actualizarProducto(idProductoMostrador,new ProductoMostrador(nombreProducto,precioProducto));

            ctx.redirect("/listarProductos");
        });

        //Crear Producto
        app.get("/listarProductos/crearProducto", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo","| Crear Producto");
            modelo.put("titulo2","Crear Producto");
            modelo.put("accion","/listarProductos/crear/procesar");


            ctx.render("/templates/Crear-EditarProducto/Crear-Editar.html",modelo);
        });

        app.post("/listarProductos/crear/procesar", ctx -> {
            String nombreProducto = ctx.formParam("nombre");
            BigDecimal precioProducto =  new BigDecimal(ctx.formParam("precio",Integer.class).get());

            administracion.agregarProducto(new ProductoMostrador(nombreProducto,precioProducto));

            ctx.redirect("/listarProductos");
        });


    }

    private void registrandoPlantillas(){
        //registrando los sistemas de plantilla.
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }


}
