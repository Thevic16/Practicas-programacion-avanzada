package edu.pucmm.eict.util;

import edu.pucmm.eict.db.ProductoCarritoServices;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Entity
public class VentasProductos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID de forma automatica
    private long id;
    private String fechaCompra;
    private String nombreCliente;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ProductoCarrito> listaProductos;
    private double total;


    //private static long cont =0;

    public VentasProductos(String fechaCompra, String nombreCliente, List<ProductoCarrito> listaProductos) {
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProductos = listaProductos;

        for (ProductoCarrito producto:listaProductos) {
            ProductoCarritoServices.getInstancia().crear(producto);
        }

        this.total = getTotal();
    }

    public VentasProductos() { //debo tener un contructor vacio.
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
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

        for (ProductoCarrito producto:listaProductos) {
            ProductoCarritoServices.getInstancia().crear(producto);
        }

        this.listaProductos = listaProductos;
    }

    public double getTotal() {
        double total = 0;

        for (ProductoCarrito producto:listaProductos) {
            total = total + producto.getPrecio().doubleValue()*producto.getCantidad();
        }
        this.total = total;

        return this.total;
    }
}
