import br.com.feluz.dao.InventoryDAO;
import br.com.feluz.dao.ProductDAO;
import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.domain.Inventory;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class InventoryDAOTest {
    private IInventoryDAO inventoryDAO;
    private Inventory inventory;
    private IProductDAO productDAO;
    private Product product;

    public InventoryDAOTest() {
        inventoryDAO = new InventoryDAO();
        productDAO = new ProductDAO();
    }

    @Before
    public void setUp() {
        product = new Product();
        product.setCode("P1");
        product.setNome("Produto 1");
        product.setDescricao("Produto 1");
        product.setCategoria("Eletrônicos");
        product.setValor(BigDecimal.TEN);

        inventory = new Inventory();
        inventory.setCode("Estoque-1");
        inventory.setLocation("Londrina - PR");
        inventory.setProduct(product);
        inventory.setAvailableQuantity(30);
    }

    @After
    public void end() {
        try {
            inventoryDAO.remove(inventory.getCode());
            productDAO.remove(product.getCode());
        } catch (Exception ignored) {

        }
    }

    @Test
    public void saveInventoryTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando Estoque e retornando boolean
        Boolean isRegisteredInventory = inventoryDAO.register(inventory);
        Assert.assertTrue(isRegisteredInventory);
    }

    @Test
    public void searchInventoryTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException, TableException, MoreThanOneRegisterException {
        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando Estoque e retornando boolean
        Boolean isRegisteredInventory = inventoryDAO.register(inventory);
        Assert.assertTrue(isRegisteredInventory);

        Inventory estoqueConsultado = inventoryDAO.find("Estoque-1");
        Assert.assertNotNull(estoqueConsultado);
        Assert.assertEquals(inventory.getCode(), estoqueConsultado.getCode());
    }

    @Test
    public void updateInventoryTest() throws DAOException, TableException, MoreThanOneRegisterException, SQLException, TipoChaveNaoEncontradaException {
        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando Estoque e retornando boolean
        Boolean isRegisteredInventory = inventoryDAO.register(inventory);
        Assert.assertTrue(isRegisteredInventory);

        //Alterando local de estoque e comparando
        inventory.setLocation("Sorocaba - SP");
        inventoryDAO.update(inventory);
        Inventory busca = inventoryDAO.find("Estoque-1");
        Assert.assertNotNull(busca);
        Assert.assertEquals("Sorocaba - SP", busca.getLocation());
    }

    @Test
    public void removeInventoryTest() throws DAOException, TableException, MoreThanOneRegisterException, SQLException, TipoChaveNaoEncontradaException {
        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);

        //Registrando Estoque e retornando boolean
        Boolean isRegisteredInventory = inventoryDAO.register(inventory);
        Assert.assertTrue(isRegisteredInventory);

        //Removendo inventário e comparando
        inventoryDAO.remove("Estoque-1");
        Inventory busca = inventoryDAO.find("Estoque-1");
        Assert.assertNull(busca);
    }

    @Test
    public void findAllInventoryTest() throws DAOException, SQLException, TipoChaveNaoEncontradaException {
        //Registrando produto e retornando boolean
        Boolean isRegisteredProduct = productDAO.register(product);
        Assert.assertTrue(isRegisteredProduct);
        Assert.assertNotNull(product);

        //Registrando Estoque e retornando boolean
        Boolean isRegisteredInventory = inventoryDAO.register(inventory);
        Assert.assertTrue(isRegisteredInventory);
        Assert.assertNotNull(inventory);

        //Criando mais um estoque
        Inventory stock = new Inventory();
        stock.setCode("Estoque-2");
        stock.setProduct(product);
        stock.setAvailableQuantity(18);
        stock.setLocation("Curitiba = PR");
        Boolean isRegisteredStock = inventoryDAO.register(stock);
        Assert.assertTrue(isRegisteredStock);
        Assert.assertNotNull(stock);

        //busca de todos os estoques
        Collection<Inventory> stockList = inventoryDAO.findAll();
        Assert.assertNotNull(stockList);
        Assert.assertEquals(2, stockList.size());
        inventoryDAO.remove(stock.getCode());

        for (Inventory i : stockList) {
            inventoryDAO.remove(i.getCode());
        }

        Collection<Inventory> newStockList = inventoryDAO.findAll();
        Assert.assertEquals(0, newStockList.size());
    }
}
