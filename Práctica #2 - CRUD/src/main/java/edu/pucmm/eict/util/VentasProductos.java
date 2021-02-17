package edu.pucmm.eict.util;

import java.util.Date;
import java.util.List;

public class VentasProductos {
    private long id;
    private Date fechaCompra;
    private String nombreCliente;
    private List<ProductoCarrito> listaProductos;

    private static long cont =0;

    public VentasProductos(Date fechaCompra, String nombreCliente, List<ProductoCarrito> listaProductos) {
        this.id = cont++;
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProductos = listaProductos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<ProductoCarrito> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoCarrito> listaProductos) {
        this.listaProductos = listaProductos;
    }

}
