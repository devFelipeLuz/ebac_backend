package br.com.feluz.services.generics;

import br.com.feluz.dao.Persistence;
import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public abstract class GenericService<T extends Persistence, E extends Serializable> implements IGenericService<T, E> {

    protected IGenericDAO<T,E> dao;

    public GenericService(IGenericDAO<T,E> dao) {
        this.dao = dao;
    }

    @Override
    public Boolean register(T entity) throws TipoChaveNaoEncontradaException {
        return this.dao.register(entity);
    }

    @Override
    public void remove(E valor) {
        this.dao.remove(valor);
    }

    @Override
    public void update(T entity) throws TipoChaveNaoEncontradaException {
        this.dao.update(entity);
    }

    @Override
    public T find(E valor) {
        return this.dao.find(valor);
    }

    @Override
    public Collection<T> findAll() {
        return this.dao.findAll();
    }
}
