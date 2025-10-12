package br.com.feluz.dao;

import br.com.feluz.dao.generics.IGenericDAO;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

public interface ISaleDAO extends IGenericDAO<Sale, String> {

    void finishSale(Sale sale) throws TipoChaveNaoEncontradaException;
}
