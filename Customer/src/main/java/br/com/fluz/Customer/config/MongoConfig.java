
package br.com.fluz.Customer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.feluz.Customer.repository")
public class MongoConfig {

}
