package br.com.feluz.services.interfaces;

import br.com.feluz.domain.Inventory;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.generics.IGenericService;

import java.sql.SQLException;

public interface IProductService extends IGenericService<Product, String> {

    public Boolean registerProductAndStock(Product product, Inventory inventory) throws DAOException, SQLException, TipoChaveNaoEncontradaException;

    public Inventory findStockByProduct(Long productId) throws DAOException, SQLException;
}
