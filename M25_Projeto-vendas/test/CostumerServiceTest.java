import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import dao.CostumerDAOMock;
import br.com.feluz.dao.ICostumerDAO;
import br.com.feluz.domain.Costumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.com.feluz.services.CostumerService;
import br.com.feluz.services.ICostumerService;

public class CostumerServiceTest {
    private ICostumerService costumerService;
    private Costumer costumer;

    public CostumerServiceTest() {
        ICostumerDAO daoMock = new CostumerDAOMock();
        costumerService = new CostumerService(daoMock);
    }

    @Before
    public void init() {
        costumer = new Costumer();
        costumer.setName("Felipe");
        costumer.setCpf(12345678910L);
        costumer.setTel(15912345678L);
        costumer.setAddress("Av. Bobos");
        costumer.setHouseNumber(0);
        costumer.setCity("Sorocaba");
        costumer.setState("SP");
    }

    @Test
    public void searchCostumer() {
        Costumer findCostumer = costumerService.find(costumer.getCpf());
        Assert.assertNotNull(findCostumer);
    }

    @Test
    public void saveCostumerTest() throws TipoChaveNaoEncontradaException {
        Boolean retorno =  costumerService.register(costumer);
        Assert.assertTrue(retorno);
    }

    @Test
    public void removeCostumerTest() {
        costumerService.remove(costumer.getCpf());
    }

    @Test
    public void updateCostumerTest() throws TipoChaveNaoEncontradaException {
        costumer.setName("Matheus Teixeira");
        costumerService.update(costumer);

        Assert.assertEquals("Matheus Teixeira", costumer.getName());
    }
}
