package br.com.feluz.jpa.service;

import br.com.feluz.jpa.Inventory;
import br.com.feluz.jpa.dao.IInventoryDAO;
import br.com.feluz.jpa.service.generic.GenericServiceJpa;

public class InventoryServiceJpa extends GenericServiceJpa<Inventory, Long> implements IInventoryServiceJpa {

    public InventoryServiceJpa(IInventoryDAO inventoryDAO) {
        super(inventoryDAO);
    }
}
