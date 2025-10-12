package br.com.feluz.dao;

import br.com.feluz.dao.generics.GenericDAO;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

public class SaleDAO extends GenericDAO<Sale, String> implements ISaleDAO {

    @Override
    public Class<Sale> getTipoClasse() {
        return Sale.class;
    }

    @Override
    public void atualiarDados(Sale entity, Sale entityCadastrado) {
        entityCadastrado.setCodigo(entity.getCodigo());
        entityCadastrado.setStatus(entity.getStatus());
    }

    @Override
    public void remove(String valor) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    public void finishSale(Sale sale) throws TipoChaveNaoEncontradaException {
        sale.setStatus(Sale.Status.CONCLUIDA);
        super.update(sale);
    }
}
