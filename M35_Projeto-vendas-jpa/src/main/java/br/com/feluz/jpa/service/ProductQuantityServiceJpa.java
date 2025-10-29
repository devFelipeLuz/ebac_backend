package br.com.feluz.jpa.service;

import br.com.feluz.jpa.ProductQuantity;
import br.com.feluz.jpa.dao.IProductQuantityDAO;
import br.com.feluz.jpa.service.generic.GenericServiceJpa;

public class ProductQuantityServiceJpa extends GenericServiceJpa<ProductQuantity, Long> implements IProductQuantityServiceJpa {

    public ProductQuantityServiceJpa(IProductQuantityDAO prodQDAO) {
        super(prodQDAO);
    }

}
