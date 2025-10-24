package dao;

import br.com.feluz.dao.interfaces.IProductQuantityDAO;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class ProductQuantityDAOMock implements IProductQuantityDAO {
    @Override
    public Boolean register(ProductQuantity entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return true;
    }

    @Override
    public ProductQuantity find(Long value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException {
        ProductQuantity prodQ = new ProductQuantity();
        prodQ.setId(value);
        return prodQ;
    }

    @Override
    public void update(ProductQuantity entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {

    }

    @Override
    public void remove(Long value) throws DAOException, SQLException {

    }

    @Override
    public Collection<ProductQuantity> findAll() throws DAOException, SQLException {
        return null;
    }
}
