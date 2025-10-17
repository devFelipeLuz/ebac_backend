package br.com.feluz.dao.generic.jdbc.dao;

import java.util.List;

import br.com.feluz.domain.Product;

public interface IProductDAO {
	public Integer register(Product product) throws Exception;
    public Product find(String nome) throws Exception;
    public Integer update(Product product) throws Exception;
    public Integer remove(Product product) throws Exception;
    public List<Product> findAll() throws Exception;
}
