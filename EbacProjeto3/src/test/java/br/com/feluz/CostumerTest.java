package br.com.feluz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


import br.com.feluz.dao.generic.jdbc.dao.CostumerDAO;
import br.com.feluz.dao.generic.jdbc.dao.ICostumerDAO;
import br.com.feluz.domain.Costumer;


public class CostumerTest {
	private ICostumerDAO costumerDAO;
	
	@Before
	public void setUp() {
		costumerDAO = new CostumerDAO();	
	}
	
	@Test
	public void registerTest() throws Exception {
		Costumer costumer = new Costumer();
		costumer.setCode("10");
		costumer.setName("Felipe Luz");
		Integer countRegisters = costumerDAO.register(costumer);
		assertTrue(countRegisters == 1);
		
		Costumer costumerDB = costumerDAO.find("10");
		assertNotNull(costumerDB);
		assertNotNull(costumerDB.getId());
		assertEquals(costumer.getCode(), costumerDB.getCode());
		assertEquals(costumer.getName(), costumerDB.getName());
		
		Integer countDel = costumerDAO.remove(costumerDB);
		assertTrue(countDel == 1);
	}
	
	@Test
	public void findTest() throws Exception {
		Costumer costumer = new Costumer();
		costumer.setCode("10");
		costumer.setName("Felipe Luz");
		Integer countRegisters = costumerDAO.register(costumer);
		assertTrue(countRegisters == 1);
		
		Costumer costumerDB = costumerDAO.find("10");
		assertNotNull(costumerDB);
		assertNotNull(costumerDB.getId());
		assertEquals(costumer.getCode(), costumerDB.getCode());
		assertEquals(costumer.getName(), costumerDB.getName());
		
		Integer countDel = costumerDAO.remove(costumerDB);
		assertTrue(countDel == 1);
	}
	
	@Test
	public void removeTest() throws Exception {
		Costumer costumer = new Costumer();
		costumer.setCode("10");
		costumer.setName("Felipe Luz");
		Integer countRegisters = costumerDAO.register(costumer);
		assertTrue(countRegisters == 1);
		
		Costumer costumerDB = costumerDAO.find("10");
		assertNotNull(costumerDB);
		assertNotNull(costumerDB.getId());
		assertEquals(costumer.getCode(), costumerDB.getCode());
		assertEquals(costumer.getName(), costumerDB.getName());
		
		Integer countDel = costumerDAO.remove(costumerDB);
		assertTrue(countDel == 1);
	}
	
	@Test
	public void findAllTest() throws Exception {
		Costumer costumer = new Costumer();
		costumer.setCode("10");
		costumer.setName("Felipe Luz");
		Integer countRegisters = costumerDAO.register(costumer);
		assertTrue(countRegisters == 1);
		
		Costumer costumer1 = new Costumer();
		costumer.setCode("20");
		costumer.setName("Paula Nobre");
		Integer countRegisters1 = costumerDAO.register(costumer);
		assertTrue(countRegisters1 == 1);
		
		List<Costumer> list = costumerDAO.findAll();
		assertNotNull(list);
		assertEquals(2, list.size());
		
		int countDel = 0;
		for (Costumer c : list) {
			costumerDAO.remove(c);
			countDel++;
		}
		assertEquals(list.size(), countDel);
		
		list = costumerDAO.findAll();
		assertEquals(list.size(), 0);
	}
	
	@Test
	public void updateTest() throws Exception {
		Costumer costumer = new Costumer();
		costumer.setCode("10");
		costumer.setName("Felipe Luz");
		Integer countRegisters = costumerDAO.register(costumer);
		assertTrue(countRegisters == 1);
		
		Costumer costumerDB = costumerDAO.find("10");
		assertNotNull(costumerDB);
		assertNotNull(costumerDB.getId());
		assertEquals(costumer.getCode(), costumerDB.getCode());
		assertEquals(costumer.getName(), costumerDB.getName());
		
		costumerDB.setCode("20");
		costumerDB.setName("Qualquer outro nome");
		Integer countUpdate = costumerDAO.update(costumerDB);
		assertTrue(countUpdate == 1);
		
		Costumer costumerDB1 = costumerDAO.find("10");
		assertNull(costumerDB1);
		
		Costumer costumerDB2 = costumerDAO.find("20");
		assertNotNull(costumerDB2);
		assertEquals(costumerDB.getCode(), costumerDB2.getCode());
		assertEquals(costumerDB.getName(), costumerDB2.getName());
		
		List<Costumer> list = costumerDAO.findAll();
		for (Costumer c : list) {
			costumerDAO.remove(c);
		}
	}
	
}
