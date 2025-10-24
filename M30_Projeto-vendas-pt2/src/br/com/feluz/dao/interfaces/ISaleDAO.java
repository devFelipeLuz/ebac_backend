package br.com.feluz.dao.interfaces;

import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.SQLException;

public interface ISaleDAO extends IGenericDAO<Sale, String> {

    void finishSale(Sale sale) throws TipoChaveNaoEncontradaException, DAOException, SQLException;

    void cancelSale(Sale sale) throws TipoChaveNaoEncontradaException, DAOException, SQLException;
}
