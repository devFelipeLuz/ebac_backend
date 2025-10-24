package br.com.feluz.services;

import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.services.generics.GenericService;
import br.com.feluz.services.interfaces.IProductQuantityService;

public class ProductQuantityService extends GenericService<ProductQuantity, Long> implements IProductQuantityService {

    public ProductQuantityService(IGenericDAO<ProductQuantity, Long> dao) {
        super(dao);
    }
}
