package edu.pucmm.eict.util;

import java.util.ArrayList;
import java.util.List;

public class CarroCompra {
    private long id;
    private List<ProductoCarrito> listaProductos;

    private static long cont =0;

    public CarroCompra() {
        this.id = cont++;
        this.listaProductos = new ArrayList<ProductoCarrito>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProductoCarrito> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoCarrito> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Producto encontrarProductoPorId(String id){
        Producto producto = null;
        boolean encontrado = false;
        int i = 0;

        while (!encontrado && i < listaProductos.size()){
            if(listaProductos.get(i) == producto){
                encontrado = true;
            }
            i++;
        }
        return producto;
    }

    public void agregarProducto(ProductoCarrito producto) {
        this.listaProductos.add(producto);
    }
    public void eliminarProducto(ProductoCarrito producto) {
        this.listaProductos.remove(producto);
    }



}
