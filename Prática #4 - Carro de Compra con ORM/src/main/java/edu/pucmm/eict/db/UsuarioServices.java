package edu.pucmm.eict.db;


import edu.pucmm.eict.util.ProductoMostrador;
import edu.pucmm.eict.util.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UsuarioServices extends GestionDb<Usuario>{
    private static UsuarioServices instancia;

    private UsuarioServices() {
        super(Usuario.class);
    }

    public static UsuarioServices getInstancia(){
        if(instancia == null){
            instancia = new UsuarioServices();
        }
        return instancia;
    }

    public Usuario findUserByUser(String UsuarioCuenta){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Usuario u where u.usuario like :usuario");
        query.setParameter("usuario", UsuarioCuenta);
        Usuario usuario = (Usuario) query.getSingleResult();
        return usuario;
    }
}
