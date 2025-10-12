package dao;

import br.com.feluz.dao.IProductDAO;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import java.util.Collection;

public class ProductDAOMock implements IProductDAO {

    @Override
    public Boolean register(Product entity) throws TipoChaveNaoEncontradaException {
        return true;
    }

    @Override
    public void remove(String valor) {

    }

    @Override
    public void update(Product entity) throws TipoChaveNaoEncontradaException {

    }

    @Override
    public Product find(String valor) {
        Product product = new Product();
        product.setCodigo(valor);
        return product;
    }

    @Override
    public Collection<Product> findAll() {
        return null;
    }
}
