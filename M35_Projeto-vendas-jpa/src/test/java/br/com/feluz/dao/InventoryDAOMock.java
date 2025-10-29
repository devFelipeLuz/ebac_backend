package br.com.feluz.dao;




import br.com.feluz.jpa.Inventory;
import br.com.feluz.jpa.dao.IInventoryDAO;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryDAOMock implements IInventoryDAO {

    private static Map<String, Inventory> storage = new HashMap<>();


    @Override
    public Inventory save(Inventory entity, EntityManager em) {
        return null;
    }

    @Override
    public Inventory find(Long id, EntityManager em) {
        return null;
    }

    @Override
    public Inventory save(Inventory entity) {
        return null;
    }

    @Override
    public Inventory find(Long id) {
        return null;
    }

    @Override
    public void remove(Inventory entity) {

    }

    @Override
    public List<Inventory> findAll() {
        return List.of();
    }
}
