
package br.com.fluz.Product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.feluz.Product.repository")
public class MongoConfig {

}
