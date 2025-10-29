package br.com.feluz;

import br.com.feluz.jpa.Costumer;
import br.com.feluz.jpa.Product;
import br.com.feluz.jpa.Sale;
import br.com.feluz.jpa.dao.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class SaleDAOTest {

    private ISaleDAO saleDAO;
    private ICostumerDAO costumerDAO;
    private IProductDAO productDAO;
    private Costumer costumer;
    private Product product;
    private Sale sale;

    public SaleDAOTest() {
        saleDAO = new SaleDAO();
        costumerDAO = new CostumerDAO();
        productDAO = new ProductDAO();
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
        product.setName("Produto 1");
        product.setDescription("Produto 1");
        product.setCategory("Eletrônicos");
        product.setValue(BigDecimal.TEN);

        sale = new Sale();
        sale.setCode("S1");
        sale.setSaleData(Instant.now());
        sale.setCostumer(costumer);
        sale.setStatus(Sale.Status.INICIADA);
        sale.addProduct(product, 2);
    }

    @After
    public void end() {
        List<Sale> saleList = saleDAO.findAll();
        saleList.forEach(saleDAO::remove);

        List<Product> productList = productDAO.findAll();
        productList.forEach(productDAO::remove);

        List<Costumer> costumerList = costumerDAO.findAll();
        costumerList.forEach(costumerDAO::remove);
    }

    @Test
    public void save() {
        costumerDAO.save(costumer);
        productDAO.save(product);
        Sale isRegisteredSale = saleDAO.save(sale);
        Assert.assertNotNull(isRegisteredSale);
        Assert.assertEquals(isRegisteredSale.getId(), sale.getId());
    }

    @Test
    public void find() {
        costumerDAO.save(costumer);
        productDAO.save(product);
        saleDAO.save(sale);

        Sale busca = saleDAO.find(sale.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(sale.getId(), busca.getId());
    }

    @Test
    public void update() {
        costumerDAO.save(costumer);
        productDAO.save(product);
        saleDAO.save(sale);

        sale.setCode("Sfake");
        saleDAO.save(sale);

        Sale busca = saleDAO.find(sale.getId());
        Assert.assertNotNull(busca);
        Assert.assertEquals(busca.getCode(), sale.getCode());
    }

    @Test
    public void findAll() {
        costumerDAO.save(costumer);
        productDAO.save(product);
        saleDAO.save(sale);

        Sale newSale = new Sale();
        newSale.setCode("Codigo fake");
        newSale.setSaleData(Instant.now());
        newSale.setCostumer(costumer);
        newSale.setStatus(Sale.Status.INICIADA);
        newSale.addProduct(product, 2);
        saleDAO.save(newSale);

        List<Sale> list = saleDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }
}
