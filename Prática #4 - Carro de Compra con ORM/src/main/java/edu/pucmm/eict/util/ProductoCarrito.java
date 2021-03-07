package edu.pucmm.eict.util;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class ProductoCarrito implements Serializable {

    @Id
    private int id;
    private int cantidad;
    private String nombre;
    private BigDecimal precio;


    public ProductoCarrito() { //Debo tener un constructor vacio
    }

    public ProductoCarrito(int id,String nombre, BigDecimal precio, int cantidad) {
        this.cantidad = cantidad;
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}
