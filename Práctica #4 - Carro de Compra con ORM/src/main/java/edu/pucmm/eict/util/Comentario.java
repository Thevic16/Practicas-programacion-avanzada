package edu.pucmm.eict.util;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Comentario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID de forma automatica
    private int id;
    private String nombreCLiente;
    private String textoComentario;

    public Comentario(String nombreCLiente, String textoComentario) {
        this.nombreCLiente = nombreCLiente;
        this.textoComentario = textoComentario;
    }

    public Comentario() { // Debo tener un contructor vacio.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCLiente() {
        return nombreCLiente;
    }

    public void setNombreCLiente(String nombreCLiente) {
        this.nombreCLiente = nombreCLiente;
    }

    public String getTextoComentario() {
        return textoComentario;
    }

    public void setTextoComentario(String textoComentario) {
        this.textoComentario = textoComentario;
    }
}
