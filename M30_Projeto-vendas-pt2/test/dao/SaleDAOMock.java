package dao;

import br.com.feluz.dao.interfaces.ISaleDAO;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SaleDAOMock implements ISaleDAO {

    private static Map<String, Sale> storage = new HashMap<>();

    @Override
    public Boolean register(Sale entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        if (!storage.containsKey(entity.getCodigo())) {
            storage.put(entity.getCodigo(), entity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean register(Sale entity, Connection dataBase) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return register(entity);
    }

    @Override
    public Sale find(String value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException {
        if (storage.containsKey(value)) {
            return storage.get(value);
        }
        return null;
    }

    @Override
    public Sale find(String value, Connection dataBase) throws TableException, DAOException, MoreThanOneRegisterException, SQLException {
        return find(value);
    }

    @Override
    public void update(Sale entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        if (storage.containsKey(entity.getCodigo())) {
            storage.put(entity.getCodigo(), entity);
        }
    }

    @Override
    public void update(Sale entity, Connection dataBase) throws DAOException {
        try {
            update(entity);
        } catch (Exception e) {

        }
    }

    @Override
    public void remove(String value) throws DAOException, SQLException {
        if (storage.containsKey(value)) {
            storage.remove(value);
        }
    }

    @Override
    public void remove(String value, Connection dataBase) throws DAOException {
        try {
            remove(value);
        } catch (Exception e) {

        }
    }

    @Override
    public Collection<Sale> findAll() throws DAOException, SQLException {
        return storage.values();
    }

    @Override
    public void finishSale(Sale sale) throws TipoChaveNaoEncontradaException, DAOException, SQLException {

    }

    @Override
    public void cancelSale(Sale sale) throws TipoChaveNaoEncontradaException, DAOException, SQLException {

    }
}
