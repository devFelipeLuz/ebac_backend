package br.com.feluz.jpa.dao;

import br.com.feluz.jpa.Costumer;
import br.com.feluz.jpa.dao.generic.GenericJpaDAO;
import br.com.feluz.jpa.dao.generic.GenericJpaDB1DAO;

public class CostumerDAO extends GenericJpaDB1DAO<Costumer, Long> implements ICostumerDAO {

    public CostumerDAO() {
        super(Costumer.class);
    }
}
