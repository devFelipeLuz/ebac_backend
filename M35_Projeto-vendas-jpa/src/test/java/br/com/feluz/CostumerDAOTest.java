package br.com.feluz;


import br.com.feluz.jpa.Costumer;
import br.com.feluz.jpa.dao.CostumerDAO;
import br.com.feluz.jpa.dao.ICostumerDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class CostumerDAOTest {
    private ICostumerDAO costumerDAO;
    private Costumer costumer;


    public CostumerDAOTest() {
        costumerDAO = new CostumerDAO();
    }

    @Before
    public void setUp() {
        costumer = new Costumer();
        costumer.setName("Felipe");
        costumer.setCpf(12345678910L);
        costumer.setTel(15912345678L);
        costumer.setEmail("felipe@teste.com");
        costumer.setAddress("Av. Bobos");
        costumer.setHouseNumber(0);
        costumer.setCity("Sorocaba");
        costumer.setState("SP");
    }

    @After
    public void end() {
        List<Costumer> costumerList = costumerDAO.findAll();
        costumerList.forEach(costumerDAO::remove);
    }

    @Test
    public void save() {
        Costumer isRegistered = costumerDAO.save(costumer);
        Assert.assertNotNull(isRegistered);
        Assert.assertEquals(isRegistered.getId(), costumer.getId());
    }

    @Test
    public void find() {
        Costumer isRegistered = costumerDAO.save(costumer);

        Costumer busca = costumerDAO.find(isRegistered.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(costumer.getId(), busca.getId());
    }

    @Test
    public void update() {
        Costumer isRegistered = costumerDAO.save(costumer);

        isRegistered.setName("Outro nome");
        costumerDAO.save(isRegistered);

        Costumer busca = costumerDAO.find(isRegistered.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(busca.getName(), costumer.getName());
    }

    @Test
    public void remove() {
        Costumer isRegistered = costumerDAO.save(costumer);

        costumerDAO.remove(isRegistered);

        Costumer busca = costumerDAO.find(isRegistered.getId());
        Assert.assertNull(busca);
    }

    @Test
    public void findAll() {
        costumerDAO.save(costumer);
        Costumer newCostumer = new Costumer();
        newCostumer.setName("Nome fake");
        newCostumer.setCpf(11122233344L);
        newCostumer.setTel(21911112222L);
        newCostumer.setEmail("fake@teste.com");
        newCostumer.setAddress("Av. Lagoinha da Nazaré Tedesco");
        newCostumer.setHouseNumber(1);
        newCostumer.setCity("São Paulo");
        newCostumer.setState("SP");
        costumerDAO.save(newCostumer);

        List<Costumer> list = costumerDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }
}
