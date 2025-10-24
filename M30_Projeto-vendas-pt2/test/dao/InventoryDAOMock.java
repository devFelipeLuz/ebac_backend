package dao;

import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.domain.Inventory;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventoryDAOMock implements IInventoryDAO {

    private static Map<String, Inventory> storage = new HashMap<>();

    @Override
    public Boolean register(Inventory entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        if (!storage.containsKey(entity.getCode())) {
            storage.put(entity.getCode(), entity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean register(Inventory entity, Connection dataBase) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return register(entity);
    }

    @Override
    public Inventory find(String value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException {
        if (storage.containsKey(value)) {
            return storage.get(value);
        }
        return null;
    }

    @Override
    public Inventory find(String value, Connection dataBase) throws TableException, DAOException, MoreThanOneRegisterException, SQLException {
        return find(value);
    }

    @Override
    public void update(Inventory entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        if (storage.containsKey(entity.getCode())) {
            storage.put(entity.getCode(), entity);
        }
    }

    @Override
    public void update(Inventory entity, Connection dataBase) throws DAOException {
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
    public Collection<Inventory> findAll() throws DAOException, SQLException {
        return storage.values();
    }

    @Override
    public Inventory findByProduct(Long productId) throws DAOException, SQLException {
        Optional<Inventory> found = storage.values().stream()
                .filter(inv -> inv.getProduct().getId().equals(productId))
                .findFirst();

        return found.orElse(null);
    }
}
