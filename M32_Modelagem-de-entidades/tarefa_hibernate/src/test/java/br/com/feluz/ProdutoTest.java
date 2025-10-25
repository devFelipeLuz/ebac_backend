package br.com.feluz;


import br.com.feluz.dao.IProdutoDAO;
import br.com.feluz.dao.ProdutoDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProdutoTest {
    private IProdutoDAO produtoDAO;
    private Produto produto;

    public ProdutoTest() {
        produtoDAO = new ProdutoDAO();
    }

    @Before
    public void setUp() {
        produto = new Produto();
        produto.setCodigo("P1");
        produto.setNome("Vassoura Elétrica");
        produto.setValor(80d);
    }

    @After
    public void end() {
        List<Produto> list = produtoDAO.buscarTodos();
        list.forEach(p -> produtoDAO.excluir(p));
    }

    @Test
    public void cadastrar() {
        Produto retorno = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(retorno);
        Assert.assertEquals(produto.getId(), retorno.getId());
    }

    @Test
    public void buscar() {
        Produto retorno = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(retorno);
        Assert.assertEquals(produto.getId(), retorno.getId());

        Produto busca = produtoDAO.buscar(retorno.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(produto.getId(), busca.getId());
    }

    @Test
    public void atualizar() {
        Produto retorno = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(retorno);
        Assert.assertEquals(produto.getId(), retorno.getId());

        produto.setNome("Avaiana de Pau");
        produtoDAO.atualizar(produto);

        Produto busca = produtoDAO.buscar(produto.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(busca.getNome(), produto.getNome());
    }

    @Test
    public void excluir() {
        Produto retorno = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(retorno);
        Assert.assertEquals(produto.getId(), retorno.getId());

        produtoDAO.excluir(retorno);

        Produto busca = produtoDAO.buscar(retorno.getId());
        Assert.assertNull(busca);
    }

    @Test
    public void buscarTodos() {
        Produto retorno = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(retorno);
        Assert.assertEquals(retorno.getId(), produto.getId());

        Produto novoProduto = new Produto();
        novoProduto.setCodigo("P.FAKE");
        novoProduto.setNome("Vassoura de Aco");
        novoProduto.setValor(120d);
        Produto retorno1 = produtoDAO.cadastrar(novoProduto);
        Assert.assertNotNull(retorno1);
        Assert.assertEquals(retorno1.getId(), novoProduto.getId());

        List<Produto> list = produtoDAO.buscarTodos();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }
}
