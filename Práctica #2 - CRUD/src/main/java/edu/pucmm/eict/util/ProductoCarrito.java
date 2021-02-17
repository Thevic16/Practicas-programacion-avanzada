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
}
