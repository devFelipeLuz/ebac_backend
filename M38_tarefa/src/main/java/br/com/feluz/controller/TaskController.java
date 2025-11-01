package br.com.feluz.controller;

import br.com.feluz.domain.Task;
import br.com.feluz.service.ITaskService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("taskBean")
@SessionScoped
public class TaskController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ITaskService taskService;

    private List<Task> tasks;

    private Task currentTask = new Task();

    public TaskController() {
        this.currentTask.setStatus(Task.Status.PENDENTE);
    }

    public List<Task> getTasks() {
        if (tasks == null) {
            this.tasks = taskService.findAll();
        }
        return tasks;
    }

    public String refreshList() {
        this.tasks = taskService.findAll();
        return "tasks.xhtml?faces-redirect=true";
    }

    public String save() {
        if (currentTask.getId() == null) {
            taskService.create(currentTask);
        } else {
            taskService.update(currentTask);
        }

        currentTask = new Task();
        return refreshList();
    }

    public String finalizeTask(Task task) {
        taskService.finalizeTask(task);
        return refreshList();
    }

    public String remove(Task task) {
        taskService.remove(task);
        return refreshList();
    }

    public ITaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(ITaskService taskService) {
        this.taskService = taskService;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
}
