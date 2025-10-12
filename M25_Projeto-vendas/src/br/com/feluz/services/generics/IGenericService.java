package br.com.feluz.services.generics;

import br.com.feluz.dao.Persistence;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericService<T extends Persistence, E extends Serializable> {

    Boolean register(T entity) throws TipoChaveNaoEncontradaException;

    T find(E valor);

    void remove(E valor);

    void update(T entity) throws TipoChaveNaoEncontradaException;

    Collection<T> findAll();
}
