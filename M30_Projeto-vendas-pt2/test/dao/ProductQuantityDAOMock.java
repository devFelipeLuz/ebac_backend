package dao;

import br.com.feluz.dao.interfaces.IProductQuantityDAO;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductQuantityDAOMock implements IProductQuantityDAO {

    private static Map<Long, ProductQuantity> storage = new HashMap<>();

    @Override
    public Boolean register(ProductQuantity entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        if (!storage.containsKey(entity.getId())) {
            storage.put(entity.getId(), entity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean register(ProductQuantity entity, Connection dataBase) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return register(entity);
    }

    @Override
    public ProductQuantity find(Long value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException {
        if (storage.containsKey(value)) {
            return storage.get(value);
        }
        return null;
    }

    @Override
    public ProductQuantity find(Long value, Connection dataBase) throws TableException, DAOException, MoreThanOneRegisterException, SQLException {
        return find(value);
    }

    @Override
    public void update(ProductQuantity entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        if (storage.containsKey(entity.getId())) {
            storage.put(entity.getId(), entity);
        }
    }

    @Override
    public void update(ProductQuantity entity, Connection dataBase) throws DAOException {
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
    public Collection<ProductQuantity> findAll() throws DAOException, SQLException {
        return storage.values();
    }

    @Override
    public List<ProductQuantity> findBySale(Long saleId) throws SQLException, DAOException {
        Collection<ProductQuantity> allItens = storage.values();
        List<ProductQuantity> foundItems = allItens.stream()
                .filter(item -> item.getSale() != null && saleId.equals(item.getSale().getId()))
                .collect(Collectors.toList());

        return foundItems;
    }
}
