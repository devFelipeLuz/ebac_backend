import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.dao.ProductDAO;
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
import java.sql.SQLException;
import java.util.Collection;

public class ProductDAOTest {
    private final IProductDAO productDAO;
    private Product product;

    public ProductDAOTest() {
        productDAO = new ProductDAO();
    }

    @Before
    public void setUp() throws TipoChaveNaoEncontradaException, DAOException {
        product = new Product();
        product.setCode("A1");
        product.setNome("Produto 1");
        product.setDescricao("Produto 1");
        product.setCategoria("Eletrônicos");
        product.setValor(BigDecimal.TEN);
    }

    @After
    public void removeProductMock() throws DAOException, SQLException {
        try {
            productDAO.remove(product.getCode());
        } catch (Exception ignored) {

        }
    }

    @Test
    public void saveProductTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Boolean retorno = productDAO.register(product);
        Assert.assertTrue(retorno);
    }

    @Test
    public void searchProductTest() throws DAOException, TableException, MoreThanOneRegisterException, TipoChaveNaoEncontradaException, SQLException {
        productDAO.register(product);
        Product findProduct = productDAO.find("A1");
        Assert.assertNotNull(findProduct);
    }

    @Test
    public void updateProductTest() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        productDAO.register(product);
        product.setNome("Qualquer outro produto");
        productDAO.update(product);
        Assert.assertEquals("Qualquer outro produto", product.getNome());
    }

    @Test
    public void removeProductTest() throws DAOException, SQLException, TableException, MoreThanOneRegisterException, TipoChaveNaoEncontradaException {
        productDAO.register(product);
        productDAO.remove(product.getCode());
        Product findProduct = productDAO.find("A1");
        Assert.assertNull(findProduct);
    }

    @Test
    public void findAllProductTest() throws DAOException, TipoChaveNaoEncontradaException, SQLException {
        Product product1 = new Product();
        product1.setCode("A25");
        product1.setNome("Produto 2");
        product1.setDescricao("Produto 2");
        product1.setCategoria("Limpeza");
        product1.setValor(BigDecimal.TEN);

        Boolean retorno = productDAO.register(product);
        Assert.assertTrue(retorno);

        Boolean retorno1 = productDAO.register(product1);
        Assert.assertTrue(retorno1);

        Collection<Product> productList = productDAO.findAll();
        Assert.assertNotNull(productList);
        Assert.assertEquals(2, productList.size());

        productList.forEach(prod -> {
            try {
                productDAO.remove(prod.getCode());
            } catch (DAOException | SQLException e) {
                e.printStackTrace();
            }
        });

        Collection<Product> productList1 = productDAO.findAll();
        Assert.assertNotNull(productList1);
        Assert.assertEquals(0, productList1.size());
    }
}
