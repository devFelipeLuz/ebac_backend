package br.com.fluz.Product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fluz.Product.domain.Product;
import br.com.fluz.Product.resources.ProductResource;
import br.com.fluz.Product.service.ProductCommandService;
import br.com.fluz.Product.service.ProductQueryService;

@WebMvcTest(ProductResource.class)
public class ProductResourceTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProductQueryService query;
	
	@MockBean
	private ProductCommandService command;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Product product;
	
	@BeforeEach
	void setUp() {
		product = Product.builder()
				.code("P1")
				.name("Vassoura de Aco")
				.valor(BigDecimal.TEN)
				.build();
	}
	
	@Test
	void registerProductResource() throws Exception {
		when(command.register(product)).thenReturn(product);
		
		mvc.perform(post("/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Vassoura de Aco"));
		
		verify(command).register(product);
	}
	
	@Test
	void findCustomerResource() throws Exception {
		when(query.findById("1")).thenReturn(Optional.of(product));
		
		mvc.perform(get("/product?id=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Vassoura de Aco"));
		
		verify(query).findById("1");
	}
	
	@Test
	void updateCustomerResource() throws Exception {
	    when(command.update(product)).thenReturn(product);

	    product.setName("Outro nome fake");

	    mvc.perform(put("/product")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(product)))
	        	.andExpect(status().isOk())
	        	.andExpect(jsonPath("$.name").value("Outro nome fake"));

	    verify(command).update(product);
	}
	
	@Test
	void removeCustomerResource() throws Exception {
		mvc.perform(delete("/product/1"))
				.andExpect(status().isOk());
		
		verify(command).remove("1");
	}
	
	@Test
	void findAllCustomerResource() throws Exception {
		Page<Product> page = new PageImpl<>(List.of(product), PageRequest.of(0, 10), 1);
		
		when(query.findAll(any(PageRequest.class))).thenReturn(page);
		
		mvc.perform(get("/customer?page=0&size=10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].name").value("Teste da Silva"));
		
		 verify(query).findAll(any(PageRequest.class));
	}
}
