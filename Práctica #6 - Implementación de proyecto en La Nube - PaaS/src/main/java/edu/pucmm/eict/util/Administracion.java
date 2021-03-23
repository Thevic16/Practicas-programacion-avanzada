package edu.pucmm.eict.util;

import edu.pucmm.eict.db.ProductoCarritoServices;
import edu.pucmm.eict.db.ProductoMostradorServices;
import edu.pucmm.eict.db.UsuarioServices;
import edu.pucmm.eict.db.VentasProductosServices;

import java.util.ArrayList;
import java.util.List;

public class Administracion {
    private List<ProductoMostrador> listaProductos;
    private List<VentasProductos> listaVentasProductos;
    private List<Usuario> usuarios;


    public Administracion() {
        this.listaProductos = new ArrayList<ProductoMostrador>();
        this.listaVentasProductos = new ArrayList<VentasProductos>();
        this.usuarios = new ArrayList<Usuario>();
    }

    public List<ProductoMostrador> getListaProductos() {
        List<ProductoMostrador> lista =ProductoMostradorServices.getInstancia().findAll();

        if(lista != null){
            return lista;
        }
        else{
            return listaProductos;
        }

    }

    public void setListaProductos(List<ProductoMostrador> listaProductos) {

        for (ProductoMostrador producto:listaProductos) {
            ProductoMostradorServices.getInstancia().crear(producto);
        }

        //this.listaProductos = listaProductos;
    }

    public List<VentasProductos> getListaVentasProductos() {
        List<VentasProductos> lista =VentasProductosServices.getInstancia().findAll();

        if(lista != null){
            return lista;
        }
        else{
            return listaVentasProductos;
        }

    }

    public void setVentasProductos(List<VentasProductos> ventasProductos) {
        for (VentasProductos ventas :ventasProductos) {
            VentasProductosServices.getInstancia().crear(ventas);
        }
    }

    public void agregarVentasProductos(VentasProductos Ventasproducto) {
        //ProductoCarritoServices.getInstancia().
        VentasProductosServices.getInstancia().editar(Ventasproducto);
        //this.listaVentasProductos.add(Ventasproducto);
    }

    public List<Usuario> getUsuarios() {

        List<Usuario> lista = UsuarioServices.getInstancia().findAll();

        if(lista != null){
            return lista;
        }
        else{
            return usuarios;
        }
    }

    public void setUsuarios(List<Usuario> usuarios) {
        for (Usuario usuario :usuarios) {
            UsuarioServices.getInstancia().crear(usuario);
        }
        //this.usuarios = usuarios;
    }

    public void agregarProducto(ProductoMostrador producto) {
        ProductoMostradorServices.getInstancia().crear(producto);
        //this.listaProductos.add(producto);
    }

    public void eliminarProducto(int id) {
        ProductoMostradorServices.getInstancia().eliminar(id);
        //this.listaProductos.remove(encontrarProductoPorId(id));
    }

    public void actualizarProducto(int idProductoAnterior,ProductoMostrador productoActualizado) {
        ProductoMostradorServices.getInstancia().eliminar(idProductoAnterior);
        ProductoMostradorServices.getInstancia().crear(productoActualizado);
        //this.listaProductos.set(listaProductos.indexOf(encontrarProductoPorId(idProductoAnterior)),productoActualizado);
    }

    public ProductoMostrador encontrarProductoPorId(int id){
        return ProductoMostradorServices.getInstancia().findProductById(id);
    }

    public boolean login(String usuarioCuenta, String password) {
        boolean login = false;

        if (usuarioCuenta.equals("admin") && password.equals("admin")) {
            login = true;
        } else {

            Usuario usuario = UsuarioServices.getInstancia().findUserByUser(usuarioCuenta);
            if (usuario != null) {
                if (usuarioCuenta.equals(usuario.getUsuario()) && password.equals(usuario.getPassword())) {
                    login = true;
                }
            }
        }

        return login;
    }


    public void agregarUsuario(Usuario usuario) {
        UsuarioServices.getInstancia().crear(usuario);
        //this.usuarios.add(usuario);
    }

}
