package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Costumer;
import br.com.feluz.jpa.Product;
import br.com.feluz.jpa.ProductQuantity;
import br.com.feluz.jpa.Sale;
import br.com.feluz.jpa.connection.ConnectionDB;
import br.com.feluz.jpa.dao.generic.GenericJpaDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SaleDAO extends GenericJpaDAO<Sale, Long> implements ISaleDAO {

    public SaleDAO() {
        super(Sale.class);
    }

    public void finishSale(Sale sale, EntityManager em) {
        super.save(sale);
    }

    public void cancelSale(Sale sale, EntityManager em) {
        super.save(sale);
    }

    @Override
    public Sale save(Sale sale) {
        EntityManager em = ConnectionDB.getConnection();

        try {
            em.getTransaction().begin();

            Costumer costumer = em.merge(sale.getCostumer());
            sale.setCostumer(costumer);

            if (sale.getId() == null) {
                em.persist(sale);
            } else {
                sale = em.merge(sale);
            }

            for (ProductQuantity pq : sale.getProducts()) {
                Product product = em.merge(pq.getProduct());
                pq.setProduct(product);
                pq.setSale(sale);

                if (pq.getId() == null) {
                    em.persist(pq);
                } else {
                    em.merge(pq);
                }
            }

            em.getTransaction().commit();
            return sale;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Sale> findAll() {
        EntityManager em = ConnectionDB.getConnection();
        try {
            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Sale> query = cb.createQuery(Sale.class);
            Root<Sale> root = query.from(Sale.class);
            root.fetch("costumer");
            root.fetch("products");
            TypedQuery<Sale> tpQuery = em.createQuery(query);
            return tpQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
