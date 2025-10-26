package br.com.feluz;

import br.com.feluz.dao.AcessorioDAO;
import br.com.feluz.dao.CarroDAO;
import br.com.feluz.dao.MarcaDAO;
import br.com.feluz.dao.interfaces.IAcessorioDAO;
import br.com.feluz.dao.interfaces.ICarroDAO;
import br.com.feluz.dao.interfaces.IMarcaDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AcessorioTest {
    private ICarroDAO carroDAO;
    private Carro carro;

    private IMarcaDAO marcaDAO;
    private Marca marca;

    private IAcessorioDAO acessorioDAO;
    private Acessorio acessorio;

    public AcessorioTest() {
        carroDAO = new CarroDAO();
        marcaDAO = new MarcaDAO();
        acessorioDAO = new AcessorioDAO();
    }

    @Before
    public void setUp() {
        marca = new Marca();
        marca.setNome("Mitsubishi");
        marca.adicionarCarro(carro);

        acessorio = new Acessorio();
        acessorio.adicionarCarro(carro);
        acessorio.setNome("Multimidia");

        carro = new Carro();
        carro.setModelo("Lancer Evo IX");
        carro.setMarca(marca);
        carro.adicionarAcessorio(acessorio);
    }

    @After
    public void end() {
        List<Carro> carroList = carroDAO.findAll();
        carroList.forEach(c -> carroDAO.remove(c));

        List<Marca> marcaList = marcaDAO.findAll();
        marcaList.forEach(m -> marcaDAO.remove(m));

        List<Acessorio> acessorioList = acessorioDAO.findAll();
        acessorioList.forEach(ac -> acessorioDAO.remove(ac));
    }

    @Test
    public void registerTest() {
        marcaDAO.register(marca);

        Boolean acessorioRegistered = acessorioDAO.register(acessorio);
        Assert.assertTrue(acessorioRegistered);

        carroDAO.register(carro);
    }

    @Test
    public void findTest() {
        marcaDAO.register(marca);
        acessorioDAO.register(acessorio);
        carroDAO.register(carro);

        Acessorio busca = acessorioDAO.find(acessorio.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(acessorio.getId(), busca.getId());
    }

    @Test
    public void updateTest() {
        marcaDAO.register(marca);
        acessorioDAO.register(acessorio);
        carroDAO.register(carro);

        acessorio.setNome("Travas elétricas");
        acessorioDAO.update(acessorio);

        Acessorio busca = acessorioDAO.find(acessorio.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(busca.getNome(), acessorio.getNome());
    }

    @Test
    public void removeTest() {
        marcaDAO.register(marca);
        acessorioDAO.register(acessorio);
        carroDAO.register(carro);

        Carro c = carroDAO.find(carro.getId());
        carroDAO.remove(c);

        Acessorio busca = acessorioDAO.find(acessorio.getId());
        acessorioDAO.remove(busca);

        Acessorio novaBusca = acessorioDAO.find(busca.getId());
        Assert.assertNull(novaBusca);
    }

    @Test
    public void findAllTest() {
        marcaDAO.register(marca);
        acessorioDAO.register(acessorio);
        carroDAO.register(carro);

        Acessorio novoAcessorio = new Acessorio();
        novoAcessorio.setNome("Acessório Fake");
        novoAcessorio.adicionarCarro(carro);
        acessorioDAO.register(novoAcessorio);

        List<Acessorio> list = acessorioDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }
}
