package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.ProductQuantity;
import br.com.feluz.jpa.dao.generic.IGenericJpaDAO;

import javax.persistence.EntityManager;
import java.util.List;

public interface IProductQuantityDAO extends IGenericJpaDAO<ProductQuantity, Long> {

    public List<ProductQuantity> findBySale(Long saleId, EntityManager em);

}
