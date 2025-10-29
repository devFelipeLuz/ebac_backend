package br.com.feluz.jpa.service.generic;

import br.com.feluz.jpa.dao.PersistenceJpa;

import java.io.Serializable;
import java.util.List;

public interface IGenericServiceJpa<T extends PersistenceJpa, V extends Serializable> {

    T save(T entity);

    T find(T entity);

    void remove(T entity);

    List<T> findAll();

}
