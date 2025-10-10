import dao.ClienteDao;
import dao.mocks.ClienteDaoMock;
import dao.IClienteDao;
import org.junit.Assert;
import org.junit.Test;
import service.ClienteService;

public class ClienteServiceTest {

    @Test
    public void salvarTest() {
        IClienteDao mock = new ClienteDaoMock();
        ClienteService service = new ClienteService(mock);
        String retorno = service.salvar();
        Assert.assertEquals("Sucesso", retorno);
    }

    @Test (expected = UnsupportedOperationException.class)
    public void esperadoErroNoSalvarTest() {
        IClienteDao cliente = new ClienteDao();
        ClienteService service = new ClienteService(cliente);
        String retorno = service.salvar();
        Assert.assertEquals("Sucesso", retorno);
    }
}
