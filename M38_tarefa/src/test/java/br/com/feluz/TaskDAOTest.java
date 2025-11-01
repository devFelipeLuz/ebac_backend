package br.com.feluz;


import br.com.feluz.dao.ITaskDAO;
import br.com.feluz.dao.TaskDAO;
import br.com.feluz.domain.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TaskDAOTest {
    ITaskDAO taskDAO;
    Task task;

    public TaskDAOTest() {
        taskDAO = new TaskDAO();
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
        Task retorno = taskDAO.create(task);
        Assert.assertNotNull(retorno);
        Assert.assertEquals(task.getId(), retorno.getId());
    }

    @Test
    public void find() {
        taskDAO.create(task);

        Task busca = taskDAO.find(task.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(task.getId(), busca.getId());
    }

    @Test
    public void update() {
        taskDAO.create(task);

        task.setName("Ir ao mercado");
        taskDAO.update(task);

        Task busca = taskDAO.find(task.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(task.getName(), busca.getName());
    }

    @Test
    public void remove() {
        Task retorno = taskDAO.create(task);

        taskDAO.remove(retorno);

        Task busca = taskDAO.find(retorno.getId());
        Assert.assertNull(busca);
    }

    @Test
    public void findAll() {
        taskDAO.create(task);
        Task newTask = new Task();
        newTask.setName("Varrer o chão");
        newTask.setDescription("Varrer o chão");
        newTask.setStatus(Task.Status.CONCLUIDO);
        taskDAO.create(newTask);

        List<Task> list = taskDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void pendingTasks() {
        taskDAO.create(task);
        Task newTask = new Task();
        newTask.setName("Varrer o chão");
        newTask.setDescription("Varrer o chão");
        newTask.setStatus(Task.Status.PENDENTE);
        taskDAO.create(newTask);

        List<Task> list = taskDAO.pendingTasks();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void completedTasks() {
        taskDAO.create(task);
        Task newTask = new Task();
        newTask.setName("Varrer o chão");
        newTask.setDescription("Varrer o chão");
        newTask.setStatus(Task.Status.CONCLUIDO);
        taskDAO.create(newTask);

        Task otherNewTask = new Task();
        otherNewTask.setName("Lavar o carro");
        otherNewTask.setDescription("Lavar o carro");
        otherNewTask.setStatus(Task.Status.CONCLUIDO);
        taskDAO.create(otherNewTask);

        List<Task> list = taskDAO.completedTasks();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }
}
