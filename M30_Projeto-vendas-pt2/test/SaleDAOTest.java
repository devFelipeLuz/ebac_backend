import br.com.feluz.dao.jdbc.ConnectionDB;
import br.com.feluz.dao.CostumerDAO;
import br.com.feluz.dao.ProductDAO;
import br.com.feluz.dao.SaleDAO;
import br.com.feluz.dao.interfaces.ICostumerDAO;
import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.dao.interfaces.ISaleDAO;
import br.com.feluz.domain.Costumer;
import br.com.feluz.domain.Product;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaleDAOTest {

    private ISaleDAO saleDAO;
    private ICostumerDAO costumerDAO;
    private IProductDAO productDAO;
    private Costumer costumer;
    private Product product;
    private Sale sale;

    public SaleDAOTest() {
        saleDAO = new SaleDAO();
        costumerDAO = new CostumerDAO();
        productDAO = new ProductDAO();
    }

    @Before
    public void setUp() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        costumer = new Costumer();
        costumer.setName("Felipe");
        costumer.setCpf(12345678910L);
        costumer.setTel(15912345678L);
        costumer.setEmail("felipe@teste.com");
        costumer.setAddress("Av. Bobos");
        costumer.setHouseNumber(0);
        costumer.setCity("Sorocaba");
        costumer.setState("SP");

        product = new Product();
        product.setCode("P1");
        product.setNome("Produto 1");
        product.setDescricao("Produto 1");
        product.setCategoria("Eletrônicos");
        product.setValor(BigDecimal.TEN);

        sale = new Sale();
        sale.setCodigo("S1");
        sale.setDataVenda(Instant.now());
        sale.setCliente(costumer);
        sale.setStatus(Sale.Status.INICIADA);
        sale.adicionarProduto(product, 2);
    }

    @After
    public void end() {
        try {
            deleteSales();
            costumerDAO.remove(costumer.getCpf());
            productDAO.remove(product.getCode());

        } catch (Exception ignored) {

        }
    }

    @Test
    public void registerSaleTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
    }

    @Test
    public void searchSaleTest() throws TipoChaveNaoEncontradaException, DAOException, TableException, MoreThanOneRegisterException, SQLException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);

        //Teste principal
        //Verificando e comparando se a venda realmente ficou registrada
        Sale vendaConsultada = saleDAO.find(sale.getCodigo());
        Assert.assertNotNull(vendaConsultada);
        Assert.assertEquals(sale.getCodigo(), vendaConsultada.getCodigo());
    }

    @Test
    public void cancelSaleTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException, TableException, MoreThanOneRegisterException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);

        //Teste principal
        //Verificando se o status da venda é alteradp para CANCELADA
        sale.setStatus(Sale.Status.CANCELADA);
        saleDAO.update(sale);
        Sale vendaConsultada = saleDAO.find(sale.getCodigo());
        Assert.assertEquals(sale.getCodigo(), vendaConsultada.getCodigo());
        Assert.assertEquals(Sale.Status.CANCELADA, vendaConsultada.getStatus());
    }

    @Test
    public void addMoreOfTheSameProductsSaleTest() throws TipoChaveNaoEncontradaException, DAOException, TableException, MoreThanOneRegisterException, SQLException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
        Assert.assertNotNull(sale);
        Assert.assertEquals("S1", sale.getCodigo());

        //Buscando a venda e adicionando mais um produto do mesmo, atualizando no banco.
        Sale vendaConsultada = saleDAO.find("S1");
        vendaConsultada.adicionarProduto(product, 1);
        saleDAO.update(vendaConsultada);

        Sale novaBusca = saleDAO.find("S1");

        //verificando se a alteracao realmente foi feita
        Assert.assertEquals(3, (int) novaBusca.getQuantidadeTotalProdutos());
        BigDecimal valorTotal = BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_DOWN);
        Assert.assertEquals(novaBusca.getValorTotal(), valorTotal);
        Assert.assertEquals(Sale.Status.INICIADA, novaBusca.getStatus());
    }

    @Test
    public void addMoreDiferentProductsSaleTest() throws TipoChaveNaoEncontradaException, DAOException, TableException, MoreThanOneRegisterException, SQLException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
        Assert.assertNotNull(sale);
        Assert.assertEquals("S1", sale.getCodigo());

        //Cadastrando um novo produto
        Product newProduct = createProduct("P2", BigDecimal.valueOf(50));
        Assert.assertNotNull(newProduct);
        Assert.assertEquals("P2", newProduct.getCode());

        //Buscando a venda e adicionando um novo produto a ela
        Sale vendaConsultada = saleDAO.find(sale.getCodigo());
        vendaConsultada.adicionarProduto(newProduct, 1);

        //Espera-se 3 produtos, valor total de 70 e status Iniciada
        assertEquals(3, (int) vendaConsultada.getQuantidadeTotalProdutos());
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);
        assertEquals(vendaConsultada.getStatus(), Sale.Status.INICIADA);
    }

    @Test(expected = DAOException.class)
    public void registerAnExistentCodeSaleTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
        Assert.assertNotNull(sale);
        Assert.assertEquals("S1", sale.getCodigo());

        //Registrando uma venda com o mesmo código
        Boolean isRegisteredNewSale1 = saleDAO.register(sale);
        Assert.assertFalse(isRegisteredNewSale1);
        Assert.assertEquals(Sale.Status.INICIADA, sale.getStatus());
    }

    @Test
    public void removeProductSaleTest() throws TipoChaveNaoEncontradaException, DAOException, TableException, MoreThanOneRegisterException, SQLException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
        Assert.assertNotNull(sale);
        Assert.assertEquals("S1", sale.getCodigo());

        //Criando novo produto
        Product newProduct = createProduct("P3", BigDecimal.valueOf(50));
        Assert.assertNotNull(newProduct);
        Assert.assertEquals("P3", newProduct.getCode());

        //Buscando venda e adicionando um produto a ela
        Sale vendaConsultada = saleDAO.find("S1");
        vendaConsultada.adicionarProduto(newProduct, 1);
        assertEquals(3, (int) vendaConsultada.getQuantidadeTotalProdutos());
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);

        //removendo um produto
        vendaConsultada.removerProduto(newProduct, 1);

        assertEquals(2, (int) vendaConsultada.getQuantidadeTotalProdutos());
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);
        assertEquals(vendaConsultada.getStatus(), Sale.Status.INICIADA);
    }

    @Test
    public void removeOnlyOneProductSaleTest() throws TipoChaveNaoEncontradaException, DAOException, TableException, MoreThanOneRegisterException, SQLException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
        Assert.assertNotNull(sale);
        Assert.assertEquals("S1", sale.getCodigo());

        //Criando novo produto
        Product newProduct = createProduct("P4", BigDecimal.valueOf(50));
        Assert.assertNotNull(newProduct);
        Assert.assertEquals("P4", newProduct.getCode());

        //Buscando a venda e adicionando um produto a ela
        Sale vendaConsultada = saleDAO.find("S1");
        vendaConsultada.adicionarProduto(newProduct, 1);
        assertEquals(3, (int) vendaConsultada.getQuantidadeTotalProdutos());
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);

        //removendo apenas um produto da venda
        vendaConsultada.removerProduto(newProduct, 1);
        assertEquals(2, (int) vendaConsultada.getQuantidadeTotalProdutos());
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);
        assertEquals(Sale.Status.INICIADA, vendaConsultada.getStatus());
    }

    @Test
    public void removeAllProductsSaleTest() throws TipoChaveNaoEncontradaException, DAOException, TableException, MoreThanOneRegisterException, SQLException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
        Assert.assertNotNull(sale);
        Assert.assertEquals("S1", sale.getCodigo());

        //Criando novo produto
        Product newProduct = createProduct("P5", BigDecimal.valueOf(50));
        Assert.assertNotNull(newProduct);
        Assert.assertEquals("P5", newProduct.getCode());

        //Buscando venda e adicionando o produto a ela
        Sale vendaConsultada = saleDAO.find("S1");
        vendaConsultada.adicionarProduto(newProduct, 1);
        Assert.assertEquals(3, (int) vendaConsultada.getQuantidadeTotalProdutos());
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        Assert.assertEquals(vendaConsultada.getValorTotal(), valorTotal);

        //removendo todos os produtos da venda
        vendaConsultada.removerTodosProdutos();
        Assert.assertEquals(0, (int) vendaConsultada.getQuantidadeTotalProdutos());
        Assert.assertEquals(vendaConsultada.getValorTotal(), BigDecimal.valueOf(0));
        Assert.assertEquals(Sale.Status.INICIADA, vendaConsultada.getStatus());
    }

    @Test
    public void finishSaleTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException, TableException, MoreThanOneRegisterException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
        Assert.assertNotNull(sale);
        Assert.assertEquals("S1", sale.getCodigo());

        //finalizando venda
        saleDAO.finishSale(sale);

        //Buscando venda e comparando codigo e status
        Sale vendaConsultada = saleDAO.find("S1");
        assertEquals(vendaConsultada.getCodigo(), vendaConsultada.getCodigo());
        assertEquals(Sale.Status.CONCLUIDA, vendaConsultada.getStatus());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void tryAddProductsWithFinishSaleTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException, TableException, MoreThanOneRegisterException {
        //Registrando cliente e retornando boolean
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda e retornando boolean
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);
        Assert.assertNotNull(sale);
        Assert.assertEquals("S1", sale.getCodigo());

        //Finalizando a venda
        saleDAO.finishSale(sale);

        //Buscando a venda e comparando codigo e status
        Sale vendaConsultada = saleDAO.find("S1");
        assertEquals(vendaConsultada.getCodigo(), vendaConsultada.getCodigo());
        assertEquals(Sale.Status.CONCLUIDA, vendaConsultada.getStatus());

        vendaConsultada.adicionarProduto(this.product, 1);
    }

    private void deleteSales() throws SQLException {
        String sqlProd = "DELETE FROM TB_PRODUTO_QUANTIDADE";
        executeDelete(sqlProd);

        String sqlV = "DELETE FROM TB_VENDA";
        executeDelete(sqlV);
    }

    private void executeDelete(String sql) throws SQLException {
        Connection dataBase = ConnectionDB.getConnection();
        PreparedStatement stm = null;
        try {
            stm = dataBase.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnectionDB(dataBase, stm, null);
        }
    }

    private Product createProduct(String codigo, BigDecimal valor) {
        Product mockProduct = new Product();
        mockProduct.setCode(codigo);
        mockProduct.setNome("Produto fake");
        mockProduct.setDescricao("Produto fake");
        mockProduct.setCategoria("Categoria fake");
        mockProduct.setValor(valor);
        return mockProduct;
    }

    private void closeConnectionDB(Connection dataBase, PreparedStatement stm, ResultSet rs) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (stm != null && !stm.isClosed()) {
            stm.close();
        }
        if (dataBase != null && !dataBase.isClosed()) {
            dataBase.close();
        }
    }
}
