package dao;

import br.com.feluz.dao.interfaces.ICostumerDAO;
import br.com.feluz.domain.Costumer;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CostumerDAOMock implements ICostumerDAO {

    private static Map<Long, Costumer> storage = new HashMap<>();

    public CostumerDAOMock() {}

    @Override
    public Boolean register(Costumer entity) throws TipoChaveNaoEncontradaException {
        if (!storage.containsKey(entity.getCpf())) {
            storage.put(entity.getCpf(), entity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean register(Costumer entity, Connection dataBase) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return register(entity);
    }

    @Override
    public Costumer find(Long value) {
        if (storage.containsKey(value)) {
            return storage.get(value);
        }
        return null;
    }

    @Override
    public Costumer find(Long value, Connection dataBase) throws TableException, DAOException {
        return find(value);
    }

    @Override
    public void update(Costumer entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        if (storage.containsKey(entity.getCpf())) {
            storage.put(entity.getCpf(), entity);
        }
    }

    @Override
    public void update(Costumer entity, Connection dataBase) throws DAOException {
        try {
            update(entity);
        } catch (Exception e) {

        }
    }

    @Override
    public void remove(Long value) throws DAOException, SQLException {
        if (storage.containsKey(value)) {
            storage.remove(value);
        }
    }

    @Override
    public void remove(Long value, Connection dataBase) throws DAOException {
        try {
            remove(value);
        } catch (Exception e) {

        }
    }

    @Override
    public Collection<Costumer> findAll() throws DAOException, SQLException {
        return storage.values();
    }
}
