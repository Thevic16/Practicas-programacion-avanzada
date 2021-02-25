package edu.pucmm.eict.util;

import edu.pucmm.eict.db.DataBaseServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Administracion {
    private List<ProductoMostrador> listaProductos;
    private List<VentasProductos> listaVentasProductos;
    private List<Usuario> usuarios;

    public Administracion() {
        this.listaProductos = new ArrayList<ProductoMostrador>();
        this.listaVentasProductos = new ArrayList<VentasProductos>();
        this.usuarios = new ArrayList<Usuario>();
    }

    public List<ProductoMostrador> getListaProductos() {
        return listaProductos;
    }

    public List<ProductoMostrador> getListaProductosDisponibles(CarroCompra carroCompra) {
        List<ProductoMostrador> listaProductosDisponibles = new ArrayList<ProductoMostrador>();

        for (ProductoMostrador productoMostrador : listaProductos) {
            if (carroCompra.encontrarProductoPorId(productoMostrador.getId()) == null) {
                listaProductosDisponibles.add(productoMostrador);
            }
        }

        return listaProductosDisponibles;
    }

    public void setListaProductos(List<ProductoMostrador> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<VentasProductos> getListaVentasProductos() {
        return listaVentasProductos;
    }

    public void setVentasProductos(List<VentasProductos> ventasProductos) {
        this.listaVentasProductos = ventasProductos;
    }

    public void agregarVentasProductos(VentasProductos Ventasproducto) {
        this.listaVentasProductos.add(Ventasproducto);
    }


    public void agregarProducto(ProductoMostrador producto) {
        this.listaProductos.add(producto);
    }

    public void eliminarProducto(int id) {
        this.listaProductos.remove(encontrarProductoPorId(id));
    }

    public void actualizarProducto(int idProductoAnterior, ProductoMostrador productoActualizado) {
        this.listaProductos.set(listaProductos.indexOf(encontrarProductoPorId(idProductoAnterior)), productoActualizado);
    }

    public ProductoMostrador encontrarProductoPorId(int id) {
        ProductoMostrador producto = null;
        boolean encontrado = false;
        int i = 0;

        while (!encontrado && i < listaProductos.size()) {
            if (listaProductos.get(i).getId() == id) {
                encontrado = true;
                producto = listaProductos.get(i);
            }
            i++;
        }
        return producto;
    }

    //Usuario
    public List<Usuario> getUsuarios() {
        Usuario usuario = null;
        this.usuarios.clear();

        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select * from Usuario";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPassword(rs.getString("password"));

                this.usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            System.out.println("Exception thrown  :" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Exception thrown  :" + ex);
            }
        }

        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {

        for (Usuario usuario:usuarios) {
            agregarUsuario(usuario);
        }

        //this.usuarios = usuarios;
    }

    public void agregarUsuario(Usuario usuario) {

        Connection con = null;
        try {

            String query = "insert into Usuario(usuario, nombre, password) values(?,?,?)";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, usuario.getUsuario());
            prepareStatement.setString(2, usuario.getNombre());
            prepareStatement.setString(3, usuario.getPassword());


            prepareStatement.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("Exception thrown  :" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Exception thrown  :" + ex);
            }
        }

        //this.usuarios.add(usuario);
    }

    public boolean login(String usuarioCuenta, String password) {
        boolean login = false;

        if (usuarioCuenta.equals("admin") && password.equals("admin")) {
            login = true;
        } else {
            Usuario usuario = encontrarUsuarioPorUsuario(usuarioCuenta);
            if (usuario != null) {
                if (usuarioCuenta.equals(usuario.getUsuario()) && password.equals(usuario.getPassword())) {
                    login = true;
                }
            }
        }

        return login;
    }

    public Usuario encontrarUsuarioPorUsuario(String usuarioCuenta) {
        Usuario usuario = null;
        boolean encontrado = false;
        int i = 0;

        this.usuarios.clear();

        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select * from Usuario";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPassword(rs.getString("password"));

                this.usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            System.out.println("Exception thrown  :" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Exception thrown  :" + ex);
            }
        }

        while (!encontrado && i < usuarios.size()) {
            if (usuarios.get(i).getUsuario().equals(usuarioCuenta)) {
                encontrado = true;
                usuario = usuarios.get(i);
            }
            i++;
        }
        return usuario;
    }



}
