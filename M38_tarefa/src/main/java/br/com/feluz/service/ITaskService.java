package br.com.feluz.service;

import br.com.feluz.domain.Task;

import java.util.List;

public interface ITaskService {

    Task create(Task entity);

    Task find(Long id);

    Task update(Task entity);

    void remove(Task entity);

    List<Task> findAll();

    List<Task> pendingTasks();

    List<Task> completedTasks();

    void finalizeTask(Task entity);
}
