package br.com.feluz.jpa.dao.generic;

import br.com.feluz.jpa.dao.PersistenceJpa;

import java.io.Serializable;

public abstract class GenericJpaDB2DAO<T extends PersistenceJpa, V extends Serializable> extends GenericJpaDAO<T, V> {

    public GenericJpaDB2DAO(Class<T> enityClass) {
        super(enityClass, "Projeto4-pt2");
    }

}
