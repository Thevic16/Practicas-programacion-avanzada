package edu.pucmm.eict.db;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BootStrapServices {

    private static Server server;

    /**
     *
     * @throws SQLException
     */
    public static void startDb() throws SQLException {
        server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();
    }

    /**
     *
     * @throws SQLException
     */
    public static void stopDb() throws SQLException {
        server.shutdown();
    }


    /**
     * Metodo para recrear las tablas necesarios
     * @throws SQLException
     */
    /*
    public static void crearTablas() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS ESTUDIANTE\n" +
                "(\n" +
                "  MATRICULA INTEGER PRIMARY KEY NOT NULL,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  APELLIDO VARCHAR(100) NOT NULL,\n" +
                "  TELEFONO VARCHAR(25) NOT NULL,\n" +
                "  CARRERA VARCHAR(50) NOT NULL\n" +
                ");";
        Connection con = DataBaseServices.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }*/

    public static void crearTablaUsuario() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS Usuario\n" +
                "(\n" +
                "  usuario VARCHAR(100) PRIMARY KEY NOT NULL,\n" +
                "  nombre VARCHAR(100) NOT NULL,\n" +
                "  password VARCHAR(100) NOT NULL\n" + ");";

        Connection con = DataBaseServices.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }

    public static void crearTablaProductoMos() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS ProductoMostrador\n" +
                "(\n" +
                "  id INTEGER PRIMARY KEY NOT NULL,\n" +
                "  nombre VARCHAR(100) NOT NULL,\n" +
                "  precio FLOAT NOT NULL\n" + ");";

        Connection con = DataBaseServices.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }

    public static void crearTablaListaProductoCar() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS ListaProductoCarrito\n" +
                "(\n" +
                "  id INTEGER PRIMARY KEY NOT NULL,\n" +
                "  cantidad INTEGER NOT NULL,\n" +
                "  nombre VARCHAR(100) NOT NULL,\n" +
                "  precio FLOAT NOT NULL,\n" +
                "  idVentasProductos INTEGER NOT NULL\n" +");";

        Connection con = DataBaseServices.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();


         sql = "ALTER TABLE ListaProductoCarrito\n" +
                "\n" +
                "  ADD FOREIGN KEY (id)\n" +
                "  REFERENCES ProductoMostrador(id)\n"+";";

        con = DataBaseServices.getInstancia().getConexion();
        statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();

        sql = "ALTER TABLE ListaProductoCarrito\n" +
                "\n" +
                "  ADD FOREIGN KEY (idVentasProductos)\n" +
                "  REFERENCES Ventasproducto(id)\n"+";";

        con = DataBaseServices.getInstancia().getConexion();
        statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();

    }

    public static void crearTablaVentasproducto() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS Ventasproducto\n" +
                "(\n" +
                "  id INTEGER PRIMARY KEY NOT NULL,\n" +
                "  date DATE NOT NULL,\n" +
                "  nombreCliente VARCHAR(100) NOT NULL\n" +");";

        Connection con = DataBaseServices.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }

}
