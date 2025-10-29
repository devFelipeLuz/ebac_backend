package br.com.feluz.dao;


import br.com.feluz.jpa.Product;
import br.com.feluz.jpa.dao.IProductDAO;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAOMock implements IProductDAO {

    private static Map<String, Product> storage = new HashMap<>();


    @Override
    public Product save(Product entity) {
        return null;
    }

    @Override
    public Product find(Long id) {
        return null;
    }

    @Override
    public void remove(Product entity) {

    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Product save(Product product, EntityManager em) {
        return null;
    }
}
