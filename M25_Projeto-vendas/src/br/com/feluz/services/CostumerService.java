package br.com.feluz.services;

import br.com.feluz.dao.ICostumerDAO;
import br.com.feluz.domain.Costumer;
import br.com.feluz.services.generics.GenericService;

public class CostumerService extends GenericService<Costumer, Long> implements ICostumerService {

    public CostumerService(ICostumerDAO costumerDAO) {
        super(costumerDAO);
    }

    @Override
    public Costumer find(Long cpf) {
        return this.dao.find(cpf);
    }
}
