package br.com.feluz.jpa.service;

import br.com.feluz.jpa.exception.DAOException;
import br.com.feluz.jpa.Inventory;
import br.com.feluz.jpa.ProductQuantity;
import br.com.feluz.jpa.Sale;
import br.com.feluz.jpa.connection.ConnectionDB;
import br.com.feluz.jpa.dao.IInventoryDAO;
import br.com.feluz.jpa.dao.IProductQuantityDAO;
import br.com.feluz.jpa.dao.ISaleDAO;
import br.com.feluz.jpa.service.generic.GenericServiceJpa;

import javax.persistence.EntityManager;
import java.util.List;

public class SaleServiceJpa extends GenericServiceJpa<Sale, Long> implements ISaleServiceJpa {
    private final ISaleDAO saleDAO;
    private final IProductQuantityDAO prodQDAO;
    private final IInventoryDAO inventoryDAO;

    public SaleServiceJpa(ISaleDAO saleDAO, IProductQuantityDAO prodQDAO, IInventoryDAO inventoryDAO) {
        super(saleDAO);
        this.saleDAO = saleDAO;
        this.prodQDAO = prodQDAO;
        this.inventoryDAO = inventoryDAO;
    }

    public void finalizeSale(Sale sale) throws DAOException {
        EntityManager em = ConnectionDB.getConnection();
        List<ProductQuantity> itens = prodQDAO.findBySale(sale.getId(), em);

        try {
            em.getTransaction().begin();

            for (ProductQuantity item : itens) {
                Inventory stock = inventoryDAO.find(item.getProduct().getId(), em);

                if (stock == null) {
                    throw new DAOException("ESTOQUE NÃO ENCONTRADO PARA O PRODUTO");
                }

                if (stock.getAvailableQuantity() < item.getQuantity()) {
                    throw new DAOException("ESTOQUE INSUFICIENTE PARA O PRODUTO " + item.getProduct().getNome());
                }
                stock.removeStock(item.getQuantity());
                inventoryDAO.save(stock, em);

            }
            saleDAO.finishSale(sale, em);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void cancelSale(Sale sale) throws DAOException {
        EntityManager em = ConnectionDB.getConnection();

        try {
            if (sale.getStatus() == Sale.Status.CONCLUIDA) {
                List<ProductQuantity> itens = prodQDAO.findBySale(sale.getId(), em);
                em.getTransaction().begin();

                for (ProductQuantity item : itens) {
                    Inventory stock = inventoryDAO.find(item.getProduct().getId(), em);

                    if (stock == null) {
                        throw new DAOException("ESTOQUE NÃO ENCONTRADO PARA O PRODUTO: " + item.getProduct().getCode());
                    }

                    stock.addStock(item.getQuantity());
                    inventoryDAO.save(stock, em);
                }
                saleDAO.cancelSale(sale, em);
                em.getTransaction().commit();
            }

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
