package br.com.feluz.dao.generics;

import br.com.feluz.dao.interfaces.Persistence;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface IGenericDAO <T extends Persistence, V extends Serializable> {
    Boolean register(T entity) throws DAOException, SQLException, TipoChaveNaoEncontradaException;
    Boolean register(T entity, Connection dataBase) throws TipoChaveNaoEncontradaException, DAOException, SQLException;

    T find(V value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException;
    T find(V value, Connection dataBase) throws TableException, DAOException, MoreThanOneRegisterException, SQLException;

    void update(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException;
    void update(T entity, Connection dataBase) throws DAOException;

    void remove(V value) throws DAOException, SQLException;
    void remove(V value, Connection dataBase) throws DAOException;

    Collection<T> findAll() throws DAOException, SQLException;
}
