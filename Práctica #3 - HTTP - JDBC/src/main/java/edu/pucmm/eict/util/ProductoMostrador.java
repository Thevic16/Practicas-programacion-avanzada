package edu.pucmm.eict.util;

import edu.pucmm.eict.db.DataBaseServices;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoMostrador extends Producto {

    private int id;

    private static int cont =0;

    public ProductoMostrador(String nombre, BigDecimal precio) {
        super(nombre, precio);

        //Buscando en que numero se quedo el ID.
        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select id from PRODUCTOMOSTRADOR order by id DESC";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            ResultSet rs = prepareStatement.executeQuery();
            rs.next();
            this.id=rs.getInt("id")+1;


        } catch (SQLException ex) {
            System.out.println("Exception thrown  :" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Exception thrown  :" + ex);
            }
        }

    }
    public ProductoMostrador() {
        super();
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
