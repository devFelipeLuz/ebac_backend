package br.com.feluz.services.generics;

import br.com.feluz.dao.interfaces.Persistence;
import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;

public abstract class GenericService<T extends Persistence, V extends Serializable> implements IGenericService<T, V> {

    protected IGenericDAO<T, V> dao;

    public GenericService(IGenericDAO<T, V> dao) {
        this.dao = dao;
    }

    @Override
    public Boolean register(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return this.dao.register(entity);
    }

    @Override
    public T find(V valor) throws DAOException, SQLException, TableException, MoreThanOneRegisterException {
        try {
            return this.dao.find(valor);
        } catch (MoreThanOneRegisterException | TableException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        this.dao.update(entity);
    }

    @Override
    public void remove(V valor) throws DAOException, SQLException {
        this.dao.remove(valor);
    }

    @Override
    public Collection<T> findAll() throws DAOException  {
        try {
            return this.dao.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
