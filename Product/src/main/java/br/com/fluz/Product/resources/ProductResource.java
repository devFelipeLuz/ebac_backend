package br.com.fluz.Product.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fluz.Product.domain.Product;
import br.com.fluz.Product.service.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/product")
public class ProductResource {

	private ProductQueryService queryService;

	private ProductCommandService commandService;
	
	@Autowired
	public ProductResource(ProductQueryService queryService, ProductCommandService commandService) {
		this.queryService = queryService;
		this.commandService = commandService;
	}

	@GetMapping
	public ResponseEntity<Page<Product>> findAll(Pageable pageable) {
		return ResponseEntity.ok(queryService.findAll(pageable));
	}

	@GetMapping(params = "id")
	public ResponseEntity<Optional<Product>> findById(@RequestParam String id) {
		return ResponseEntity.ok(queryService.findById(id));
	}
	
	@GetMapping(params = "code")
	public ResponseEntity<Product> findByCode(@RequestParam String code) {
		return ResponseEntity.ok(queryService.findByCode(code));
	}
	
	@PostMapping
	public ResponseEntity<Product> register(@RequestBody @Valid Product product) {
		return ResponseEntity.ok(commandService.register(product));
	}
	
	@PutMapping
	public ResponseEntity<Product> update(@RequestBody @Valid Product product) {
		return ResponseEntity.ok(commandService.update(product));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> remove(@PathVariable String id) {
		commandService.remove(id);
		return ResponseEntity.ok("Removido com sucesso!");
	}
}
