package br.com.feluz.services;

import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.domain.Product;
import br.com.feluz.services.generics.GenericService;
import br.com.feluz.services.interfaces.IProductService;

public class ProductService extends GenericService<Product, String> implements IProductService {

    public ProductService(IProductDAO dao) {
        super(dao);
    }
}
