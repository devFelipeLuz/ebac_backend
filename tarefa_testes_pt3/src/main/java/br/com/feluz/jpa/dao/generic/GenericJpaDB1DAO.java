package br.com.feluz.jpa.dao.generic;

import br.com.feluz.jpa.dao.PersistenceJpa;

import java.io.Serializable;

public abstract class GenericJpaDB1DAO<T extends PersistenceJpa, V extends Serializable> extends GenericJpaDAO<T, V> {

    public GenericJpaDB1DAO(Class<T> entityClass) {

        super(entityClass, "Projeto4");

    }
}
