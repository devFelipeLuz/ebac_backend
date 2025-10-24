package br.com.feluz.services;

import br.com.feluz.dao.InventoryDAO;
import br.com.feluz.dao.ProductQuantityDAO;
import br.com.feluz.dao.SaleDAO;
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

    public SaleService(ISaleDAO dao) {
        super(dao);
    }
}

/*
private final SaleDAO saleDAO;
private final ProductQuantityDAO prodQDAO;
private final InventoryDAO inventoryDAO;

public SaleService(SaleDAO saleDAO, ProductQuantityDAO prodQDAO, InventoryDAO inventoryDAO) {
    super(saleDAO);
    this.saleDAO = saleDAO;
    this.prodQDAO = prodQDAO;
    this.inventoryDAO = inventoryDAO;
}

@Override
public Boolean register(Sale sale) throws DAOException, SQLException, TipoChaveNaoEncontradaException {
    return this.dao.register(sale);
}

@Override
public Sale find(String codigo) throws DAOException, TableException, MoreThanOneRegisterException, SQLException {
    return this.dao.find(codigo);
}

@Override
public void update(Sale sale) throws DAOException, SQLException, TipoChaveNaoEncontradaException {
    this.dao.update(sale);
}

@Override
public void remove(String codigo) throws DAOException, SQLException {
    this.dao.remove(codigo);
}

@Override
public Collection<Sale> findAll() {
    try {
        return this.dao.findAll();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

public void finalizeSale(Sale sale) throws DAOException, TableException, MoreThanOneRegisterException, SQLException, TipoChaveNaoEncontradaException {
    saleDAO.finishSale(sale);
    List<ProductQuantity> itens = prodQDAO.findAll();

    for (ProductQuantity item : itens) {
        Inventory stock = inventoryDAO.find(item.getProduto().getCode());

        if (stock == null) {
            throw new DAOException("ESTOQUE NÃO ENCONTRADO PARA O PRODUTO");
        }
        stock.removeStock(item.getQuantidade());
        inventoryDAO.update(stock);
    }
}

public void cancelSale(Sale sale) throws DAOException, SQLException {
    saleDAO.cancelSale(sale);
}
 */
