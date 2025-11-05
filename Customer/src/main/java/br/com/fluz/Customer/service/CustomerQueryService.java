package br.com.fluz.Customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fluz.Customer.domain.Customer;
import br.com.fluz.Customer.repository.CustomerRepository;

/**
 * @author Felipe Luz
 */

@Service
public class CustomerQueryService {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerQueryService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Page<Customer> findAll(Pageable pageable) {
		return this.customerRepository.findAll(pageable);
	}

	public Optional<Customer> findById(String id) {
		return this.customerRepository.findById(id);
	}

	public Customer findByCpf(Long cpf) {
		return this.customerRepository.findByCpf(cpf);
	}

	public Customer findByName(String name) {
		return this.customerRepository.findByName(name);
	}
}
