package br.com.feluz.service;

import br.com.feluz.domain.Task;
import br.com.feluz.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ObjectInputFilter.Status;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task save(Task entity) {
    	if (entity.getStatus() == null) {
    		entity.setStatus(Task.Status.PENDENTE);
    	}
    		
        return taskRepository.save(entity);
    }

    public Task find(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
