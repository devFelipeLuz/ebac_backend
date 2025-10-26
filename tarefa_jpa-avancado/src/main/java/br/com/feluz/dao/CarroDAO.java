package br.com.feluz.dao;

import br.com.feluz.Carro;
import br.com.feluz.dao.interfaces.ICarroDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CarroDAO implements ICarroDAO {


    @Override
    public Boolean register(Carro carro) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(carro);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

        return true;
    }

    @Override
    public Carro find(Long id) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();
        /*
        CriteriaBuilder builder =
                entityManager.getCriteriaBuilder();
        CriteriaQuery<Carro> query =
                builder.createQuery(Carro.class);
        Root<Carro> root = query.from(Carro.class);
        query.select(root).where(builder.equal(root.get("id"), id));

        TypedQuery<Carro> tpQuery =
                entityManager.createQuery(query);

        Carro carro = tpQuery.getSingleResult(); */

        Carro carro = entityManager.find(Carro.class, id);

        entityManager.close();
        entityManagerFactory.close();

        return carro;
    }

    @Override
    public void update(Carro carro) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        carro = entityManager.merge(carro);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public void remove(Carro carro) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        carro = entityManager.merge(carro);
        entityManager.remove(carro);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public List<Carro> findAll() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Carro> query = builder.createQuery(Carro.class);
        Root<Carro> root = query.from(Carro.class);

        TypedQuery<Carro> tpQuery = entityManager.createQuery(query);
        List<Carro> list = tpQuery.getResultList();

        entityManager.close();
        entityManagerFactory.close();

        return list;
    }
}
