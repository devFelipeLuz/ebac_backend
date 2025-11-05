package br.com.fluz.Customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fluz.Customer.domain.Customer;
import br.com.fluz.Customer.repository.CustomerRepository;

/**
 * @author Felipe Luz
 */

@Service
public class CustomerCommandService {

	private CustomerRepository customerRepository;
	
	@Autowired
	public CustomerCommandService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public Customer register(Customer customer) {
		return this.customerRepository.insert(customer);
	}
	
	public Customer update(Customer customer) {
		return this.customerRepository.save(customer);
	}
	
	public void remove(String id) {
		this.customerRepository.deleteById(id);
	}
	
}
