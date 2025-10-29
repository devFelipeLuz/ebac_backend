package br.com.feluz.dao;

import br.com.feluz.jpa.Costumer;
import br.com.feluz.jpa.dao.ICostumerDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostumerDAOMock implements ICostumerDAO {

    private static Map<Long, Costumer> storage = new HashMap<>();

    public CostumerDAOMock() {}


    @Override
    public Costumer save(Costumer entity) {
        return null;
    }

    @Override
    public Costumer find(Long id) {
        return null;
    }

    @Override
    public void remove(Costumer entity) {

    }

    @Override
    public List<Costumer> findAll() {
        return List.of();
    }
}
