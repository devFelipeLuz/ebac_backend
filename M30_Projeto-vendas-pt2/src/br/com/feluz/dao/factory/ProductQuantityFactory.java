package br.com.feluz.dao.factory;

import br.com.feluz.domain.Product;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.domain.Sale;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductQuantityFactory {

    public static ProductQuantity convert(ResultSet rs) throws SQLException {
        Product product = ProductFactory.convert(rs);
        Sale sale = SaleFactory.convertShallow(rs);
        ProductQuantity prodQ = new ProductQuantity();
        prodQ.setProduto(product);
        prodQ.setSale(sale);
        prodQ.setId(rs.getLong("ID_PRODUTO_QUANTIDADE"));
        prodQ.setQuantidade(rs.getInt("QUANTIDADE"));
        prodQ.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));
        return prodQ;
    }
}
