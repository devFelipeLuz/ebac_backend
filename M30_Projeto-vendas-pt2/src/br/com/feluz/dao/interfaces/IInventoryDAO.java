package br.com.feluz.dao.interfaces;

import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.domain.Inventory;
import br.com.feluz.exceptions.DAOException;

import java.sql.SQLException;

public interface IInventoryDAO extends IGenericDAO<Inventory, String> {

    Inventory findByProduct(Long productId) throws DAOException, SQLException;
}
