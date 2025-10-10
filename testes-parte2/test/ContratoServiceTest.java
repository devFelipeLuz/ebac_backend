import dao.ContratoDao;
import dao.IContratoDao;
import dao.mocks.ContratoDaoMock;
import org.junit.Assert;
import org.junit.Test;
import service.ContratoService;
import service.IContratoService;

public class ContratoServiceTest {

    @Test
    public void salvarTest() {
        IContratoDao daoMock = new ContratoDaoMock();
        IContratoService service = new ContratoService(daoMock);
        String retorno = service.salvar();
        Assert.assertEquals("Sucesso", retorno);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void esperadoErroNoSalvarComBancoDeDadosTest() {
        IContratoDao dao = new ContratoDao();
        IContratoService service = new ContratoService(dao);
        String retorno = service.salvar();
        Assert.assertEquals("Sucesso", retorno);
    }

    @Test
    public void buscarTest() {
        IContratoDao daoMock = new ContratoDaoMock();
        IContratoService service = new ContratoService(daoMock);
        String retorno = service.buscar();
        Assert.assertEquals("Encontrado", retorno);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void esperadoErroNoBuscarNoBancoDeDadosTest() {
        IContratoDao dao = new ContratoDao();
        IContratoService service = new ContratoService(dao);
        String retorno = service.buscar();
        Assert.assertEquals("Encontrado", retorno);
    }

    @Test
    public void excluirTest() {
        IContratoDao daoMock = new ContratoDaoMock();
        IContratoService service = new ContratoService(daoMock);
        String retorno = service.excluir();
        Assert.assertEquals("Removido", retorno);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void esperadoErroNoExcluirNoBancoDeDadosTest() {
        IContratoDao dao = new ContratoDao();
        IContratoService service = new ContratoService(dao);
        String retorno = service.excluir();
        Assert.assertEquals("Removido", retorno);
    }

    @Test
    public void atualizarTest() {
        IContratoDao daoMock = new ContratoDaoMock();
        IContratoService service = new ContratoService(daoMock);
        String retorno = service.atualizar();
        Assert.assertEquals("Atualizado", retorno);
    };

    @Test(expected = UnsupportedOperationException.class)
    public void esperadoErroNoAtualizarNoBancoDeDadosTest() {
        IContratoDao dao = new ContratoDao();
        IContratoService service = new ContratoService(dao);
        String retorno = service.atualizar();
        Assert.assertEquals("Atualizado", retorno);
    };

    //TODO
    //Fazer métodos de buscar, excluir e atualizar
}