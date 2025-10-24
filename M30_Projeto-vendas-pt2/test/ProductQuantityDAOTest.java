import br.com.feluz.dao.CostumerDAO;
import br.com.feluz.dao.ProductDAO;
import br.com.feluz.dao.ProductQuantityDAO;
import br.com.feluz.dao.SaleDAO;
import br.com.feluz.dao.interfaces.ICostumerDAO;
import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.dao.interfaces.ISaleDAO;
import br.com.feluz.domain.Costumer;
import br.com.feluz.domain.Product;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collection;

public class ProductQuantityDAOTest {
    ICostumerDAO costumerDAO;
    Costumer costumer;
    IProductDAO productDAO;
    Product product;
    ISaleDAO saleDAO;
    Sale sale;
    ProductQuantityDAO prodQDAO;
    ProductQuantity prodQ;

    public ProductQuantityDAOTest() {
        costumerDAO = new CostumerDAO();
        productDAO = new ProductDAO();
        saleDAO = new SaleDAO();
        prodQDAO = new ProductQuantityDAO();
    }

    @Before
    public void setUp() {
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
            prodQDAO.remove(prodQ.getId());
            saleDAO.remove(sale.getCodigo());
            productDAO.remove(product.getCode());
            costumerDAO.remove(costumer.getCpf());

        } catch (Exception ignored) {

        }
    }

    @Test
    public void saveProductQuantityTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        //Registrando cliente
        Boolean isRegisteredCostumer = costumerDAO.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        //Registrando produto
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando venda
        Boolean isRegisteredSale = saleDAO.register(sale);
        Assert.assertTrue(isRegisteredSale);

        //Buscando os produtos, comparando quantidade de valor
        ProductQuantity busca = prodQDAO.findBySaleAndProduct(sale.getId(), product.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(Integer.valueOf(2), busca.getQuantidade());
        BigDecimal expectedValue = BigDecimal.TEN.multiply(BigDecimal.valueOf(2));
        Assert.assertEquals(0, busca.getValorTotal().compareTo(expectedValue));
        Assert.assertNotNull(busca.getProduto());
        Assert.assertEquals("P1", busca.getProduto().getCode());
        Assert.assertNotNull(busca.getSale());
        Assert.assertEquals("S1", busca.getSale().getCodigo());

        this.prodQ = busca;
    }

    @Test
    public void searchProductQuantityTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        //Registro de cliente, produto e venda
        costumerDAO.register(costumer);
        productDAO.register(product);
        saleDAO.register(sale);

        //Buscando os produtos, comparando quantidade de valor
        ProductQuantity busca = prodQDAO.findBySaleAndProduct(sale.getId(), product.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(Integer.valueOf(2), busca.getQuantidade());
        BigDecimal expectedValue = BigDecimal.TEN.multiply(BigDecimal.valueOf(2));
        Assert.assertEquals(0, busca.getValorTotal().compareTo(expectedValue));
        Assert.assertNotNull(busca.getProduto());
        Assert.assertEquals("P1", busca.getProduto().getCode());
        Assert.assertNotNull(busca.getSale());
        Assert.assertEquals("S1", busca.getSale().getCodigo());

        this.prodQ = busca;
    }

    @Test
    public void updateProductQuantityTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        //Registro de cliente, produto e venda
        costumerDAO.register(costumer);
        productDAO.register(product);
        saleDAO.register(sale);

        //Buscando e verificando se existe
        ProductQuantity busca = prodQDAO.findBySaleAndProduct(sale.getId(), product.getId());
        Assert.assertNotNull(busca);

        //alterando quantidade
        int novaQuantidade = 5;
        busca.setQuantidade(novaQuantidade);
        busca.setValorTotal(busca.getProduto().getValor().multiply(BigDecimal.valueOf(novaQuantidade)));

        //atualizando a quantidade
        prodQDAO.update(busca);

        //nova busca e comparando se o valor atualizou
        ProductQuantity itemAtualizado = prodQDAO.findBySaleAndProduct(sale.getId(), product.getId());
        Assert.assertNotNull(itemAtualizado);
        Assert.assertEquals(Integer.valueOf(novaQuantidade), itemAtualizado.getQuantidade());

        this.prodQ = itemAtualizado;
    }

    @Test
    public void removeProductQuantityTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        //Registro de cliente, produto e venda
        costumerDAO.register(costumer);
        productDAO.register(product);
        saleDAO.register(sale);

        //Buscando
        ProductQuantity busca = prodQDAO.findBySaleAndProduct(sale.getId(), product.getId());
        Assert.assertNotNull(busca);

        //Removendo
        prodQDAO.remove(busca.getId());

        //Nova busca e comparacao
        ProductQuantity novaBusca = prodQDAO.findBySaleAndProduct(sale.getId(), product.getId());
        Assert.assertNull(novaBusca);

        this.prodQ = novaBusca;
        saleDAO.remove(sale.getCodigo());
        productDAO.remove(product.getCode());
        costumerDAO.remove(costumer.getCpf());
    }

    @Test
    public void findAllProductQuantityTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        //Registro de cliente, produto e venda
        costumerDAO.register(costumer);
        productDAO.register(product);
        saleDAO.register(sale);

        //Criando novo produto
        Product newProduct = createProduct("P2", BigDecimal.valueOf(10));
        productDAO.register(newProduct);

        //Criando nova venda
        Sale newSale = createSale("S2");
        saleDAO.register(newSale);

        Collection<ProductQuantity> prodQList = prodQDAO.findAll();
        Assert.assertNotNull(prodQList);
        Assert.assertEquals(2, prodQList.size());

        Collection<Sale> saleList = saleDAO.findAll();
        Collection<Product> prodList = productDAO.findAll();
        Collection<Costumer> costumerList = costumerDAO.findAll();

        for (ProductQuantity p : prodQList) {
            prodQDAO.remove(p.getId());
        }
        for (Sale s : saleList) {
            saleDAO.remove(s.getCodigo());
        }
        for (Product prod : prodList) {
            productDAO.remove(prod.getCode());
        }
        for (Costumer c : costumerList) {
            costumerDAO.remove(c.getCpf());
        }

        prodQList = prodQDAO.findAll();
        Assert.assertEquals(0, prodQList.size());

        saleList = saleDAO.findAll();
        Assert.assertEquals(0, saleList.size());

        prodList = productDAO.findAll();
        Assert.assertEquals(0, prodList.size());

        costumerList = costumerDAO.findAll();
        Assert.assertEquals(0, costumerList.size());
    }

    private Sale createSale(String codigo) {
        Sale mockSale = new Sale();
        mockSale.setCodigo(codigo);
        mockSale.setDataVenda(Instant.now());
        mockSale.setCliente(costumer);
        mockSale.setStatus(Sale.Status.INICIADA);
        mockSale.adicionarProduto(product, 2);
        return mockSale;
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
