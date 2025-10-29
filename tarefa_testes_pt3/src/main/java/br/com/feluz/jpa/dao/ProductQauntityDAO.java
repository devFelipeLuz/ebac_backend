package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.ProductQuantity;
import br.com.feluz.jpa.dao.generic.GenericJpaDB1DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProductQauntityDAO extends GenericJpaDB1DAO<ProductQuantity, Long> implements IProductQuantityDAO {

    public ProductQauntityDAO() {
        super(ProductQuantity.class);
    }

    @Override
    public List<ProductQuantity> findBySale(Long saleId, EntityManager em) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductQuantity> query = cb.createQuery(ProductQuantity.class);
            Root<ProductQuantity> root = query.from(ProductQuantity.class);
            query.select(root).where(cb.equal(root.get("sale").get("id"), saleId));

            TypedQuery<ProductQuantity> tpQuery = em.createQuery(query);
            return tpQuery.getResultList();

        } catch (Exception e) {
            throw e;

        }
    }
}
