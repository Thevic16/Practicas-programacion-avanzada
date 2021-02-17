package edu.pucmm.eict.util;

import java.math.BigDecimal;

public class ProductoMostrador extends Producto {

    private int id;

    private static int cont =0;

    public ProductoMostrador(String nombre, BigDecimal precio) {
        super(nombre, precio);
        this.id = cont++;
    }
}
