import br.com.feluz.dao.CostumerDAO;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.dao.interfaces.ICostumerDAO;
import br.com.feluz.domain.Costumer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collection;


public class CostumerDAOTest {
    private ICostumerDAO costumerDAO;
    private Costumer costumer;


    public CostumerDAOTest() {
        costumerDAO = new CostumerDAO();
    }

    @Before
    public void setUp() throws DAOException, TipoChaveNaoEncontradaException {
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
    public void removeCostumerMock() throws DAOException, SQLException {
        if (costumer != null) {
            try {
                costumerDAO.remove(costumer.getCpf());
            } catch (Exception ignored) {

            }
        }
    }

    @Test
    public void saveCostumerTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Boolean retorno = costumerDAO.register(costumer);
        Assert.assertTrue(retorno);
    }

    @Test
    public void searchCostumerTest() throws MoreThanOneRegisterException, TableException, DAOException, TipoChaveNaoEncontradaException, SQLException {
        costumerDAO.register(costumer);
        Costumer findCostumer = costumerDAO.find(costumer.getCpf());
        Assert.assertNotNull(findCostumer);
    }

    @Test
    public void updateCostumerTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        costumerDAO.register(costumer);
        costumer.setName("Matheus Teixeira");
        costumerDAO.update(costumer);
        Assert.assertEquals("Matheus Teixeira", costumer.getName());
    }

    @Test
    public void removeCostumerTest() throws DAOException, SQLException, TableException, MoreThanOneRegisterException, TipoChaveNaoEncontradaException {
        costumerDAO.register(costumer);
        costumerDAO.remove(costumer.getCpf());
        Costumer findCostumer = costumerDAO.find(costumer.getCpf());
        Assert.assertNull(findCostumer);
    }

    @Test
    public void findAllCostumerTest() throws DAOException, TipoChaveNaoEncontradaException, SQLException {
        Costumer costumer1 = new Costumer();
        costumer1.setName("Ricardo Juarez");
        costumer1.setCpf(11122233344L);
        costumer1.setTel(21911112222L);
        costumer1.setEmail("ricardo@teste.com");
        costumer1.setAddress("Av. Vai e Vem");
        costumer1.setHouseNumber(99);
        costumer1.setCity("Rio de Janeiro");
        costumer1.setState("RJ");

        Boolean retorno = costumerDAO.register(costumer);
        Assert.assertTrue(retorno);

        Boolean retorno1 = costumerDAO.register(costumer1);
        Assert.assertTrue(retorno1);

        Collection<Costumer> costumerList = costumerDAO.findAll();
        Assert.assertNotNull(costumerList);
        Assert.assertEquals(2, costumerList.size());

        costumerList.forEach(cos -> {
            try {
                costumerDAO.remove(cos.getCpf());
            } catch (DAOException | SQLException e) {
                e.printStackTrace();
            }
        });

        Collection<Costumer> costumerList1 = costumerDAO.findAll();
        Assert.assertNotNull(costumerList1);
        Assert.assertEquals(0, costumerList1.size());
    }
}
