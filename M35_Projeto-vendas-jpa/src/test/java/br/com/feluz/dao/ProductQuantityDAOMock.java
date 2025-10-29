package br.com.feluz.dao;


import br.com.feluz.jpa.ProductQuantity;
import br.com.feluz.jpa.dao.IProductQuantityDAO;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductQuantityDAOMock implements IProductQuantityDAO {

    private static Map<Long, ProductQuantity> storage = new HashMap<>();


    @Override
    public List<ProductQuantity> findBySale(Long saleId, EntityManager em) {
        return List.of();
    }

    @Override
    public ProductQuantity save(ProductQuantity entity) {
        return null;
    }

    @Override
    public ProductQuantity find(Long id) {
        return null;
    }

    @Override
    public void remove(ProductQuantity entity) {

    }

    @Override
    public List<ProductQuantity> findAll() {
        return List.of();
    }
}
