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
    private String descripcion;

    @OneToMany(fetch = FetchType.EAGER)
    @OrderColumn(name="id")
    private List<Foto> fotos;

    @OneToMany(fetch = FetchType.EAGER)
    @OrderColumn(name="id")
    private List<Comentario> comentarios;

    public ProductoMostrador() { // Debo tener un contructor vacio.
    }



    public ProductoMostrador(String nombre, BigDecimal precio, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void agregarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
