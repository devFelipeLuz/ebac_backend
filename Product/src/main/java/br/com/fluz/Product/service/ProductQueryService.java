package br.com.fluz.Product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fluz.Product.domain.Product;
import br.com.fluz.Product.repository.ProductRepository;

@Service
public class ProductQueryService {

	private ProductRepository productRepository;
	
	@Autowired
	public ProductQueryService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
	
	public Optional<Product> findById(String id) {
		return productRepository.findById(id);
	}
	
	public Product findByCode(String code) {
		return productRepository.findByCode(code);
	}
}
