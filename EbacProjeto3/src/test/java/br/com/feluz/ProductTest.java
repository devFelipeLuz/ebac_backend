package br.com.feluz;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.feluz.dao.generic.jdbc.dao.IProductDAO;
import br.com.feluz.dao.generic.jdbc.dao.ProductDAO;
import br.com.feluz.domain.Product;

import java.util.List;

public class ProductTest {
    private IProductDAO dao;

    @Before
    public void setUp() {
        dao = new ProductDAO();
    }

    @Test
    public void registerTest() throws Exception {
        //Cadastrando um produto
        Product product = new Product();
        product.setName("Biscoito Trakinas");
        product.setPrice(2.00);
        Integer countRegisters = dao.register(product);
        Assert.assertTrue(countRegisters == 1);

        //Encontrando o produto
        Product productDB = dao.find("Biscoito Trakinas");
        Assert.assertNotNull(productDB);
        Assert.assertNotNull(productDB.getId());
        Assert.assertEquals(product.getName(), productDB.getName());
        Assert.assertEquals(product.getPrice(), productDB.getPrice());

        //Removendo para limpar o banco
        Integer countDel = dao.remove(productDB);
        Assert.assertTrue(countDel == 1);
    }

    @Test
    public void findTest() throws Exception {
        //Cadastrando um produto
        Product product = new Product();
        product.setName("Biscoito Trakinas");
        product.setPrice(2.00);
        Integer countRegisters = dao.register(product);
        Assert.assertTrue(countRegisters == 1);

        //Encontrando o produto
        Product productDB = dao.find("Biscoito Trakinas");
        Assert.assertNotNull(productDB);
        Assert.assertNotNull(productDB.getId());
        Assert.assertEquals(product.getName(), productDB.getName());
        Assert.assertEquals(product.getPrice(), productDB.getPrice());

        Integer countDel = dao.remove(productDB);
        Assert.assertTrue(countDel == 1);
    }

    @Test
    public void updateTest() throws Exception {
        //Cadastrando um produto
        Product product = new Product();
        product.setName("Biscoito Trakinas");
        product.setPrice(2.00);
        Integer countRegisters = dao.register(product);
        Assert.assertTrue(countRegisters == 1);

        //Encontrando o produto
        Product productDB = dao.find("Biscoito Trakinas");
        Assert.assertNotNull(productDB);
        Assert.assertNotNull(productDB.getId());
        Assert.assertEquals(product.getName(), productDB.getName());
        Assert.assertEquals(product.getPrice(), productDB.getPrice());

        //Alterando o produto
        productDB.setName("Qualquer outro nome");
        productDB.setPrice(23.90);
        Integer countUpdate = dao.update(productDB);
        Assert.assertTrue(countUpdate == 1);

        //Verificando se o produto anterior realmente não existe mais
        Product productDB1 = dao.find("Biscoito Trakinas");
        Assert.assertNull(productDB1);

        //Verificando se o produto foi realmente alterado
        Product productDB2 = dao.find("Qualquer outro nome");
        Assert.assertNotNull(productDB2);
        Assert.assertEquals(productDB.getName(), productDB2.getName());
        Assert.assertEquals(productDB.getPrice(), productDB2.getPrice());

        //Limpando o banco
        List<Product> list = dao.findAll();
        for (Product p : list) {
            dao.remove(p);
        }
    }

    @Test
    public void removeTest() throws Exception {
        //Cadastrando um produto
        Product product = new Product();
        product.setName("Biscoito Trakinas");
        product.setPrice(2.00);
        Integer countRegisters = dao.register(product);
        Assert.assertTrue(countRegisters == 1);

        //Encontrando o produto
        Product productDB = dao.find("Biscoito Trakinas");
        Assert.assertNotNull(productDB);
        Assert.assertNotNull(productDB.getId());
        Assert.assertEquals(product.getName(), productDB.getName());
        Assert.assertEquals(product.getPrice(), productDB.getPrice());

        //Removendo para limpar o banco
        Integer countDel = dao.remove(productDB);
        Assert.assertTrue(countDel == 1);
    }
    
    @Test
    public void findAllTest() throws Exception {
        //Cadastrando produtos
        Product product = new Product();
        product.setName("Biscoito Trakinas");
        product.setPrice(2.00);
        Integer countRegisters = dao.register(product);
        Assert.assertTrue(countRegisters == 1);

        Product product1 = new Product();
        product1.setName("Biscoito Oreo");
        product1.setPrice(2.99);
        Integer countRegisters1 = dao.register(product1);
        Assert.assertTrue(countRegisters1 == 1);

        List<Product> productList = dao.findAll();
        Assert.assertNotNull(productList);
        Assert.assertEquals(2, productList.size());

        int countDel = 0;
        for (Product p : productList) {
            dao.remove(p);
            countDel++;
        }
        Assert.assertEquals(productList.size(), countDel);

        productList = dao.findAll();
        Assert.assertEquals(productList.size(), 0);
    }
}