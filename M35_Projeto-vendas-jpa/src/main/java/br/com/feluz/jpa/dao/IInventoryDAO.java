package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Inventory;
import br.com.feluz.jpa.dao.generic.IGenericJpaDAO;

import javax.persistence.EntityManager;

public interface IInventoryDAO extends IGenericJpaDAO<Inventory, Long> {
    Inventory save(Inventory entity, EntityManager em);

    Inventory find(Long id, EntityManager em);
}
