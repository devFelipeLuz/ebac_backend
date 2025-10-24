package br.com.feluz.dao.factory;

import br.com.feluz.domain.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductFactory {

    public static Product convert(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("ID_PRODUTO"));
        product.setCode(rs.getString("CODIGO_PRODUTO"));
        product.setNome(rs.getString("NOME"));
        product.setDescricao(rs.getString("DESCRICAO"));
        product.setCategoria(rs.getString("CATEGORIA"));
        product.setValor(rs.getBigDecimal("VALOR"));
        return product;
    }
}
