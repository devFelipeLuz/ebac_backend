package br.com.feluz.services;

import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.dao.jdbc.ConnectionDB;
import br.com.feluz.domain.Inventory;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.generics.GenericService;
import br.com.feluz.services.interfaces.IProductService;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductService extends GenericService<Product, String> implements IProductService {
    private final IInventoryDAO inventoryDAO;

    public ProductService(IProductDAO productDAO, IInventoryDAO inventoryDAO) {
        super(productDAO);
        this.inventoryDAO = inventoryDAO;
    }

    @Override
    public Boolean registerProductAndStock(Product product, Inventory inventory) throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        Connection dataBase = ConnectionDB.getConnection();

        try {
            dataBase.setAutoCommit(false);
            Boolean productRegistered = ((IProductDAO) this.dao).register(product, dataBase);
            inventory.setProduct(product);
            inventoryDAO.register(inventory, dataBase);

            dataBase.commit();

            return productRegistered;

        } catch (Exception e) {
            if (dataBase != null) {
                try {
                    dataBase.rollback();
                } catch (SQLException rollbackEx) {
                    throw new DAOException("Erro no rollback após falha no cadastro de Produto/Estoque.", rollbackEx);
                }
            }
            throw e;
        } finally {
            if (dataBase != null) {
                dataBase.setAutoCommit(true);
                dataBase.close();
            }
        }
    }

    @Override
    public Inventory findStockByProduct(Long productId) throws DAOException, SQLException {
        return this.inventoryDAO.findByProduct(productId);
    }

    public Inventory findStock(Long productId) throws DAOException, SQLException {
        Inventory stock = inventoryDAO.findByProduct(productId);
        return stock;
    }

    public void updateStock(Long productId, Integer newQuantity) throws Exception {
        Inventory stock = inventoryDAO.findByProduct(productId);

        if (stock == null) {
            throw new Exception("ESTOQUE NÃO ENCONTRADO PARA O PRODUTO COM ID " + productId);
        }

        stock.setAvailableQuantity(newQuantity);
        inventoryDAO.update(stock);
    }
}
