import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.domain.Inventory;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.InventoryService;
import br.com.feluz.services.interfaces.IInvetoryService;
import br.com.feluz.services.interfaces.IProductService;
import br.com.feluz.services.ProductService;
import dao.InventoryDAOMock;
import dao.ProductDAOMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ProductServiceTest {

    private IProductService productService;
    private Product product;
    private IInvetoryService invetoryService;
    private Inventory inventory;

    public ProductServiceTest() {
        IInventoryDAO inventoryMock = new InventoryDAOMock();
        invetoryService = new InventoryService(inventoryMock);
        IProductDAO productMock = new ProductDAOMock();
        productService = new ProductService(productMock, inventoryMock);
    }

    @Before
    public void init() {
        product = new Product();
        product.setId(1L);
        product.setCode("A1");
        product.setDescricao("Produto 1");
        product.setNome("Produto 1");
        product.setCategoria("Eletrônicos");
        product.setValor(BigDecimal.TEN);

        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProduct(product);
        inventory.setCode("EST001");
        inventory.setAvailableQuantity(30);
        inventory.setLocation("Londrina - PR");
    }

    @Test
    public void registerProductServiceTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Boolean retorno = productService.register(product);
        Assert.assertTrue(retorno);
    }

    @Test
    public void findProductServiceTest() throws DAOException, SQLException, TableException, MoreThanOneRegisterException, TipoChaveNaoEncontradaException {
        Boolean retorno = productService.register(product);
        Assert.assertTrue(retorno);

        Product busca = this.productService.find(product.getCode());
        Assert.assertNotNull(busca);
        Assert.assertEquals(product.getCode(), busca.getCode());
    }

    @Test
    public void updateProductServiceTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException, TableException, MoreThanOneRegisterException {
        Boolean retorno = productService.register(product);
        Assert.assertTrue(retorno);

        product.setNome("Produto Fake");
        this.productService.update(product);

        Product busca = this.productService.find(product.getCode());
        Assert.assertNotNull(busca);
        Assert.assertEquals(product.getNome(), busca.getNome());
    }

    @Test
    public void removeProductServiceTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException, TableException, MoreThanOneRegisterException {
        Boolean retorno = productService.register(product);
        Assert.assertTrue(retorno);

        Product busca = this.productService.find(product.getCode());
        Assert.assertNotNull(busca);

        this.productService.remove(busca.getCode());

        Product novaBusca = this.productService.find(product.getCode());
        Assert.assertNull(novaBusca);
    }

    @Test
    public void registerProductAndStock_mustBeAtomic() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        Boolean isRegistered = this.productService.registerProductAndStock(product, inventory);
        Assert.assertTrue(isRegistered);

        Inventory busca = this.productService.findStockByProduct(product.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(30, busca.getAvailableQuantity().intValue());
    }
}
