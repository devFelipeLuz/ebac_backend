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

public class MarcaTest {
    private ICarroDAO carroDAO;
    private Carro carro;

    private IMarcaDAO marcaDAO;
    private Marca marca;

    private IAcessorioDAO acessorioDAO;
    private Acessorio acessorio;

    public MarcaTest() {
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
        Boolean marcaRegistered = marcaDAO.register(marca);
        Assert.assertTrue(marcaRegistered);

        acessorioDAO.register(acessorio);

        carroDAO.register(carro);
    }

    @Test
    public void findTest() {
        marcaDAO.register(marca);
        acessorioDAO.register(acessorio);
        carroDAO.register(carro);

        Marca busca = marcaDAO.find(marca.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(marca.getId(), busca.getId());
    }

    @Test
    public void updateTest() {
        marcaDAO.register(marca);
        acessorioDAO.register(acessorio);
        carroDAO.register(carro);

        marca.setNome("Nissan");
        marcaDAO.update(marca);

        Marca busca = marcaDAO.find(marca.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(busca.getNome(), marca.getNome());
    }

    @Test
    public void removeTest() {
        marcaDAO.register(marca);
        acessorioDAO.register(acessorio);
        carroDAO.register(carro);

        Carro c = carroDAO.find(carro.getId());
        carroDAO.remove(c);

        Marca busca = marcaDAO.find(marca.getId());
        marcaDAO.remove(busca);

        Marca novaBusca = marcaDAO.find(marca.getId());
        Assert.assertNull(novaBusca);
    }

    @Test
    public void findAllTest() {
        marcaDAO.register(marca);
        acessorioDAO.register(acessorio);
        carroDAO.register(carro);

        Marca novaMarca = new Marca();
        novaMarca.setNome("Marca Fake");
        novaMarca.adicionarCarro(carro);
        marcaDAO.register(novaMarca);

        List<Marca> list = marcaDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }
}
