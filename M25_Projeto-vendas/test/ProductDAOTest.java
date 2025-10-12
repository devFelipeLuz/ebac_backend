import br.com.feluz.dao.IProductDAO;
import br.com.feluz.dao.ProductDAO;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

public class ProductDAOTest {
    private final IProductDAO productDAO;
    private Product product;

    public ProductDAOTest() {
        productDAO = new ProductDAO();
    }

    @Before
    public void init() throws TipoChaveNaoEncontradaException {
        product = new Product();
        product.setCodigo("A1");
        product.setDescricao("Produto 1");
        product.setNome("Produto 1");
        product.setValor(BigDecimal.TEN);
        productDAO.register(product);
    }

    @Test
    public void search() {
        Product product = this.productDAO.find(this.product.getCodigo());
        Assert.assertNotNull(product);
    }

    @Test
    public void save() throws TipoChaveNaoEncontradaException {
        product.setCodigo("A2");
        Boolean retorno = productDAO.register(product);
        Assert.assertTrue(retorno);
    }

    @Test
    public void remove() {
        productDAO.remove(product.getCodigo());
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException {
        product.setNome("Rodrigo Pires");
        productDAO.update(product);

        Assert.assertEquals("Rodrigo Pires", product.getNome());
    }

    @Test
    public void findAll() {
        Collection<Product> list = productDAO.findAll();
        Assert.assertTrue(list != null);
        Assert.assertTrue(list.size() == 1);
    }
}
