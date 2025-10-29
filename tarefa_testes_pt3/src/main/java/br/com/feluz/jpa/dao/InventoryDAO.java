package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Inventory;
import br.com.feluz.jpa.connection.ConnectionDB;
import br.com.feluz.jpa.dao.generic.GenericJpaDB1DAO;

import javax.persistence.EntityManager;

public class InventoryDAO extends GenericJpaDB1DAO<Inventory, Long> implements IInventoryDAO {

    public InventoryDAO() {
        super(Inventory.class);
    }

    @Override
    public Inventory save(Inventory inventory) {
        EntityManager em = ConnectionDB.getConnection();

        try {
            em.getTransaction().begin();

            if (inventory.getId() == null) {
                em.persist(inventory);
            } else {
                inventory.getProducts().forEach(em::merge);
                inventory = em.merge(inventory);
            }
            em.getTransaction().commit();
            return inventory;

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
    public Inventory save(Inventory inventory, EntityManager em) {

        inventory.getProducts().forEach(em::merge);

        if (inventory.getId() == null) {
            em.persist(inventory);
        } else {
            inventory = em.merge(inventory);
        }

        return inventory;
    }

    @Override
    public Inventory find(Long id, EntityManager em) {

        return em.find(Inventory.class, id);

    }
}
