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

    //producto
    public List<ProductoMostrador> getListaProductos() {
        this.listaProductos.clear();
        ProductoMostrador producto = null;


        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select * from PRODUCTOMOSTRADOR ";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                producto = new ProductoMostrador();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getBigDecimal("precio"));

                this.listaProductos.add(producto);
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

        return listaProductos;
    }

    public List<ProductoMostrador> getListaProductosDisponibles(CarroCompra carroCompra) {
        List<ProductoMostrador> listaProductosDisponibles = new ArrayList<ProductoMostrador>();

        for (ProductoMostrador productoMostrador : getListaProductos()) {
            if (carroCompra.encontrarProductoPorId(productoMostrador.getId()) == null) {
                listaProductosDisponibles.add(productoMostrador);
            }
        }

        return listaProductosDisponibles;
    }

    public void setListaProductos(List<ProductoMostrador> listaProductos) {

        this.listaProductos.clear();

        for (ProductoMostrador producto:listaProductos) {
            agregarProducto(producto);
        }

        //this.listaProductos = listaProductos;
    }

    public void agregarProducto(ProductoMostrador producto) {

        Connection con = null;
        try {

            String query = "insert into PRODUCTOMOSTRADOR(id, nombre, precio) values(?,?,?)";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, producto.getId());
            prepareStatement.setString(2, producto.getNombre());
            prepareStatement.setBigDecimal(3, producto.getPrecio());


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

        //this.listaProductos.add(producto);
    }

    public void eliminarProducto(int id) {
        ProductoMostrador producto = encontrarProductoPorId(id);

        Connection con = null;
        try {

            String query = "delete PRODUCTOMOSTRADOR where id=? and nombre =? and precio=?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, producto.getId());
            prepareStatement.setString(2, producto.getNombre());
            prepareStatement.setBigDecimal(3, producto.getPrecio());


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

        //this.listaProductos.remove(encontrarProductoPorId(id));
    }

    public void actualizarProducto(int idProductoAnterior, ProductoMostrador productoActualizado) {
        ProductoMostrador productoAnterior = encontrarProductoPorId(idProductoAnterior);

        Connection con = null;
        try {

            String query = "update PRODUCTOMOSTRADOR set id =?, nombre=?, precio=? where id=? ";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, productoActualizado.getId());
            prepareStatement.setString(2, productoActualizado.getNombre());
            prepareStatement.setBigDecimal(3, productoActualizado.getPrecio());
            prepareStatement.setInt(4, productoAnterior.getId());


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

        //this.listaProductos.set(listaProductos.indexOf(encontrarProductoPorId(idProductoAnterior)), productoActualizado);
    }

    public ProductoMostrador encontrarProductoPorId(int id) {
        ProductoMostrador producto = null;
        boolean encontrado = false;
        int i = 0;

        while (!encontrado && i < getListaProductos().size()) {
            if (getListaProductos().get(i).getId() == id) {
                encontrado = true;
                producto = getListaProductos().get(i);
            }
            i++;
        }
        return producto;
    }

    //Ventas
    public List<VentasProductos> getListaVentasProductos() {

        this.listaProductos.clear();
        VentasProductos ventasProductos = null;


        Connection con = null;
        try {
            String query = "select * from VENTASPRODUCTO ";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                ventasProductos = new VentasProductos();
                ventasProductos.setId(rs.getInt("id"));
                ventasProductos.setFechaCompra(rs.getString("date"));
                ventasProductos.setNombreCliente(rs.getString("nombreCliente"));

                this.listaVentasProductos.add(ventasProductos);
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

        return listaVentasProductos;
    }

    public void setVentasProductos(List<VentasProductos> ventasProductos) {

        this.listaVentasProductos.clear();

        for (VentasProductos venta:ventasProductos) {
            agregarVentasProductos(venta);
        }

        //this.listaVentasProductos = ventasProductos;
    }

    public void agregarVentasProductos(VentasProductos Ventasproducto) {
        Connection con = null;
        try {

            String query = "insert into VENTASPRODUCTO(id, date, nombreCliente) values(?,?,?)";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setLong(1, Ventasproducto.getId());
            prepareStatement.setString(2, Ventasproducto.getFechaCompra());
            prepareStatement.setString(3, Ventasproducto.getNombreCliente());


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

        //this.listaVentasProductos.add(Ventasproducto);
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

        this.usuarios.clear();

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
