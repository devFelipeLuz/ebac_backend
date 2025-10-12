package br.com.feluz.dao;

import br.com.feluz.dao.generics.GenericDAO;
import br.com.feluz.domain.Costumer;

public class CostumerDAO extends GenericDAO<Costumer, Long> implements ICostumerDAO {

    public CostumerDAO() {
        super();
    }

    @Override
    public Class<Costumer> getTipoClasse() {
        return Costumer.class;
    }

    @Override
    public void atualiarDados(Costumer entity, Costumer entityCadastrado) {
        entityCadastrado.setCity(entity.getCity());
        entityCadastrado.setCpf(entity.getCpf());
        entityCadastrado.setAddress(entity.getAddress());
        entityCadastrado.setState(entity.getState());
        entityCadastrado.setName(entity.getName());
        entityCadastrado.setHouseNumber(entity.getHouseNumber());
        entityCadastrado.setTel(entity.getTel());
    }
}
