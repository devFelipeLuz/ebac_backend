package dao;

import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.domain.Inventory;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.SQLException;
import java.util.Collection;

public class InventoryDAOMock implements IInventoryDAO {

    @Override
    public Boolean register(Inventory entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return true;
    }

    @Override
    public Inventory find(String value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException {
        Inventory inventory = new Inventory();
        inventory.setCode(value);
        return inventory;
    }

    @Override
    public void update(Inventory entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {

    }

    @Override
    public void remove(String value) throws DAOException, SQLException {

    }

    @Override
    public Collection<Inventory> findAll() throws DAOException, SQLException {
        return null;
    }
}
