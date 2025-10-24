package br.com.feluz.services;

import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.domain.Inventory;
import br.com.feluz.services.generics.GenericService;
import br.com.feluz.services.interfaces.IInvetoryService;

public class InventoryService extends GenericService<Inventory, String> implements IInvetoryService {
    public InventoryService(IGenericDAO<Inventory, String> dao) {
        super(dao);
    }
}
