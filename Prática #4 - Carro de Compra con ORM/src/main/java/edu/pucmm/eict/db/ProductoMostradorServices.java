package edu.pucmm.eict.db;

import edu.pucmm.eict.util.ProductoMostrador;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ProductoMostradorServices extends GestionDb<ProductoMostrador> {
    private static ProductoMostradorServices instancia;

    private ProductoMostradorServices() {
        super(ProductoMostrador.class);
    }

    public static ProductoMostradorServices getInstancia() {
        if (instancia == null) {
            instancia = new ProductoMostradorServices();
        }
        return instancia;
    }

    public ProductoMostrador findProductById(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from ProductoMostrador p where p.id = :id");
        query.setParameter("id", Integer.valueOf(id));
        ProductoMostrador producto = (ProductoMostrador) query.getSingleResult();
        return producto;
    }

    public List<ProductoMostrador> findAll(int startPosition, int maxResult) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<ProductoMostrador> criteriaQuery = em.getCriteriaBuilder().createQuery(ProductoMostrador.class);
            criteriaQuery.select(criteriaQuery.from(ProductoMostrador.class));
            return em.createQuery(criteriaQuery).setFirstResult(startPosition).setMaxResults(maxResult).getResultList();
        } finally {
            em.close();
        }
    }

    public long total() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder qb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = qb.createQuery(long.class);
            cq.select(qb.count(cq.from(ProductoMostrador.class)));
            return em.createQuery(cq).getSingleResult();

        } finally {
            em.close();
        }
    }

}
