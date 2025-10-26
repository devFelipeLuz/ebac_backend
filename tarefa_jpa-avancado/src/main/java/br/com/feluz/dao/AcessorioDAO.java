package br.com.feluz.dao;

import br.com.feluz.Acessorio;
import br.com.feluz.Carro;
import br.com.feluz.dao.interfaces.IAcessorioDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.util.List;

public class AcessorioDAO implements IAcessorioDAO {


    @Override
    public Boolean register(Acessorio acessorio) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(acessorio);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

        return true;
    }

    @Override
    public Acessorio find(Long id) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        /*CriteriaBuilder builder =
                entityManager.getCriteriaBuilder();
        CriteriaQuery<Acessorio> query =
                builder.createQuery(Acessorio.class);
        Root<Acessorio> root = query.from(Acessorio.class);
        query.select(root).where(builder.equal(root.get("id"), id));

        TypedQuery<Acessorio> tpQuery =
                entityManager.createQuery(query);

        Acessorio acessorio = tpQuery.getSingleResult();*/

        Acessorio acessorio = entityManager.find(Acessorio.class, id);

        entityManager.close();
        entityManagerFactory.close();

        return acessorio;
    }

    @Override
    public void update(Acessorio acessorio) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        acessorio = entityManager.merge(acessorio);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public void remove(Acessorio acessorio) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        acessorio = entityManager.merge(acessorio);
        entityManager.remove(acessorio);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public List<Acessorio> findAll() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Acessorio> query = builder.createQuery(Acessorio.class);
        Root<Acessorio> root = query.from(Acessorio.class);

        TypedQuery<Acessorio> tpQuery = entityManager.createQuery(query);
        List<Acessorio> list = tpQuery.getResultList();

        entityManager.close();
        entityManagerFactory.close();

        return list;
    }
}
