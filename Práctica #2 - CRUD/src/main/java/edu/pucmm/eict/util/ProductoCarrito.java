package edu.pucmm.eict.util;

import java.math.BigDecimal;

public class ProductoCarrito extends Producto{
    private int cantidad;

    private int id;
    private static int cont =0;

    public ProductoCarrito(String nombre, BigDecimal precio, int cantidad) {
        super(nombre, precio);
        this.cantidad = cantidad;
        this.id = cont++;
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

    public static int getCont() {
        return cont;
    }

    public static void setCont(int cont) {
        ProductoCarrito.cont = cont;
    }
}
