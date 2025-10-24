import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.dao.interfaces.IProductQuantityDAO;
import br.com.feluz.dao.interfaces.ISaleDAO;
import br.com.feluz.domain.*;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.InventoryService;
import br.com.feluz.services.ProductQuantityService;
import br.com.feluz.services.SaleService;
import br.com.feluz.services.interfaces.*;
import dao.InventoryDAOMock;
import dao.ProductQuantityDAOMock;
import dao.SaleDAOMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;

public class SaleServiceTest {

    private ICostumerService costumerService;
    private IProductService productService;
    private ISaleService saleService;
    private IProductQuantityService prodQService;
    private IInvetoryService invetoryService;
    private Costumer costumer;
    private Product product;
    private Sale sale;


    public SaleServiceTest() {
        IInventoryDAO inventoryMock = new InventoryDAOMock();
        invetoryService = new InventoryService(inventoryMock);

        IProductQuantityDAO prodQMock = new ProductQuantityDAOMock();
        prodQService = new ProductQuantityService(prodQMock);

        ISaleDAO saleMock = new SaleDAOMock();
        saleService = new SaleService(saleMock, prodQMock, inventoryMock);
    }

    @Before
    public void setUp() {
        costumer = new Costumer();
        costumer.setId(1L);
        costumer.setName("Felipe");
        costumer.setCpf(12345678910L);
        costumer.setTel(15912345678L);
        costumer.setEmail("felipe@teste.com");
        costumer.setAddress("Av. Bobos");
        costumer.setHouseNumber(0);
        costumer.setCity("Sorocaba");
        costumer.setState("SP");

        product = new Product();
        product.setId(1L);
        product.setCode("A1");
        product.setDescricao("Produto 1");
        product.setNome("Produto 1");
        product.setCategoria("Eletrônicos");
        product.setValor(BigDecimal.TEN);

        sale = new Sale();
        sale.setId(1L);
        sale.setCodigo("S1");
        sale.setDataVenda(Instant.now());
        sale.setCliente(costumer);
        sale.setStatus(Sale.Status.INICIADA);
        sale.adicionarProduto(product, 2);
    }

    @Test
    public void registerSaleServiceTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        Boolean isRegisteredCostumer = costumerService.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        Boolean isRegisteredProduct = productService.register(product);
        Assert.assertTrue(isRegisteredProduct);

        Boolean isRegisteredSale = saleService.register(sale);
        Assert.assertTrue(isRegisteredSale);
    }

    @Test
    public void searchSaleServiceTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException, TableException, MoreThanOneRegisterException {
        Boolean isRegisteredCostumer = costumerService.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        Boolean isRegisteredProduct = productService.register(product);
        Assert.assertTrue(isRegisteredProduct);

        Boolean isRegisteredSale = saleService.register(sale);
        Assert.assertTrue(isRegisteredSale);

        Sale busca = saleService.find(sale.getCodigo());
        Assert.assertNotNull(busca);
        Assert.assertEquals(sale.getId(), busca.getId());
    }

    @Test
    public void updateSaleServiceTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException, TableException, MoreThanOneRegisterException {
        Boolean isRegisteredCostumer = costumerService.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        Boolean isRegisteredProduct = productService.register(product);
        Assert.assertTrue(isRegisteredProduct);

        Boolean isRegisteredSale = saleService.register(sale);
        Assert.assertTrue(isRegisteredSale);

        sale.setId(0001L);
        saleService.update(sale);

        Sale busca = saleService.find(sale.getCodigo());
        Assert.assertNotNull(busca);
        Assert.assertEquals(sale.getCodigo(), busca.getCodigo());
    }

    @Test
    public void removeTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException, TableException, MoreThanOneRegisterException {
        Boolean isRegisteredCostumer = costumerService.register(costumer);
        Assert.assertTrue(isRegisteredCostumer);

        Boolean isRegisteredProduct = productService.register(product);
        Assert.assertTrue(isRegisteredProduct);

        Boolean isRegisteredSale = saleService.register(sale);
        Assert.assertTrue(isRegisteredSale);

        Sale busca = saleService.find(sale.getCodigo());
        Assert.assertNotNull(busca);

        saleService.remove(busca.getCodigo());

        Sale novaBusca = saleService.find(sale.getCodigo());
        Assert.assertNull(novaBusca);
    }
}
