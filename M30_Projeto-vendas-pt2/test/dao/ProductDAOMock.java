package dao;

import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductDAOMock implements IProductDAO {

    private static Map<String, Product> storage = new HashMap<>();

    @Override
    public Boolean register(Product entity) throws TipoChaveNaoEncontradaException {
        if (!storage.containsKey(entity.getCode())) {
            storage.put(entity.getCode(), entity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean register(Product entity, Connection dataBase) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return register(entity);
    }

    @Override
    public Product find(String value) {
        if (storage.containsKey(value)) {
            return storage.get(value);
        }
        return null;
    }

    @Override
    public Product find(String value, Connection dataBase) throws TableException, DAOException {
        return find(value);
    }

    @Override
    public void update(Product entity) throws TipoChaveNaoEncontradaException {
        if (storage.containsKey(entity.getCode())) {
            storage.put(entity.getCode(), entity);
        }
    }

    @Override
    public void update(Product entity, Connection dataBase) throws DAOException {
        try {
            update(entity);
        } catch (Exception e) {

        }
    }

    @Override
    public void remove(String value) {
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
    public Collection<Product> findAll() {
        return storage.values();
    }
}
