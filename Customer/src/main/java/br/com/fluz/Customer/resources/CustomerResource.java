package br.com.fluz.Customer.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fluz.Customer.domain.Customer;
import br.com.fluz.Customer.service.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/customer")
public class CustomerResource {

	private CustomerQueryService queryService;

	private CustomerCommandService commandService;

	@Autowired
	public CustomerResource(CustomerQueryService queryService, CustomerCommandService commandService) {
		this.queryService = queryService;
		this.commandService = commandService;
	}

	@GetMapping
	public ResponseEntity<Page<Customer>> findAll(Pageable pageable) {
		return ResponseEntity.ok(queryService.findAll(pageable));
	}

	@GetMapping(params = "id")
	public ResponseEntity<Optional<Customer>> findById(@RequestParam String id) {
		return ResponseEntity.ok(queryService.findById(id));
	}

	@GetMapping(params = "cpf")
	public ResponseEntity<Customer> findByCpf(@RequestParam Long cpf) {
		return ResponseEntity.ok(queryService.findByCpf(cpf));
	}

	@GetMapping(params = "name")
	public ResponseEntity<Customer> findByName(@RequestParam String name) {
		return ResponseEntity.ok(queryService.findByName(name));
	}

	@PostMapping
	public ResponseEntity<Customer> register(@RequestBody @Valid Customer customer) {
		return ResponseEntity.ok(commandService.register(customer));
	}

	@PutMapping
	public ResponseEntity<Customer> update(@RequestBody @Valid Customer customer) {
		return ResponseEntity.ok(commandService.update(customer));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> remove(@PathVariable String id) {
		commandService.remove(id);
		return ResponseEntity.ok("Removido com sucesso!");
	}
}
