package br.com.feluz;

import br.com.feluz.dao.ITaskDAO;
import br.com.feluz.dao.TaskDAO;
import br.com.feluz.domain.Task;
import br.com.feluz.service.ITaskService;
import br.com.feluz.service.TaskService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TaskServiceTest {
    private ITaskDAO taskDAO;
    private ITaskService taskService;
    private Task task;

    public TaskServiceTest() {
        taskDAO = new TaskDAO();
        taskService = new TaskService(taskDAO);
    }

    @Before
    public void setUp() {
        task = new Task();
        task.setName("Lavar o banheiro");
        task.setDescription("Lavar o banheiro");
        task.setStatus(Task.Status.PENDENTE);
    }

    @After
    public void end() {
        List<Task> taskList = taskDAO.findAll();
        taskList.forEach(taskDAO::remove);
    }

    @Test
    public void create() {
        Task retorno = taskService.create(task);
        Assert.assertNotNull(retorno);
        Assert.assertEquals(task.getId(), retorno.getId());
    }

    @Test
    public void find() {
        taskService.create(task);

        Task busca = taskService.find(task.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(task.getId(), busca.getId());
    }

    @Test
    public void update() {
        taskService.create(task);

        task.setName("Limpar a casa");
        taskService.update(task);

        Task busca = taskService.find(task.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(task.getName(), busca.getName());
    }

    @Test
    public void remove() {
        Task retorno = taskService.create(task);

        taskService.remove(retorno);

        Task busca = taskService.find(retorno.getId());
        Assert.assertNull(busca);
    }

    @Test
    public void findAll() {
        taskService.create(task);
        Task newTask = new Task();
        newTask.setName("Varrer o chão");
        newTask.setDescription("Varrer o chão");
        newTask.setStatus(Task.Status.CONCLUIDO);
        taskService.create(newTask);

        List<Task> list = taskService.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void pendingTasks() {
        taskService.create(task);
        Task newTask = new Task();
        newTask.setName("Varrer o chão");
        newTask.setDescription("Varrer o chão");
        newTask.setStatus(Task.Status.PENDENTE);
        taskService.create(newTask);

        List<Task> list = taskService.pendingTasks();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void completedTasks() {
        taskService.create(task);
        Task newTask = new Task();
        newTask.setName("Varrer o chão");
        newTask.setDescription("Varrer o chão");
        newTask.setStatus(Task.Status.CONCLUIDO);
        taskService.create(newTask);

        Task otherNewTask = new Task();
        otherNewTask.setName("Lavar o carro");
        otherNewTask.setDescription("Lavar o carro");
        otherNewTask.setStatus(Task.Status.CONCLUIDO);
        taskService.create(otherNewTask);

        List<Task> list = taskService.completedTasks();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void finalizeTask() {
        Task retorno = taskService.create(task);

        taskService.finalizeTask(task);

        Task busca = taskService.find(retorno.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(Task.Status.CONCLUIDO, task.getStatus());
    }
}
