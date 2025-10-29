package br.com.feluz.jpa.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionDB {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("Projeto4");

    public static EntityManager getConnection() {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeConnection() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
