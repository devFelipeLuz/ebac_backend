package dao;

import br.com.feluz.dao.interfaces.ICostumerDAO;
import br.com.feluz.domain.Costumer;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.util.Collection;

public class CostumerDAOMock implements ICostumerDAO {

    @Override
    public Boolean register(Costumer entity) throws TipoChaveNaoEncontradaException {
        return true;
    }

    @Override
    public void remove(Long valor) {

    }

    @Override
    public void update(Costumer entity) throws TipoChaveNaoEncontradaException {

    }

    @Override
    public Costumer find(Long valor) {
        Costumer costumer = new Costumer();
        costumer.setCpf(valor);
        return costumer;
    }

    @Override
    public Collection<Costumer> findAll() {
        return null;
    }
}
