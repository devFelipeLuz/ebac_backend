package br.com.feluz.jpa.dao.generic;

import br.com.feluz.jpa.dao.PersistenceJpa;

import java.io.Serializable;
import java.util.List;

public interface IGenericJpaDAO<T extends PersistenceJpa, V extends Serializable> {

    public T save(T entity);

    public T find(V id);

    public void remove(T entity);

    public List<T> findAll();
}
