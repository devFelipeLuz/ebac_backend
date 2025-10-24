package br.com.feluz.services;

import br.com.feluz.dao.interfaces.ICostumerDAO;
import br.com.feluz.domain.Costumer;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.services.generics.GenericService;
import br.com.feluz.services.interfaces.ICostumerService;

import java.sql.SQLException;

public class CostumerService extends GenericService<Costumer, Long> implements ICostumerService {

    public CostumerService(ICostumerDAO costumerDAO) {
        super(costumerDAO);
    }

    @Override
    public Costumer find(Long cpf) throws DAOException, SQLException {
        try {
            return this.dao.find(cpf);
        } catch (MoreThanOneRegisterException | TableException e) {
            e.printStackTrace();
        }
        return null;
    }
}
