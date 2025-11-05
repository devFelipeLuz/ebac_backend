package br.com.fluz.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import br.com.fluz.Customer.domain.Customer;
import br.com.fluz.Customer.repository.CustomerRepository;
import br.com.fluz.Customer.service.CustomerCommandService;
import br.com.fluz.Customer.service.CustomerQueryService;

@SpringBootTest
public class CustomerServiceTest {
	
	@Autowired
	private CustomerQueryService queryService;
	
	@Autowired
	private CustomerCommandService commandService;
	
	@MockBean
	private CustomerRepository repository;
	
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
	void registerCustomerService() {
		when(repository.save(customer)).thenReturn(customer);
		
		Customer result = commandService.register(customer);
		
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("Teste da Silva");
		
		verify(repository).save(customer);
	}
	
	@Test
	void findCustomerService() throws Exception {
		when(repository.findById("1")).thenReturn(Optional.of(customer));
		
		Optional<Customer> result = queryService.findById("1");
		
		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Teste da Silva");
		
		verify(repository).findById("1");
	}
	
	@Test
	void updateCustomerService() {
		when(repository.save(customer)).thenReturn(customer);
		
		customer.setName("Outro nome fake");
		
		Customer result = commandService.update(customer);
		
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("Outro nome fake");
		
		verify(repository).save(customer);
	}
	
	@Test
	void removeCustomerService() {
		commandService.remove("1");
		
		verify(repository).deleteById("1");
	}
	
	@Test
	void findAllCustomerService() {
		Pageable pageable = Pageable.unpaged();
		
		List<Customer> customerList = List.of(customer);
		
		Page<Customer> customerPage = new PageImpl<>(customerList, pageable, customerList.size());
		
		when(repository.findAll(pageable)).thenReturn(customerPage);
		
		Page<Customer> result = queryService.findAll(pageable);
		
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Teste da Silva");
        
        verify(repository).findAll(pageable);
	}
}
