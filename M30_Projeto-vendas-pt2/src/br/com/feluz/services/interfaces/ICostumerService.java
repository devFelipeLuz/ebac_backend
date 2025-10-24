package br.com.feluz.services.interfaces;

import br.com.feluz.domain.Costumer;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.MoreThanOneRegisterException;
import br.com.feluz.exceptions.TableException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;
import br.com.feluz.services.generics.IGenericService;

import java.sql.SQLException;

public interface ICostumerService extends IGenericService<Costumer, Long> {

    Costumer find(Long cpf) throws DAOException, SQLException, MoreThanOneRegisterException, TableException;

}
