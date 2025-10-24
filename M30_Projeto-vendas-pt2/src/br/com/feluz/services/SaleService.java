package br.com.feluz.services;

import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.dao.interfaces.IProductQuantityDAO;
import br.com.feluz.dao.interfaces.ISaleDAO;
import br.com.feluz.domain.Inventory;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.generics.GenericService;
import br.com.feluz.services.interfaces.ISaleService;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


public class SaleService extends GenericService<Sale, String> implements ISaleService {
    private final ISaleDAO saleDAO;
    private final IProductQuantityDAO prodQDAO;
    private final IInventoryDAO inventoryDAO;

    public SaleService(ISaleDAO saleDAO, IProductQuantityDAO prodQDAO, IInventoryDAO inventoryDAO) {
        super(saleDAO);
        this.saleDAO = saleDAO;
        this.prodQDAO = prodQDAO;
        this.inventoryDAO = inventoryDAO;
    }

    public void finalizeSale(Sale sale) throws DAOException, TableException, MoreThanOneRegisterException, SQLException, TipoChaveNaoEncontradaException {
        saleDAO.finishSale(sale);
        List<ProductQuantity> itens = prodQDAO.findBySale(sale.getId());

        for (ProductQuantity item : itens) {
            Inventory stock = inventoryDAO.find(item.getProduto().getCode());

            if (stock == null) {
                throw new DAOException("ESTOQUE NÃO ENCONTRADO PARA O PRODUTO");
            }

            if (stock.getAvailableQuantity() < item.getQuantidade()) {
                throw new DAOException("ESTOQUE INSUFICIENTE PARA O PRODUTO " + item.getProduto().getNome());
            } else {
                stock.removeStock(item.getQuantidade());
                inventoryDAO.update(stock);
            }
        }
    }

    public void cancelSale(Sale sale) throws DAOException, SQLException, TipoChaveNaoEncontradaException, TableException, MoreThanOneRegisterException {
        if (sale.getStatus() == Sale.Status.CONCLUIDA) {
            List<ProductQuantity> itens = prodQDAO.findBySale(sale.getId());

            for (ProductQuantity item : itens) {
                Inventory stock = inventoryDAO.find(item.getProduto().getCode());

                if (stock == null) {
                    throw new DAOException("ESTOQUE NÃO ENCONTRADO PARA O PRODUTO: " + item.getProduto().getCode());
                }

                stock.addStock(item.getQuantidade());
                inventoryDAO.update(stock);
            }
            saleDAO.cancelSale(sale);
        }
    }
}
