package br.com.feluz.dao;


import br.com.feluz.jpa.Sale;
import br.com.feluz.jpa.dao.ISaleDAO;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleDAOMock implements ISaleDAO {

    private static Map<String, Sale> storage = new HashMap<>();


    @Override
    public void finishSale(Sale sale, EntityManager em) {

    }

    @Override
    public void cancelSale(Sale sale, EntityManager em) {

    }

    @Override
    public Sale save(Sale entity) {
        return null;
    }

    @Override
    public Sale find(Long id) {
        return null;
    }

    @Override
    public void remove(Sale entity) {

    }

    @Override
    public List<Sale> findAll() {
        return List.of();
    }
}
