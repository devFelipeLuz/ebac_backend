package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Product;
import br.com.feluz.jpa.dao.generic.GenericJpaDB1DAO;

import javax.persistence.EntityManager;

public class ProductDAO extends GenericJpaDB1DAO<Product, Long> implements IProductDAO {

    public ProductDAO() {
        super(Product.class);
    }

    public Product save(Product product, EntityManager em) {
        if (product.getId() == null) {
            em.persist(product);
        } else {
            product = em.merge(product);
        }
        em.getTransaction().commit();
        return product;
    }
}
