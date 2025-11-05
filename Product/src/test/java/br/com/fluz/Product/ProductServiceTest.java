package br.com.fluz.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.fluz.Product.domain.Product;
import br.com.fluz.Product.repository.ProductRepository;
import br.com.fluz.Product.service.ProductCommandService;
import br.com.fluz.Product.service.ProductQueryService;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductQueryService queryService;
	
	@Autowired
	private ProductCommandService commandService;
	
	@MockBean
	private ProductRepository repository;
	
	Product product;
	
	@BeforeEach
	void setUp() {
		product = Product.builder()
				.code("P1")
				.name("Vassoura de Aco")
				.valor(BigDecimal.TEN)
				.build();
	}
	
	@Test
	void registerProductService() {
		when(repository.save(product)).thenReturn(product);
		
		Product result = commandService.register(product);
		
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("Vassoura de Aco");
		
		verify(repository.save(product));
	}
	
	@Test
	void findProductService() throws Exception {
		when(repository.findById("1")).thenReturn(Optional.of(product));
		
		Optional<Product> result = queryService.findById("1");
		
		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Vassoura de Aco");
		
		verify(repository).findById("1");
	}
	
	@Test
	void updateProductService() {
		when(repository.save(product)).thenReturn(product);
		
		product.setName("Produto fake");
		
		Product result = commandService.update(product);
		
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("Produto fake");
		
		verify(repository).save(product);
	}
	
	@Test
	void removeProductService() {
		commandService.remove("1");
		
		verify(repository).deleteById("1");
	}
	
	@Test
	void findAllProductService() {
		Pageable pageable = Pageable.unpaged();
		
		List<Product> customerList = List.of(product);
		
		Page<Product> customerPage = new PageImpl<>(customerList, pageable, customerList.size());
		
		when(repository.findAll(pageable)).thenReturn(customerPage);
		
		Page<Product> result = queryService.findAll(pageable);
		
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Vassoura de Aco");
        
        verify(repository).findAll(pageable);
	}
}
