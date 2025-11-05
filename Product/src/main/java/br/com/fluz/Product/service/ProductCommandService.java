package br.com.fluz.Product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fluz.Product.domain.Product;
import br.com.fluz.Product.repository.ProductRepository;

@Service
public class ProductCommandService {

	private ProductRepository productRepository;
	
	@Autowired
	public ProductCommandService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public Product register(Product product) {
		return this.productRepository.insert(product);
	}
	
	public Product update(Product product) {
		return this.productRepository.save(product);
	}
	
	public void remove(String id) {
		this.productRepository.deleteById(id);
	}
	
}
