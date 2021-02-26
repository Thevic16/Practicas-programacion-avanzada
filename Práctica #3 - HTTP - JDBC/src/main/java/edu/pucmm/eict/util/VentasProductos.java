package edu.pucmm.eict.util;

import edu.pucmm.eict.db.DataBaseServices;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class VentasProductos {
    private long id;
    private String fechaCompra;
    private String nombreCliente;
    private List<ProductoCarrito> listaProductos;
    private double total;

    public double getTotal() {
        double total = 0;

        for (ProductoCarrito producto:listaProductos) {
            total = total + producto.getPrecio().doubleValue()*producto.getCantidad();
        }
        this.total = total;

        return this.total;
    }

    //private static long cont =0;

    public VentasProductos(String fechaCompra, String nombreCliente, List<ProductoCarrito> listaProductos) {
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProductos = listaProductos;

        //Buscando en que numero se quedo el ID.
        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select id from VentasProducto order by id DESC";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            ResultSet rs = prepareStatement.executeQuery();
            rs.next();
            this.id=rs.getInt("id")+1;


        } catch (SQLException ex) {
            System.out.println("Exception thrown  :" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Exception thrown  :" + ex);
            }
        }

    }

    public VentasProductos() {

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
        this.listaProductos = listaProductos;
    }

}
