package br.com.feluz.dao;

import br.com.feluz.dao.generics.GenericDAO;
import br.com.feluz.domain.Product;

public class ProductDAO extends GenericDAO<Product, String> implements IProductDAO {

    public ProductDAO() {
        super();
    }

    @Override
    public Class<Product> getTipoClasse() {
        return Product.class;
    }

    @Override
    public void atualiarDados(Product entity, Product entityCadastrado) {
        entityCadastrado.setCodigo(entity.getCodigo());
        entityCadastrado.setDescricao(entity.getDescricao());
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setValor(entity.getValor());
    }
}
