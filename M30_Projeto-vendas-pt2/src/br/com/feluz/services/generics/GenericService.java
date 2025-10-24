package br.com.feluz.services.generics;

import br.com.feluz.dao.interfaces.Persistence;
import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.exceptions.*; // Importar todas as exceções para o throws

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
    public T find(V valor) throws MoreThanOneRegisterException, TableException, DAOException, SQLException {
        return this.dao.find(valor);
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
    public Collection<T> findAll() throws DAOException, SQLException {
        return this.dao.findAll();
    }
}