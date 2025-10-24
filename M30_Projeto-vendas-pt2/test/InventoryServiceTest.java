import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.domain.Inventory;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.InventoryService;
import br.com.feluz.services.ProductService;
import br.com.feluz.services.interfaces.IInvetoryService;
import br.com.feluz.services.interfaces.IProductService;
import dao.InventoryDAOMock;
import dao.ProductDAOMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

public class InventoryServiceTest {
    private IInvetoryService stockService;
    private IProductService productService;
    private Product product;
    private Inventory inventory;

    public InventoryServiceTest() {
        IInventoryDAO stockDAO = new InventoryDAOMock();
        IProductDAO productDAO = new ProductDAOMock();
        stockService = new InventoryService(stockDAO);
        productService = new ProductService(productDAO);
    }

    @Before
    public void setUp() {
        product = new Product();
        product.setCode("A1");
        product.setDescricao("Produto 1");
        product.setNome("Produto 1");
        product.setCategoria("Eletrônicos");
        product.setValor(BigDecimal.TEN);

        inventory = new Inventory();
        inventory.setCode("Estoque-1");
        inventory.setLocation("Londrina - PR");
        inventory.setProduct(product);
        inventory.setAvailableQuantity(30);
    }

    @Test
    public void register() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        Boolean isRegisteredProduct = productService.register(product);
        Assert.assertTrue(isRegisteredProduct);

        Boolean isRegisteredInventory = stockService.register(inventory);
        Assert.assertTrue(isRegisteredInventory);
    }

    @Test
    public void find() throws DAOException, SQLException, TipoChaveNaoEncontradaException, TableException, MoreThanOneRegisterException {
        productService.register(product);
        stockService.register(inventory);
        Inventory stock = this.stockService.find(inventory.getCode());
        Assert.assertNotNull(stock);
    }

    @Test
    public void update() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        inventory.setLocation("Curitiba - PR");
        stockService.update(inventory);

        Assert.assertEquals("Curitiba - PR", inventory.getLocation());
    }

    @Test
    public void excluir() throws DAOException, SQLException, TableException, MoreThanOneRegisterException, TipoChaveNaoEncontradaException {
        stockService.remove("Estoque-1");
    }
}
