package br.com.feluz.jpa.service.generic;

import br.com.feluz.jpa.dao.PersistenceJpa;
import br.com.feluz.jpa.dao.generic.IGenericJpaDAO;

import java.io.Serializable;
import java.util.List;

public class GenericServiceJpa<T extends PersistenceJpa, V extends Serializable> implements IGenericServiceJpa<T, V> {
    protected IGenericJpaDAO<T, V> dao;

    public GenericServiceJpa(IGenericJpaDAO<T, V> dao) {
        this.dao = dao;
    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public T find(T entity) {
        return null;
    }

    @Override
    public void remove(T entity) {

    }

    @Override
    public List<T> findAll() {
        return List.of();
    }
}
