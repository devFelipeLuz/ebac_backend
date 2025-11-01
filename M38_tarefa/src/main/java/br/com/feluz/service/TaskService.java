package br.com.feluz.service;

import br.com.feluz.dao.ITaskDAO;
import br.com.feluz.domain.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TaskService implements ITaskService {

    ITaskDAO taskDAO;

    @Inject
    public TaskService(ITaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public Task create(Task entity) {
        return this.taskDAO.create(entity);
    }

    @Override
    public Task find(Long id) {
        return this.taskDAO.find(id);
    }

    @Override
    public Task update(Task entity) {
        return this.taskDAO.update(entity);
    }

    @Override
    public void remove(Task entity) {
        this.taskDAO.remove(entity);
    }

    @Override
    public List<Task> findAll() {
        return this.taskDAO.findAll();
    }

    @Override
    public List<Task> pendingTasks() {
        return this.taskDAO.pendingTasks();
    }

    @Override
    public List<Task> completedTasks() {
        return this.taskDAO.completedTasks();
    }

    @Override
    public void finalizeTask(Task entity) {
        entity.setStatus(Task.Status.CONCLUIDO);
        this.taskDAO.update(entity);
    }
}
