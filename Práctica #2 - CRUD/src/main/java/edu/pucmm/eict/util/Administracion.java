package edu.pucmm.eict.util;

import java.util.List;

public class Administracion {
    private List<Producto> listaProductos;

    public Administracion(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public void agregarProducto(Producto producto) {
        this.listaProductos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        this.listaProductos.remove(producto);
    }

    public void actualizarProducto(Producto productoAnterior,Producto productoActualizado) {
        this.listaProductos.set(listaProductos.indexOf(productoAnterior),productoActualizado);
    }

}
