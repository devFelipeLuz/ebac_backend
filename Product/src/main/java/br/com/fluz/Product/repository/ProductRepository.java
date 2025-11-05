package br.com.fluz.Product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fluz.Product.domain.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
	
	Product findByCode(String code);
	
}
