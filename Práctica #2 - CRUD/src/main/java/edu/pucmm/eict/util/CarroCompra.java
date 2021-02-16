package edu.pucmm.eict.util;

import java.util.List;

public class CarroCompra {
    private long id;
    private List<Producto> listaProductos;

    private static long cont =0;

    public CarroCompra(List<Producto> listaProductos) {
        this.id = cont++;
        this.listaProductos = listaProductos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

}
