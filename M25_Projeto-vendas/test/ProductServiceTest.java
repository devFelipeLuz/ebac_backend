import br.com.feluz.dao.IProductDAO;
import br.com.feluz.domain.Product;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.IProductService;
import br.com.feluz.services.ProductService;
import dao.ProductDAOMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

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
        product.setCodigo("A1");
        product.setDescricao("Produto 1");
        product.setNome("Produto 1");
        product.setValor(BigDecimal.TEN);
    }

    @Test
    public void pesquisar() {
        Product produtor = this.productService.find(product.getCodigo());
        Assert.assertNotNull(produtor);
    }

    @Test
    public void salvar() throws TipoChaveNaoEncontradaException {
        Boolean retorno = productService.register(product);
        Assert.assertTrue(retorno);
    }

    @Test
    public void excluir() {
        productService.remove(product.getCodigo());
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException {
        product.setNome("Rodrigo Pires");
        productService.update(product);

        Assert.assertEquals("Rodrigo Pires", product.getNome());
    }
}
