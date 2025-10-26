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

public class CarroTest {
    private ICarroDAO carroDAO;
    private Carro carro;

    private IMarcaDAO marcaDAO;
    private Marca marca;

    private IAcessorioDAO acessorioDAO;
    private Acessorio acessorio;

    public CarroTest() {
        carroDAO = new CarroDAO();
        marcaDAO = new MarcaDAO();
        acessorioDAO = new AcessorioDAO();
    }

    @Before
    public void setUp() {
        marca = new Marca();
        marca.setNome("Mitsubishi");
        marca.adicionarCarro(carro);
        marcaDAO.register(marca);

        acessorio = new Acessorio();
        acessorio.adicionarCarro(carro);
        acessorio.setNome("Multimidia");
        acessorioDAO.register(acessorio);

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
        Boolean carroRegistered = carroDAO.register(carro);
        Assert.assertTrue(carroRegistered);
    }

    @Test
    public void findTest() {
        carroDAO.register(carro);

        Carro busca = carroDAO.find(carro.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(carro.getId(), busca.getId());
    }

    @Test
    public void updateTest() {
        carroDAO.register(carro);

        carro.setModelo("Eclipse");
        carroDAO.update(carro);

        Carro busca = carroDAO.find(carro.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(busca.getModelo(), carro.getModelo());
    }

    @Test
    public void removeTest() {
        carroDAO.register(carro);

        Carro busca = carroDAO.find(carro.getId());
        carroDAO.remove(busca);

        Carro novaBusca = carroDAO.find(busca.getId());
        Assert.assertNull(novaBusca);
    }

    @Test
    public void findAllTest() {
        carroDAO.register(carro);

        Carro novoCarro = new Carro();
        novoCarro.setModelo("Carro Fake");
        novoCarro.setMarca(marca);
        novoCarro.adicionarAcessorio(acessorio);
        carroDAO.register(novoCarro);

        List<Carro> list = carroDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }
}
