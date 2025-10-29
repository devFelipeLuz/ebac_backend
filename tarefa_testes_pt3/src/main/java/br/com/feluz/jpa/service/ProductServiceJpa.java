package br.com.feluz.jpa.service;

import br.com.feluz.jpa.Inventory;
import br.com.feluz.jpa.Product;
import br.com.feluz.jpa.connection.ConnectionDB;
import br.com.feluz.jpa.dao.IInventoryDAO;
import br.com.feluz.jpa.dao.IProductDAO;
import br.com.feluz.jpa.service.generic.GenericServiceJpa;

import javax.persistence.EntityManager;

public class ProductServiceJpa extends GenericServiceJpa<Product, Long> implements IProductServiceJpa {
    private final IProductDAO productDAO;
    private final IInventoryDAO inventoryDAO;

    public ProductServiceJpa(IProductDAO productDAO, IInventoryDAO inventoryDAO) {
        super(productDAO);
        this.productDAO = productDAO;
        this.inventoryDAO = inventoryDAO;
    }

    public Boolean saveProductAndInventory(Product product, Inventory inventory) {
        EntityManager em = ConnectionDB.getConnection();

        try {
            em.getTransaction().begin();

            product = productDAO.save(product, em);
            inventory.addProduct(product);
            inventoryDAO.save(inventory, em);

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
