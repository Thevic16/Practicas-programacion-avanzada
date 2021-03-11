package edu.pucmm.eict.util;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Entity
public class ProductoMostrador implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID de forma automatica
    private int id;
    private String nombre;
    private BigDecimal precio;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Foto> fotos;


    public ProductoMostrador() { // Debo tener un contructor vacio.
    }

    public ProductoMostrador(String nombre, BigDecimal precio) {
        this.nombre = nombre;
        this.precio = precio;
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

    public void agregarFoto(Foto foto){
        fotos.add(foto);
    }

    public void eliminarFoto(Foto foto){
        fotos.remove(foto);
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
}
