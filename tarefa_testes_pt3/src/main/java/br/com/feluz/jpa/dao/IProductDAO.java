package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Product;
import br.com.feluz.jpa.dao.generic.IGenericJpaDAO;

import javax.persistence.EntityManager;

public interface IProductDAO extends IGenericJpaDAO<Product, Long> {

    public Product save(Product product, EntityManager em);

}
