package edu.pucmm.eict.util;

import java.util.List;

public class Administracion {
    private List<ProductoMostrador> listaProductos;

    public Administracion(List<ProductoMostrador> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<ProductoMostrador> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoMostrador> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public void agregarProducto(ProductoMostrador producto) {
        this.listaProductos.add(producto);
    }

    public void eliminarProducto(ProductoMostrador producto) {
        this.listaProductos.remove(producto);
    }

    public void actualizarProducto(ProductoMostrador productoAnterior,ProductoMostrador productoActualizado) {
        this.listaProductos.set(listaProductos.indexOf(productoAnterior),productoActualizado);
    }

}
