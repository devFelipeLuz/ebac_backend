package br.com.feluz;

import br.com.feluz.domain.Task;
import br.com.feluz.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableJpaRepositories(basePackages = "br.com.feluz.repository")
@EntityScan("br.com.feluz.*")
@ComponentScan(basePackages = "br.com.feluz")
public class TaskProjectApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TaskProjectApplication.class);

    @Autowired
    private TaskRepository repository;


    public static void main(String[] args) {
        SpringApplication.run(TaskProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("StartApplication...");
        Task task = createTask();
        repository.save(task);
    }

    private Task createTask() {
        return Task.builder()
        		.name("Tarefa fake")
        		.description("Tarefa fake")
        		.status(Task.Status.PENDENTE)
        		.build();
    }

}
