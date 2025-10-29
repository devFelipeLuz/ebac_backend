package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Sale;
import br.com.feluz.jpa.dao.generic.IGenericJpaDAO;

import javax.persistence.EntityManager;

public interface ISaleDAO extends IGenericJpaDAO<Sale, Long> {

    public void finishSale(Sale sale, EntityManager em);

    public void cancelSale(Sale sale, EntityManager em);
}
