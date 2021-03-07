package edu.pucmm.eict.db;

import edu.pucmm.eict.util.ProductoMostrador;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ProductoMostradorServices extends GestionDb<ProductoMostrador>{
    private static ProductoMostradorServices instancia;

    private ProductoMostradorServices() {
        super(ProductoMostrador.class);
    }

    public static ProductoMostradorServices getInstancia(){
        if(instancia == null){
            instancia = new ProductoMostradorServices();
        }
        return instancia;
    }

    public ProductoMostrador findProductById(int id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from ProductoMostrador p where p.id = :id");
        query.setParameter("id",  Integer.valueOf(id));
        ProductoMostrador producto = (ProductoMostrador) query.getSingleResult();
        return producto;
    }
}
