package dao;

import br.com.feluz.dao.interfaces.ISaleDAO;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class SaleDAOMock implements ISaleDAO {
    @Override
    public void finishSale(Sale sale) throws TipoChaveNaoEncontradaException, DAOException, SQLException {

    }

    @Override
    public void cancelSale(Sale sale) throws TipoChaveNaoEncontradaException, DAOException, SQLException {

    }

    @Override
    public Boolean register(Sale entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return true;
    }

    @Override
    public Sale find(String value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException {
        Sale sale = new Sale();
        sale.setCodigo(value);
        return sale;
    }

    @Override
    public void update(Sale entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {

    }

    @Override
    public void remove(String value) throws DAOException, SQLException {

    }

    @Override
    public Collection<Sale> findAll() throws DAOException, SQLException {
        return null;
    }
}
