package edu.pucmm.eict.util;

import java.util.ArrayList;
import java.util.List;

public class Administracion {
    private List<ProductoMostrador> listaProductos;
    private List<VentasProductos> ventasProductos;
    private List<Usuario> usuarios;

    public Administracion() {
        this.listaProductos = new ArrayList<ProductoMostrador>();
        this.ventasProductos = new ArrayList<VentasProductos>();
        this.usuarios = new ArrayList<Usuario>();
    }

    public List<ProductoMostrador> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoMostrador> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<VentasProductos> getVentasProductos() {
        return ventasProductos;
    }

    public void setVentasProductos(List<VentasProductos> ventasProductos) {
        this.ventasProductos = ventasProductos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void agregarProducto(ProductoMostrador producto) {
        this.listaProductos.add(producto);
    }

    public void eliminarProducto(int id) {
        this.listaProductos.remove(encontrarProductoPorId(id));
    }

    public void actualizarProducto(int idProductoAnterior,ProductoMostrador productoActualizado) {
        this.listaProductos.set(listaProductos.indexOf(encontrarProductoPorId(idProductoAnterior)),productoActualizado);
    }

    public ProductoMostrador encontrarProductoPorId(int id){
        ProductoMostrador producto = null;
        boolean encontrado = false;
        int i = 0;

        while (!encontrado && i < listaProductos.size()){
            if(listaProductos.get(i).getId() == id){
                encontrado = true;
                producto = listaProductos.get(i);
            }
            i++;
        }
        return producto;
    }

}
