import br.com.feluz.dao.*;
import br.com.feluz.domain.Costumer;
import br.com.feluz.domain.Product;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

public class SaleDAOTest {

    private ISaleDAO vendaDao;
    private ICostumerDAO clienteDao;
    private IProductDAO produtoDao;
    private Costumer cliente;
    private Product produto;

    public SaleDAOTest() {
        vendaDao = new SaleDAO();
        clienteDao = new CostumerDAO();
        produtoDao = new ProductDAO();
    }

    @Before
    public void init() throws TipoChaveNaoEncontradaException {
        this.cliente = cadastrarCliente();
        this.produto = cadastrarProduto("A1", BigDecimal.TEN);
    }

    @Test
    public void pesquisar() throws TipoChaveNaoEncontradaException {
        Sale venda = criarVenda("A1");
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Sale vendaConsultada = vendaDao.find(venda.getCodigo());
        Assert.assertNotNull(vendaConsultada);
        Assert.assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
    }

    @Test
    public void salvar() throws TipoChaveNaoEncontradaException {
        Sale venda = criarVenda("A2");
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(20)));
        Assert.assertTrue(venda.getStatus().equals(Sale.Status.INICIADA));
    }

    @Test
    public void cancelarVenda() throws TipoChaveNaoEncontradaException {
        String codigoVenda = "A3";
        Sale venda = criarVenda(codigoVenda);
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertNotNull(venda);
        Assert.assertEquals(codigoVenda, venda.getCodigo());

        venda.setStatus(Sale.Status.CANCELADA);
        vendaDao.update(venda);

        Sale vendaConsultada = vendaDao.find(codigoVenda);
        Assert.assertEquals(codigoVenda, vendaConsultada.getCodigo());
        Assert.assertEquals(Sale.Status.CANCELADA, vendaConsultada.getStatus());
    }

    @Test
    public void adicionarMaisProdutosDoMesmo() throws TipoChaveNaoEncontradaException {
        String codigoVenda = "A4";
        Sale venda = criarVenda(codigoVenda);
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertNotNull(venda);
        Assert.assertEquals(codigoVenda, venda.getCodigo());

        Sale vendaConsultada = vendaDao.find(codigoVenda);
        vendaConsultada.adicionarProduto(produto, 1);

        Assert.assertTrue(venda.getQuantidadeTotalProdutos() == 3);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(30)));
        Assert.assertTrue(venda.getStatus().equals(Sale.Status.INICIADA));
    }

    @Test
    public void adicionarMaisProdutosDiferentes() throws TipoChaveNaoEncontradaException {
        String codigoVenda = "A5";
        Sale venda = criarVenda(codigoVenda);
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertNotNull(venda);
        Assert.assertEquals(codigoVenda, venda.getCodigo());

        Product prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        Assert.assertNotNull(prod);
        Assert.assertEquals(codigoVenda, prod.getCodigo());

        Sale vendaConsultada = vendaDao.find(codigoVenda);
        vendaConsultada.adicionarProduto(prod, 1);

        Assert.assertTrue(venda.getQuantidadeTotalProdutos() == 3);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(70)));
        Assert.assertTrue(venda.getStatus().equals(Sale.Status.INICIADA));
    }

    @Test
    public void salvarProdutoExistente() throws TipoChaveNaoEncontradaException {
        Sale venda = criarVenda("A6");
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);

        Boolean retorno1 = vendaDao.register(venda);
        Assert.assertFalse(retorno1);
        Assert.assertTrue(venda.getStatus().equals(Sale.Status.INICIADA));
    }

    @Test
    public void removerProduto() throws TipoChaveNaoEncontradaException {
        String codigoVenda = "A7";
        Sale venda = criarVenda(codigoVenda);
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertNotNull(venda);
        Assert.assertEquals(codigoVenda, venda.getCodigo());

        Product prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        Assert.assertNotNull(prod);
        Assert.assertEquals(codigoVenda, prod.getCodigo());

        Sale vendaConsultada = vendaDao.find(codigoVenda);
        vendaConsultada.adicionarProduto(prod, 1);
        Assert.assertTrue(venda.getQuantidadeTotalProdutos() == 3);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(70)));


        vendaConsultada.removerProduto(prod, 1);
        Assert.assertTrue(venda.getQuantidadeTotalProdutos() == 2);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(20)));
        Assert.assertTrue(venda.getStatus().equals(Sale.Status.INICIADA));
    }

    @Test
    public void removerApenasUmProduto() throws TipoChaveNaoEncontradaException {
        String codigoVenda = "A8";
        Sale venda = criarVenda(codigoVenda);
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertNotNull(venda);
        Assert.assertEquals(codigoVenda, venda.getCodigo());

        Product prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        Assert.assertNotNull(prod);
        Assert.assertEquals(codigoVenda, prod.getCodigo());

        Sale vendaConsultada = vendaDao.find(codigoVenda);
        vendaConsultada.adicionarProduto(prod, 1);
        Assert.assertTrue(venda.getQuantidadeTotalProdutos() == 3);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(70)));


        vendaConsultada.removerProduto(prod, 1);
        Assert.assertTrue(venda.getQuantidadeTotalProdutos() == 2);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(20)));
        Assert.assertTrue(venda.getStatus().equals(Sale.Status.INICIADA));
    }

    @Test
    public void removerTodosProdutos() throws TipoChaveNaoEncontradaException {
        String codigoVenda = "A9";
        Sale venda = criarVenda(codigoVenda);
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertNotNull(venda);
        Assert.assertEquals(codigoVenda, venda.getCodigo());

        Product prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        Assert.assertNotNull(prod);
        Assert.assertEquals(codigoVenda, prod.getCodigo());

        Sale vendaConsultada = vendaDao.find(codigoVenda);
        vendaConsultada.adicionarProduto(prod, 1);
        Assert.assertTrue(venda.getQuantidadeTotalProdutos() == 3);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(70)));


        vendaConsultada.removerTodosProdutos();
        Assert.assertTrue(venda.getQuantidadeTotalProdutos() == 0);
        Assert.assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(0)));
        Assert.assertTrue(venda.getStatus().equals(Sale.Status.INICIADA));
    }

    @Test
    public void finalizarVenda() throws TipoChaveNaoEncontradaException {
        String codigoVenda = "A10";
        Sale venda = criarVenda(codigoVenda);
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertNotNull(venda);
        Assert.assertEquals(codigoVenda, venda.getCodigo());

        vendaDao.finishSale(venda);

        Sale vendaConsultada = vendaDao.find(codigoVenda);
        Assert.assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
        Assert.assertEquals(venda.getStatus(), vendaConsultada.getStatus());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void tentarAdicionarProdutosVendaFinalizada() throws TipoChaveNaoEncontradaException {
        String codigoVenda = "A11";
        Sale venda = criarVenda(codigoVenda);
        Boolean retorno = vendaDao.register(venda);
        Assert.assertTrue(retorno);
        Assert.assertNotNull(venda);
        Assert.assertEquals(codigoVenda, venda.getCodigo());

        vendaDao.finishSale(venda);
        Sale vendaConsultada = vendaDao.find(codigoVenda);
        Assert.assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
        Assert.assertEquals(venda.getStatus(), vendaConsultada.getStatus());

        vendaConsultada.adicionarProduto(this.produto, 1);

    }

    private Product cadastrarProduto(String codigo, BigDecimal valor) throws TipoChaveNaoEncontradaException {
        Product produto = new Product();
        produto.setCodigo(codigo);
        produto.setDescricao("Produto 1");
        produto.setNome("Produto 1");
        produto.setValor(valor);
        produtoDao.register(produto);
        return produto;
    }

    private Costumer cadastrarCliente() throws TipoChaveNaoEncontradaException {
        Costumer cliente = new Costumer();
        cliente.setCpf(12312312312L);
        cliente.setName("Felipe");
        cliente.setCity("São Paulo");
        cliente.setAddress("End");
        cliente.setState("SP");
        cliente.setHouseNumber(10);
        cliente.setTel(1199999999L);
        clienteDao.register(cliente);
        return cliente;
    }

    private Sale criarVenda(String codigo) {
        Sale venda = new Sale();
        venda.setCodigo(codigo);
        venda.setDataVenda(Instant.now());
        venda.setCliente(this.cliente);
        venda.setStatus(Sale.Status.INICIADA);
        venda.adicionarProduto(this.produto, 2);
        return venda;
    }
}
