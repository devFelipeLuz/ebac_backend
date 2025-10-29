package br.com.feluz.jpa.dao.generic;

import br.com.feluz.jpa.connection.ConnectionDB;
import br.com.feluz.jpa.dao.PersistenceJpa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class GenericJpaDAO<T extends PersistenceJpa, V extends Serializable> implements IGenericJpaDAO<T, V> {
    private final Class<T> entityClass;

    public GenericJpaDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        EntityManager em = ConnectionDB.getConnection();
        try {
            em.getTransaction().begin();

            if (entity.getId() == null) {
                em.persist(entity);
            } else {
                entity = em.merge(entity);
            }
            em.getTransaction().commit();
            return entity;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public T find(V id) {
        EntityManager em = ConnectionDB.getConnection();
        try {
            T entity = em.find(entityClass, id);
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(T entity) {
        EntityManager em = ConnectionDB.getConnection();
        try {
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = ConnectionDB.getConnection();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(entityClass);
            Root<T> root = query.from(entityClass);
            query.select(root);

            TypedQuery<T> tpQuery = em.createQuery(query);
            return tpQuery.getResultList();
        } finally {
            em.close();
        }
    }
}
