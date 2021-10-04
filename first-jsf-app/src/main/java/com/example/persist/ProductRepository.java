package com.example.persist;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProductRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

//    EntityGraph<?> eg = em.getEntityGraph("product-with-category-graph");

    public List<Product> findAll() {
        return em.createQuery("from Product", Product.class)
                .getResultList();
    }

    public Optional<Product> findById(long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    public List<Product> findByCategoryId(long id) {
        return em.createQuery("from Product p where p.category.id = :id", Product.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
            return product;
        }
        return em.merge(product);
    }

    public void delete(long id) {
        em.createQuery("delete from Product where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public long count() {
        return em.createQuery("select count(*) from Product ", Long.class)
                .getSingleResult();
    }
}
