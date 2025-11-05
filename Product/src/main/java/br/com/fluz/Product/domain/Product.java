package br.com.fluz.Product.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import lombok.*;

@Document(collection = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	
	@Id
	private String id;
	
	@NotNull
	@Size(min = 1, max = 50)
	private String code;
	
	@NotNull
	@Size(min = 1, max = 100)
	private String name;
	
	@NotNull
	private BigDecimal valor;
}
