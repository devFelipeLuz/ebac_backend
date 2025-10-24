package br.com.feluz.dao.factory;

import br.com.feluz.domain.Costumer;
import br.com.feluz.domain.Sale;
import br.com.feluz.domain.Sale.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleFactory {

    public static Sale convert(ResultSet rs) throws SQLException {
        Costumer costumer = CostumerFactory.convert(rs);
        Sale sale = new Sale();
        sale.setCliente(costumer);
        sale.setId(rs.getLong("ID_VENDA"));
        sale.setCodigo(rs.getString("CODIGO_VENDA"));
        sale.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));
        sale.setDataVenda(rs.getTimestamp("DATA_VENDA").toInstant());
        sale.setStatus(Status.getByName(rs.getString("STATUS_VENDA")));
        return sale;
    }

    public static Sale convertShallow(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getLong("ID_VENDA"));
        sale.setCodigo(rs.getString("CODIGO_VENDA"));
        sale.setDataVenda(rs.getTimestamp("DATA_VENDA").toInstant());
        sale.setStatus(Status.getByName(rs.getString("STATUS_VENDA")));
        return sale;
    }
}
