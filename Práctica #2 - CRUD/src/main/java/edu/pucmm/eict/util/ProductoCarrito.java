package edu.pucmm.eict.util;

import java.math.BigDecimal;

public class ProductoCarrito extends Producto{
    private int cantidad;

    public ProductoCarrito(int id, String nombre, BigDecimal precio, int cantidad) {
        super(id, nombre, precio);
        this.cantidad = cantidad;
    }
}
