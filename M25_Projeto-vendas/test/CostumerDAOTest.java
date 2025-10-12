import br.com.feluz.dao.CostumerDAO;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.dao.ICostumerDAO;
import br.com.feluz.domain.Costumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;


public class CostumerDAOTest {
    private ICostumerDAO costumerDAO;
    private Costumer costumer;

    public CostumerDAOTest() {
        costumerDAO = new CostumerDAO();
    }

    @Before
    public void init() throws TipoChaveNaoEncontradaException {
        costumer = new Costumer();
        costumer.setName("Felipe");
        costumer.setCpf(12345678910L);
        costumer.setTel(15912345678L);
        costumer.setAddress("Av. Bobos");
        costumer.setHouseNumber(0);
        costumer.setCity("Sorocaba");
        costumer.setState("SP");
        costumerDAO.register(costumer);
    }

    @Test
    public void searchCostumer() {
        Costumer findCostumer = costumerDAO.find(costumer.getCpf());
        Assert.assertNotNull(findCostumer);
    }

    @Test
    public void saveCostumerTest() throws TipoChaveNaoEncontradaException {
        costumer.setCpf(11122233344L);
        Boolean retorno = costumerDAO.register(costumer);
        Assert.assertTrue(retorno);
    }

    @Test
    public void removeCostumerTest() {
        costumerDAO.remove(costumer.getCpf());
    }

    @Test
    public void updateCostumerTest() throws TipoChaveNaoEncontradaException {
        costumer.setName("Matheus Teixeira");
        costumerDAO.update(costumer);
        Assert.assertEquals("Matheus Teixeira", costumer.getName());
    }

    @Test
    public void findAllTest() {
        Collection<Costumer> list = costumerDAO.findAll();
        Assert.assertTrue(list != null);
        Assert.assertTrue(list.size() == 2);
    }
}
