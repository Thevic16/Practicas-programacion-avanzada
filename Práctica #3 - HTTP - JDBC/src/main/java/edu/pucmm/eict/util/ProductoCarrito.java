package edu.pucmm.eict.util;

import java.math.BigDecimal;

public class ProductoCarrito extends Producto{
    private int cantidad;

    private int id;

    public ProductoCarrito(int id,String nombre, BigDecimal precio, int cantidad) {
        super(nombre, precio);
        this.cantidad = cantidad;
        this.id = id;
    }

    public ProductoCarrito() {
        super();
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
