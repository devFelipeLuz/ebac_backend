package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Costumer;
import br.com.feluz.jpa.dao.generic.GenericJpaDAO;

public class CostumerDAO extends GenericJpaDAO<Costumer, Long> implements ICostumerDAO {

    public CostumerDAO() {
        super(Costumer.class);
    }
}
