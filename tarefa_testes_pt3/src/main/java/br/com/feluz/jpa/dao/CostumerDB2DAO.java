package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Costumer;
import br.com.feluz.jpa.dao.generic.GenericJpaDB2DAO;

public class CostumerDB2DAO extends GenericJpaDB2DAO<Costumer, Long> implements ICostumerDAO {

    public CostumerDB2DAO() {
        super(Costumer.class);
    }
}
