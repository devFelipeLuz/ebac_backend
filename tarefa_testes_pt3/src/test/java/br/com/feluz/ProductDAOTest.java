package br.com.feluz;

import br.com.feluz.jpa.Product;
import br.com.feluz.jpa.dao.IProductDAO;
import br.com.feluz.jpa.dao.ProductDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;


public class ProductDAOTest {
    private final IProductDAO productDAO;
    private Product product;

    public ProductDAOTest() {
        productDAO = new ProductDAO();
    }

    @Before
    public void setUp() {
        product = new Product();
        product.setCode("P1");
        product.setName("Produto 1");
        product.setDescription("Produto 1");
        product.setCategory("Eletrônicos");
        product.setValue(BigDecimal.TEN);
    }

    @After
    public void end() {
        List<Product> productList = productDAO.findAll();
        productList.forEach(productDAO::remove);
    }

    @Test
    public void save() {
        Product isRegistered = productDAO.save(product);
        Assert.assertNotNull(isRegistered);
        Assert.assertEquals(isRegistered.getId(), product.getId());
    }

    @Test
    public void find() {
        Product isRegistered = productDAO.save(product);

        Product busca = productDAO.find(isRegistered.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(product.getId(), busca.getId());
    }

    @Test
    public void update() {
        Product isRegistered = productDAO.save(product);

        isRegistered.setName("Outro produto");
        productDAO.save(isRegistered);

        Product busca = productDAO.find(isRegistered.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(busca.getNome(), product.getNome());
    }

    @Test
    public void remove() {
        Product isRegistered = productDAO.save(product);

        productDAO.remove(isRegistered);

        Product busca = productDAO.find(isRegistered.getId());
        Assert.assertNull(busca);
    }

    @Test
    public void findAll() {
        productDAO.save(product);
        Product newProduct = new Product();
        newProduct.setCode("P2");
        newProduct.setName("Produto fake");
        newProduct.setDescription("Descricao fake");
        newProduct.setCategory("Alimentício");
        newProduct.setValue(BigDecimal.TEN);
        productDAO.save(newProduct);

        List<Product> list = productDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }
}
