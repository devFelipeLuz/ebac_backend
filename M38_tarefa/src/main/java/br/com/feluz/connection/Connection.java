package br.com.feluz.connection;

import jakarta.persistence.*;

public class Connection {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("Tasks");

    public static EntityManager getConnection() {
        return entityManagerFactory.createEntityManager();
    }
}
