package br.com.fluz.Product;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import br.com.fluz.Product.domain.Product;
import br.com.fluz.Product.repository.ProductRepository;

@DataMongoTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;

	private Product product;

	@BeforeAll
	void setUp() {
		product = Product.builder()
				.code("P1")
				.name("Vassoura de Aco")
				.valor(BigDecimal.TEN)
				.build();
	}

	@AfterAll
	void end() {
		repository.deleteAll();
	}

	@Test
	void insertProduct() {
		Product result = repository.save(product);
		
		assertThat(result).isNotNull();
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("Vassoura de Aco");
	}
	
	@Test
	void findProduct() {
		repository.save(product);
		
		Optional<Product> busca = repository.findById(product.getId());
		
		assertThat(busca).isPresent();
		assertThat(busca.get().getId()).isNotNull();
		assertThat(busca.get().getName()).isEqualTo("Vassoura de Aco");
	}
	
	@Test
	void updateProduct() {
		repository.save(product);
		
		product.setName("Avaiana de Pau");
		
		repository.save(product);
		
		Optional<Product> busca = repository.findById(product.getId());
		
		assertThat(busca).isPresent();
		assertThat(busca.get().getId()).isNotNull();
		assertThat(busca.get().getName()).isEqualTo("Avaiana de Pau");
	}
	
	@Test
	void removeProduct() {
		Product result = repository.save(product);
		
		repository.deleteById(result.getId());
		
		Optional<Product> busca = repository.findById(result.getId());
		
		assertThat(busca).isEmpty();
	}
	
	@Test
	void findAllProduct() {
		repository.save(product);
		
		Product newProduct = Product.builder()
				.code("P2")
				.name("Produto fake")
				.valor(BigDecimal.TEN)
				.build();
		
		repository.save(newProduct);
		
		List<Product> list = repository.findAll();
		
		assertThat(list).hasSize(2);
	}

}
