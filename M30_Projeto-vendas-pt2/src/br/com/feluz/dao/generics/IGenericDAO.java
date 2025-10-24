package br.com.feluz.dao.generics;

import br.com.feluz.dao.interfaces.Persistence;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;

public interface IGenericDAO <T extends Persistence, V extends Serializable> {
    Boolean register(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException;
    T find(V value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException;
    void update(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException;
    void remove(V value) throws DAOException, SQLException;
    Collection<T> findAll() throws DAOException, SQLException;
}
