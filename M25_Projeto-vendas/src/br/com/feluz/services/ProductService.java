package br.com.feluz.services;

import br.com.feluz.dao.IProductDAO;
import br.com.feluz.domain.Product;
import br.com.feluz.services.generics.GenericService;

public class ProductService extends GenericService<Product, String> implements IProductService {

    public ProductService(IProductDAO dao) {
        super(dao);
    }
}
