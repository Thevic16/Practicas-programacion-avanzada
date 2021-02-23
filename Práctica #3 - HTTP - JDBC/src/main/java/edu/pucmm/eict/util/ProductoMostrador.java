package edu.pucmm.eict.util;

import java.math.BigDecimal;

public class ProductoMostrador extends Producto {

    private int id;

    private static int cont =0;

    public ProductoMostrador(String nombre, BigDecimal precio) {
        super(nombre, precio);
        this.id = cont++;
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
        ProductoMostrador.cont = cont;
    }
}
