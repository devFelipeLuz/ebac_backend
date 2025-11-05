package br.com.fluz.Customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import br.com.fluz.Customer.domain.Customer;
import br.com.fluz.Customer.repository.CustomerRepository;

@DataMongoTest
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository repository;

	private Customer customer;

	@BeforeAll
	void setUp() {
		customer = Customer.builder()
				.name("Teste da Silva")
				.cpf(12345678910L)
				.tel(15911112222L)
				.build();
	}

	@BeforeEach
	void end() {
		repository.deleteAll();
	}

	@Test
	void insertCustomer() {
		Customer result = repository.save(customer);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("Teste da Silva");
	}

	@Test
	void findCustomer() {
		repository.save(customer);

		Optional<Customer> busca = repository.findById(customer.getId());

		assertThat(busca.isPresent());
		assertThat(busca.get().getId()).isNotNull();
		assertThat(busca.get().getName()).isEqualTo("Teste da Silva");
	}

	@Test
	void updateCustomer() {
		repository.save(customer);

		customer.setName("Outro nome fake");

		repository.save(customer);

		Optional<Customer> busca = repository.findById(customer.getId());

		assertThat(busca.isPresent());
		assertThat(busca.get().getId()).isNotNull();
		assertThat(busca.get().getName()).isEqualTo("Outro nome fake");
	}

	@Test
	void removeCustomer() {
		Customer retorno = repository.save(customer);

		repository.deleteById(retorno.getId());

		Optional<Customer> busca = repository.findById(retorno.getId());

		assertThat(busca).isEmpty();
	}

	@Test
	void findAllCustomer() {
		repository.save(customer);

		Customer newCustomer = Customer.builder()
				.name("Barney Rubble")
				.cpf(11122233344L)
				.tel(21922223333L)
				.build();

		repository.save(newCustomer);

		List<Customer> list = repository.findAll();

		assertThat(list).isNotNull();
		assertThat(list).hasSize(2);
	}
}
