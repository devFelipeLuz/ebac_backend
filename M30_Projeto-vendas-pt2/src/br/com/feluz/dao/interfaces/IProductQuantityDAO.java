package br.com.feluz.dao.interfaces;

import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.exceptions.DAOException;

import java.sql.SQLException;
import java.util.List;

public interface IProductQuantityDAO extends IGenericDAO<ProductQuantity, Long> {

    List<ProductQuantity> findBySale(Long saleId) throws SQLException, DAOException;
    
}
