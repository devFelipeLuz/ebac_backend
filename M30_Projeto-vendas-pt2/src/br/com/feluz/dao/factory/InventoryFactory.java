package br.com.feluz.dao.factory;

import br.com.feluz.domain.Inventory;
import br.com.feluz.domain.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryFactory {

    public static Inventory convert(ResultSet rs) throws SQLException {
        Product product = ProductFactory.convert(rs);
        Inventory stock = new Inventory();
        stock.setId(rs.getLong("ID_ESTOQUE"));
        stock.setProduct(product);
        stock.setCode(rs.getString("CODIGO_ESTOQUE"));
        stock.setAvailableQuantity(rs.getInt("QUANTIDADE_DISPONIVEL"));
        stock.setLocation(rs.getString("LOCAL"));
        return stock;
    }
}
