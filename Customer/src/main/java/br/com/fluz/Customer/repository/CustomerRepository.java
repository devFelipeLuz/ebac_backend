package br.com.fluz.Customer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fluz.Customer.domain.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

	Customer findByCpf(Long cpf);
	
	Customer findByName(String name);
	
}
