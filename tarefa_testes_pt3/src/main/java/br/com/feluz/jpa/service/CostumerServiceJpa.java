package br.com.feluz.jpa.service;

import br.com.feluz.jpa.Costumer;
import br.com.feluz.jpa.dao.ICostumerDAO;
import br.com.feluz.jpa.service.generic.GenericServiceJpa;

public class CostumerServiceJpa extends GenericServiceJpa<Costumer, Long> implements ICostumerServiceJpa {

    public CostumerServiceJpa(ICostumerDAO costumerDAO) {
        super(costumerDAO);
    }
}
