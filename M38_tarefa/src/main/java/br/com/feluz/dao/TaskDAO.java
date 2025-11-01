package br.com.feluz.dao;

import br.com.feluz.connection.Connection;
import br.com.feluz.domain.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;


import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskDAO implements ITaskDAO {

    @Override
    public Task create(Task entity) {
        EntityManager em = Connection.getConnection();

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Task find(Long id) {
        try (EntityManager em = Connection.getConnection();) {
            return em.find(Task.class, id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Task update(Task entity) {
        EntityManager em = Connection.getConnection();

        try {
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(Task entity) {
        EntityManager em = Connection.getConnection();

        try {
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Task> findAll() {
        EntityManager em = Connection.getConnection();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery query = cb.createQuery(Task.class);
            Root<Task> root = query.from(Task.class);
            query.select(root);
            TypedQuery<Task> tpQuery = em.createQuery(query);
            return tpQuery.getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public List<Task> pendingTasks() {
        List<Task> list = findAll();
        return list.stream()
                .filter(t -> t.getStatus().equals(Task.Status.PENDENTE))
                .collect(Collectors.toList());

    }

    @Override
    public List<Task> completedTasks() {
        List<Task> list = findAll();
        return list.stream()
                .filter(t -> t.getStatus().equals(Task.Status.CONCLUIDO))
                .collect(Collectors.toList());

    }
}
