package br.com.fluz.Customer.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import lombok.*;

@Document(collection = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

	@Id
	private String id;

	@NotNull
	@Size(min = 1, max = 50)
	private String name;

	@NotNull
	@Indexed(unique = true, background = true)
	private Long cpf;

	@NotNull
	private Long tel;
}
