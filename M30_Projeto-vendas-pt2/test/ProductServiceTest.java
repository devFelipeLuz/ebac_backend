import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.interfaces.IProductService;
import br.com.feluz.services.ProductService;
import dao.ProductDAOMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ProductServiceTest {

    private IProductService productService;
    private Product product;

    public ProductServiceTest() {
        IProductDAO dao = new ProductDAOMock();
        productService = new ProductService(dao);
    }

    @Before
    public void init() {
        product = new Product();
        product.setCode("A1");
        product.setDescricao("Produto 1");
        product.setNome("Produto 1");
        product.setCategoria("Eletrônicos");
        product.setValor(BigDecimal.TEN);
    }

    @Test
    public void pesquisar() throws DAOException, SQLException, TableException, MoreThanOneRegisterException {
        Product produto = this.productService.find(product.getCode());
        Assert.assertNotNull(produto);
    }

    @Test
    public void salvar() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Boolean retorno = productService.register(product);
        Assert.assertTrue(retorno);
    }

    @Test
    public void excluir() throws DAOException, SQLException {
        productService.remove(product.getCode());
    }

    @Test
    public void alterarProduto() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        product.setNome("Rodrigo Pires");
        productService.update(product);

        Assert.assertEquals("Rodrigo Pires", product.getNome());
    }
}
