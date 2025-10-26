package br.com.feluz.dao;

import br.com.feluz.Carro;
import br.com.feluz.Marca;
import br.com.feluz.dao.interfaces.IMarcaDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class MarcaDAO implements IMarcaDAO {

    @Override
    public Boolean register(Marca marca) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(marca);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

        return true;
    }

    @Override
    public Marca find(Long id) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        /*CriteriaBuilder builder =
                entityManager.getCriteriaBuilder();
        CriteriaQuery<Marca> query =
                builder.createQuery(Marca.class);
        Root<Marca> root = query.from(Marca.class);
        query.select(root).where(builder.equal(root.get("id"), id));

        TypedQuery<Marca> tpQuery =
                entityManager.createQuery(query);

        Marca marca = tpQuery.getSingleResult();*/

        Marca marca = entityManager.find(Marca.class, id);

        entityManager.close();
        entityManagerFactory.close();

        return marca;
    }

    @Override
    public void update(Marca marca) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        marca = entityManager.merge(marca);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public void remove(Marca marca) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        marca = entityManager.merge(marca);
        entityManager.remove(marca);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public List<Marca> findAll() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TarefaJPAAvancado");
        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Marca> query = builder.createQuery(Marca.class);
        Root<Marca> root = query.from(Marca.class);

        TypedQuery<Marca> tpQuery = entityManager.createQuery(query);
        List<Marca> list = tpQuery.getResultList();

        entityManager.close();
        entityManagerFactory.close();

        return list;
    }
}
