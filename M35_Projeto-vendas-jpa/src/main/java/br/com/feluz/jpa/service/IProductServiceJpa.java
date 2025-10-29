package br.com.feluz.jpa.service;

import br.com.feluz.jpa.Inventory;
import br.com.feluz.jpa.Product;
import br.com.feluz.jpa.service.generic.IGenericServiceJpa;

import javax.persistence.EntityManager;

public interface IProductServiceJpa extends IGenericServiceJpa<Product, Long> {

    Boolean saveProductAndInventory(Product product, Inventory inventory);
}
