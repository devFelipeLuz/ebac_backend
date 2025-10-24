import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import dao.CostumerDAOMock;
import br.com.feluz.dao.interfaces.ICostumerDAO;
import br.com.feluz.domain.Costumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.com.feluz.services.CostumerService;
import br.com.feluz.services.interfaces.ICostumerService;

import java.sql.SQLException;

public class CostumerServiceTest {
    private ICostumerService costumerService;
    private Costumer costumer;

    public CostumerServiceTest() {
        ICostumerDAO daoMock = new CostumerDAOMock();
        costumerService = new CostumerService(daoMock);
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

    @Test
    public void saveCostumerTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Boolean retorno =  costumerService.register(costumer);
        Assert.assertTrue(retorno);
    }

    @Test
    public void searchCostumer() throws DAOException, SQLException {
        Costumer findCostumer = costumerService.find(costumer.getCpf());
        Assert.assertNotNull(findCostumer);
    }

    @Test
    public void updateCostumerTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        costumer.setName("Matheus Teixeira");
        costumerService.update(costumer);

        Assert.assertEquals("Matheus Teixeira", costumer.getName());
    }

    @Test
    public void removeCostumerTest() throws DAOException, SQLException {
        costumerService.remove(costumer.getCpf());
    }
}
