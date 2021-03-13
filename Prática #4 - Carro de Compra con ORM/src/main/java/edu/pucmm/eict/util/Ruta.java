package edu.pucmm.eict.util;

import edu.pucmm.eict.db.ComentarioServices;
import edu.pucmm.eict.db.FotoServices;
import edu.pucmm.eict.db.ProductoMostradorServices;
import edu.pucmm.eict.db.VentasProductosServices;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.jasypt.util.text.AES256TextEncryptor;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ruta {
    private Javalin app;
    private Administracion administracion;
    private boolean login = false;
    private VentasProductos ventasProductos;
    private List<Foto> fotos;
    private List<Comentario> comentarios;

    public Ruta (Javalin app, Administracion administracion){
        this.app = app;
        this.administracion = administracion;
        registrandoPlantillas();
        this.ventasProductos = new VentasProductos();
        this.ventasProductos.setNombreCliente("Cliente en progreso......");
        this.ventasProductos.setFechaCompra("No disponible");
        VentasProductosServices.getInstancia().crear(this.ventasProductos);
        this.fotos = new ArrayList<Foto>();
        this.comentarios = new ArrayList<Comentario>();
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
            modelo.put("productos",administracion.getListaProductos());
            modelo.put("cantidadCarrito", (carroCompra.obtenerCantidadProductos()));

            this.login = false;

            ctx.render("/templates/index/index.html",modelo);
        });


        app.post("/index/agregar/", ctx -> {
            int id = ctx.formParam("id",Integer.class).get();
            int cantidadProducto = ctx.formParam("cantidad",Integer.class).get();
            String nombreProducto = administracion.encontrarProductoPorId(id).getNombre();
            BigDecimal precioProducto = administracion.encontrarProductoPorId(id).getPrecio();

            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");
            ProductoCarrito productoCarrito = carroCompra.encontrarProductoPorId(id);

            if(productoCarrito == null){
                productoCarrito = new ProductoCarrito(id,nombreProducto,precioProducto,cantidadProducto,ventasProductos.getId());
                carroCompra.agregarProducto(productoCarrito);
            }
            else{
                productoCarrito.setCantidad(productoCarrito.getCantidad()+cantidadProducto);
            }

            ctx.redirect("/index");
        });

        app.post("/index/VisualizarInfoProduct/", ctx -> {
            int id = ctx.formParam("idVisualizar",Integer.class).get();
            ctx.sessionAttribute("idVisualizar",id);
            ProductoMostrador producto = administracion.encontrarProductoPorId(id);
            ctx.sessionAttribute("nombreProducto1",producto.getNombre());
            ctx.sessionAttribute("precioProducto1",producto.getPrecio());
            ctx.sessionAttribute("descripcionProducto1",producto.getDescripcion());

            List<Foto> fotos=  producto.getFotos();

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("producto",producto);
            modelo.put("fotos",fotos);
            modelo.put("comentarios",producto.getComentarios());
            if(ctx.cookie("Recuerdame") != null){
                modelo.put("login",true);
            }
            else{
                modelo.put("login",false);
            }

            ctx.render("/templates/VisualizarInfoProduct/VisualizarInfoProduct.html",modelo);
        });

        app.get("/index/VisualizarInfoProduct/", ctx -> {
            int id = ctx.sessionAttribute("idVisualizar");
            ProductoMostrador producto = administracion.encontrarProductoPorId(id);
            this.fotos=  producto.getFotos();
            this.comentarios = producto.getComentarios();

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("producto",producto);
            modelo.put("fotos",fotos);
            modelo.put("comentarios",this.comentarios);
            if(ctx.cookie("Recuerdame") != null){
                modelo.put("login",true);
            }
            else{
                modelo.put("login",false);
            }

            ctx.render("/templates/VisualizarInfoProduct/VisualizarInfoProduct.html",modelo);
        });

        app.post("/index/VisualizarInfoProduct/procecarComentario/", ctx -> {
            String nombreCliente  = ctx.formParam("nombreCliente");
            String comentario  = ctx.formParam("comentario");
            int idProducto  = ctx.formParam("idProducto",Integer.class).get();

            Comentario comentarioInstancia = new Comentario(nombreCliente,comentario);
            this.comentarios.add(comentarioInstancia);
            ComentarioServices.getInstancia().crear(comentarioInstancia);

            ProductoMostrador producto = ProductoMostradorServices.getInstancia().findProductById(idProducto);
            producto.getComentarios().add(comentarioInstancia);

            ProductoMostradorServices.getInstancia().editar(producto);


            ctx.redirect("/index/VisualizarInfoProduct/");
        });

        app.post("/index/VisualizarInfoProduct/eliminarComentario/", ctx -> {
            int idComentario  = ctx.formParam("idComentario",Integer.class).get();
            int idProducto2  = ctx.formParam("idProducto2",Integer.class).get();

            ProductoMostrador producto = ProductoMostradorServices.getInstancia().findProductById(idProducto2);
            Comentario comentario =ComentarioServices.getInstancia().find(idComentario);

            int n = producto.getComentarios().size();
            int i = 0;
            boolean encontrado = false;
            while (!encontrado && i<n) {
                if(comentario.getId() == producto.getComentarios().get(i).getId()){
                    producto.getComentarios().remove(i);
                    encontrado = true;
                }
                i++;
            }

            n = this.comentarios.size();
            i = 0;
            encontrado = false;
            while (!encontrado && i<n) {
              if(comentario.getId() == this.comentarios.get(i).getId()){
                  this.comentarios.remove(i);
                  encontrado = true;
              }
              i++;
            }

            ProductoMostradorServices.getInstancia().editar(producto);
            ComentarioServices.getInstancia().eliminar(idComentario);

            ctx.redirect("/index/VisualizarInfoProduct/");
        });

        app.get("/index/VisualizarInfoProduct/volver/", ctx -> {

            String nombreProducto1 = ctx.sessionAttribute("nombreProducto1");
            BigDecimal precioProducto = ctx.sessionAttribute("precioProducto1");
            String descripcionProducto1 = ctx.sessionAttribute("descripcionProducto1");

            ProductoMostrador productoNuevo = new ProductoMostrador(nombreProducto1,precioProducto,descripcionProducto1);

            int idProducto  = ctx.sessionAttribute("idVisualizar");

            productoNuevo.setFotos(ProductoMostradorServices.getInstancia().find(idProducto).getFotos());
            productoNuevo.setComentarios(this.comentarios);

            ProductoMostradorServices.getInstancia().eliminar(idProducto);

            ProductoMostradorServices.getInstancia().crear(productoNuevo);
            //administracion.actualizarProducto(idProducto,producto);

            ctx.redirect("/index/");
        });

        //ventas realizadas
        app.get("/ventasRealizadas/login", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("accion","/ventasRealizadas/login");

            ctx.render("/templates/login/login.html",modelo);
        });

        app.post("/ventasRealizadas/login", ctx -> {
            String usuario = ctx.formParam("usuario");
            String password = ctx.formParam("password");
            String recuerdame = ctx.formParam("Recuerdame");

            if (administracion.login(usuario,password)){
                ctx.redirect("/ventasRealizadas");
                this.login = true;

                //Recordar al usuario - Asignar cookie.
                if(recuerdame != null){
                    AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
                    textEncryptor.setPassword("myEncryptionPassword");
                    ctx.cookie("Recuerdame",textEncryptor.encrypt(usuario),604800);
                    //System.out.println("Cookie:"+ctx.cookie("Recuerdame"));
                }
            }
            else{
                ctx.redirect("/ventasRealizadas/login");
            }


        });

        app.get("/ventasRealizadas", ctx -> {
            if(this.login || ctx.cookie("Recuerdame") != null){
                CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("cantidadCarrito", (carroCompra.obtenerCantidadProductos()));
                modelo.put("ventasProductos", administracion.getListaVentasProductos());

                this.login = false;

                ctx.render("/templates/ventasRealizadas/ventasRealizadas.html",modelo);
            }
            else{
                ctx.redirect("/ventasRealizadas/login");
            }
        });

        // Listar productos
        app.get("/listarProductos", ctx -> {
            if(this.login || ctx.cookie("Recuerdame") != null){
                CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("cantidadCarrito", (carroCompra.obtenerCantidadProductos()));
                modelo.put("productos",administracion.getListaProductos());

                this.login = false;

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
            String recuerdame = ctx.formParam("Recuerdame");


            if (administracion.login(usuario,password)){
                ctx.redirect("/listarProductos");
                this.login = true;

                //Recordar al usuario - Asignar cookie.
                if(recuerdame != null){
                    AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
                    textEncryptor.setPassword("myEncryptionPassword");
                    ctx.cookie("Recuerdame",textEncryptor.encrypt(usuario),604800);
                    //System.out.println("Cookie:"+ctx.cookie("Recuerdame"));
                }

            }
            else{
                ctx.redirect("/listarProductos/login");
            }
        });

        app.post("/listarProductos/eliminar/", ctx -> {
            int idProductoMostrador = ctx.formParam("id",Integer.class).get();

            administracion.eliminarProducto(idProductoMostrador);

            ctx.redirect("/listarProductos");
        });

        app.post("/listarProductos/editar/", ctx -> {
            int idProductoMostrador = ctx.formParam("id",Integer.class).get();
            ctx.sessionAttribute("idProductoEditar",idProductoMostrador);

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo","| Editar Producto");
            modelo.put("titulo2","Editar Producto");
            //modelo.put("accion","/listarProductos/editar/procesar");
            modelo.put("accion","/listarProductos/foto/fromEditar");

            //this.fotos.clear(); //Limpiando Array List para cargas las fotos correspondientes.
            ProductoMostrador producto = ProductoMostradorServices.getInstancia().find(idProductoMostrador);
            this.fotos = producto.getFotos();

            ctx.render("/templates/Crear-EditarProducto/Crear-Editar.html",modelo);
        });

        app.post("/listarProductos/editar/procesar", ctx -> {
            int idProductoMostrador = ctx.sessionAttribute("idProductoEditar");
            String nombreProducto = ctx.sessionAttribute("nombreProducto");
            BigDecimal precioProducto =  ctx.sessionAttribute("precioProducto");
            String descripcion = ctx.sessionAttribute("descripcion");

            ProductoMostrador producto = new ProductoMostrador(nombreProducto,precioProducto,descripcion);
            producto.setFotos(this.fotos);
            administracion.actualizarProducto(idProductoMostrador,producto);

            this.fotos = new ArrayList<Foto>(); // Limpiando fotos.

            ctx.redirect("/listarProductos");
        });

        app.get("/listarProductos/crearProducto", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo","| Crear Producto");
            modelo.put("titulo2","Crear Producto");
            //modelo.put("accion","/listarProductos/crear/procesar");
            modelo.put("accion","/listarProductos/foto/fromCrear");

            this.fotos = new ArrayList<Foto>(); //Limpiando las lista de fotos.

            ctx.render("/templates/Crear-EditarProducto/Crear-Editar.html",modelo);
        });

        app.post("/listarProductos/crear/procesar", ctx -> {
            String nombreProducto = ctx.sessionAttribute("nombreProducto");
            BigDecimal precioProducto =  ctx.sessionAttribute("precioProducto");
            String descripcion = ctx.sessionAttribute("descripcion");

            ProductoMostrador producto = new ProductoMostrador(nombreProducto,precioProducto,descripcion);
            producto.setFotos(this.fotos);
            administracion.agregarProducto(producto);

            this.fotos = new ArrayList<Foto>(); // Limpiando fotos.

            ctx.redirect("/listarProductos");
        });


        //Fotos
        app.post("/listarProductos/foto/fromCrear", ctx -> {
            //int idProductoMostrador = ctx.sessionAttribute("idProductoEditar"); //Pasandole el ID del producto.
            String nombreProducto = ctx.formParam("nombre");
            BigDecimal precioProducto =  new BigDecimal(ctx.formParam("precio",Integer.class).get());
            String descripcion = ctx.formParam("descripcion");

            ctx.sessionAttribute("nombreProducto",nombreProducto);
            ctx.sessionAttribute("precioProducto",precioProducto);
            ctx.sessionAttribute("descripcion",descripcion);

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("fotos",fotos);
            //modelo.put("id",idProductoMostrador);
            //modelo.put("metodo","get");
            modelo.put("accion","/listarProductos/crear/procesar");
            modelo.put("accionSubir","/listarProductos/foto/subir/fromCrear");
            modelo.put("esCrear",true);

            ctx.render("/templates/listarFoto/listarFoto.html",modelo);
        });

        app.get("/listarProductos/foto/fromCrear", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("fotos",fotos);
            //modelo.put("id",idProductoMostrador);
            //modelo.put("metodo","get");
            modelo.put("accion","/listarProductos/crear/procesar");
            modelo.put("accionSubir","/listarProductos/foto/subir/fromCrear");
            modelo.put("esCrear",true);

            ctx.render("/templates/listarFoto/listarFoto.html",modelo);
        });

        app.post("/listarProductos/foto/fromEditar", ctx -> {
            int idProductoMostrador = ctx.sessionAttribute("idProductoEditar"); //Pasandole el ID del producto.

            String nombreProducto = ctx.formParam("nombre");
            BigDecimal precioProducto =  new BigDecimal(ctx.formParam("precio",Integer.class).get());
            String descripcion = ctx.formParam("descripcion");

            ctx.sessionAttribute("nombreProducto",nombreProducto);
            ctx.sessionAttribute("precioProducto",precioProducto);
            ctx.sessionAttribute("descripcion",descripcion);

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("fotos",fotos);
            modelo.put("id",idProductoMostrador);
            modelo.put("metodo","post");
            modelo.put("accion","/listarProductos/editar/procesar");
            modelo.put("accionSubir","/listarProductos/foto/subir/fromEditar");
            modelo.put("esCrear",false);

            ctx.render("/templates/listarFoto/listarFoto.html",modelo);
        });

        app.get("/listarProductos/foto/fromEditar", ctx -> {
            int idProductoMostrador = ctx.sessionAttribute("idProductoEditar"); //Pasandole el ID del producto.

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("fotos",this.fotos);
            modelo.put("id",idProductoMostrador);
            modelo.put("metodo","post");
            modelo.put("accion","/listarProductos/editar/procesar");
            modelo.put("accionSubir","/listarProductos/foto/subir/fromEditar");
            modelo.put("esCrear",false);

            ctx.render("/templates/listarFoto/listarFoto.html",modelo);
        });

        app.post("/listarProductos/foto/subir/fromCrear", ctx -> {
            ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                try {
                    byte[] bytes = uploadedFile.getContent().readAllBytes();
                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                    Foto foto = new Foto(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                    FotoServices.getInstancia().crear(foto);
                    this.fotos.add(foto);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ctx.redirect("/listarProductos/foto/fromCrear");
            });
        });

        app.post("/listarProductos/foto/subir/fromEditar", ctx -> {
            ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                try {
                    byte[] bytes = uploadedFile.getContent().readAllBytes();
                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                    Foto foto = new Foto(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                    FotoServices.getInstancia().crear(foto);
                    this.fotos.add(foto);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ctx.redirect("/listarProductos/foto/fromEditar");
            });
        });

        app.get("/listarProductos/foto/visualizar/:id", ctx -> {
            try {
                Foto foto = FotoServices.getInstancia().find(ctx.pathParam("id", Long.class).getOrNull());
                if(foto==null){
                    ctx.redirect("/listarProductos/foto/");
                    return;
                }
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("foto", foto);
                //
                ctx.render("/templates/VisualizarFoto/visualizarFoto.html", modelo);
            }catch (Exception e){
                System.out.println("Error: "+e.getMessage());
                ctx.redirect("/listarProductos/foto/");
            }
        });

        app.get("/listarProductos/foto/eliminar/:id/:idProducto/:esCrear", ctx -> {
            int idProducto = 0;
            boolean esCrear = ctx.pathParam("esCrear",boolean.class).getOrNull();
            if(esCrear == false){
                idProducto = ctx.pathParam("idProducto",int.class).getOrNull();
            }

            try {
                Foto foto = FotoServices.getInstancia().find(ctx.pathParam("id", Long.class).getOrNull());
                if(foto!=null){
                    for (Foto f:this.fotos) {
                        if(f.getId() == foto.getId()){
                            fotos.remove(f);
                            //FotoServices.getInstancia().eliminar(foto.getId());
                        }
                    }
                }
            }catch (Exception e){
                System.out.println("Error: "+e.getMessage());
            }

            if(esCrear ==true){
                ctx.redirect("/listarProductos/foto/fromCrear");
            }
            else{
                ctx.redirect("/listarProductos/foto/fromEditar");
            }
        });


        //login

        app.get("/login/crearCuenta", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("accion","/login/crearCuenta");

            ctx.render("/templates/crearCuenta/crearCuenta.html",modelo);
        });

        app.post("/login/crearCuenta", ctx -> {
            String usuario = ctx.formParam("usuario");
            String nombre = ctx.formParam("nombre");
            String password = ctx.formParam("password");
            String confirmacion = ctx.formParam("confirmacion");

            if(password.equals(confirmacion)){
                administracion.agregarUsuario(new Usuario(usuario,nombre,password));

                ctx.redirect("/index");
            }
            else{
                ctx.redirect("/login/crearCuenta");
            }

        });

        //carrito de compas
        app.get("/carritoDeCompra", ctx -> {
            CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("productos",carroCompra.getListaProductos());
            modelo.put("cantidadCarrito", (carroCompra.obtenerCantidadProductos()));
            modelo.put("totalPrecio",carroCompra.getPrecioTotal());

            this.login = false;

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


            this.ventasProductos.setFechaCompra(simpleDateFormat.format(new Date()));
            this.ventasProductos.setNombreCliente(nombreCliente);
            this.ventasProductos.setListaProductos(carroCompra.getListaProductos());

            administracion.agregarVentasProductos(this.ventasProductos);

            VentasProductosServices.getInstancia().desconectar(this.ventasProductos);

            this.ventasProductos = new VentasProductos(); //Creando la siguiente venta.
            this.ventasProductos.setNombreCliente("Cliente en progreso......");
            this.ventasProductos.setFechaCompra("No disponible");
            VentasProductosServices.getInstancia().crear(this.ventasProductos);


            ctx.sessionAttribute("carroCompra", null); // Limpiando el carrito de compras

            ctx.redirect("/index");
        });

        app.get("/carritoDeCompra/LimpiarCarroCompra/", ctx -> {
            ctx.sessionAttribute("carroCompra", null); // Limpiando el carrito de compras

            ctx.redirect("/index");
        });

    }

    private void registrandoPlantillas(){
        //registrando los sistemas de plantilla.
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }


}
