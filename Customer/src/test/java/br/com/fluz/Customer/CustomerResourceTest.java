package br.com.fluz.Customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.com.fluz.Customer.domain.Customer;
import br.com.fluz.Customer.resources.CustomerResource;
import br.com.fluz.Customer.service.CustomerCommandService;
import br.com.fluz.Customer.service.CustomerQueryService;

@WebMvcTest(CustomerResource.class)
public class CustomerResourceTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CustomerQueryService query;
	
	@MockBean
	private CustomerCommandService command;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Customer customer;
	
	
	@BeforeEach
	void setUp() {
		customer = Customer.builder()
                .name("Teste da Silva")
                .cpf(12345678910L)
                .tel(15911112222L)
                .build();
	}
	
	@Test
	void registerCustomerResource() throws Exception {
		when(command.register(customer)).thenReturn(customer);
		
		mvc.perform(post("/customer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Teste da Silva"));
		
		verify(command).register(customer);
	}
	
	@Test
	void findCustomerResource() throws Exception {
		when(query.findById("1")).thenReturn(Optional.of(customer));
		
		mvc.perform(get("/customer?id=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Teste da Silva"));
		
		verify(query).findById("1");
	}
	
	@Test
	void updateCustomerResource() throws Exception {
	    when(command.update(customer)).thenReturn(customer);

	    customer.setName("Outro nome fake");

	    mvc.perform(put("/customer")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(customer)))
	        	.andExpect(status().isOk())
	        	.andExpect(jsonPath("$.name").value("Outro nome fake"));

	    verify(command).update(customer);
	}
	
	@Test
	void removeCustomerResource() throws Exception {
		mvc.perform(delete("/customer/1"))
				.andExpect(status().isOk());
		
		verify(command).remove("1");
	}
	
	@Test
	void findAllCustomerResource() throws Exception {
		Page<Customer> page = new PageImpl<>(List.of(customer), PageRequest.of(0, 10), 1);
		
		when(query.findAll(any(PageRequest.class))).thenReturn(page);
		
		mvc.perform(get("/customer?page=0&size=10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].name").value("Teste da Silva"));
		
		 verify(query).findAll(any(PageRequest.class));
	}
}
