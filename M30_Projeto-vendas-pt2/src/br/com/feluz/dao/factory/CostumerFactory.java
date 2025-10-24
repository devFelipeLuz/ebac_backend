package br.com.feluz.dao.factory;

import br.com.feluz.domain.Costumer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CostumerFactory {

    public static Costumer convert(ResultSet rs) throws SQLException {
        Costumer costumer = new Costumer();
        costumer.setId(rs.getLong("ID_CLIENTE"));
        costumer.setName(rs.getString("NOME"));
        costumer.setCpf(rs.getLong("CPF"));
        costumer.setTel(rs.getLong("TEL"));
        costumer.setEmail(rs.getString("EMAIL"));
        costumer.setAddress(rs.getString("ENDERECO"));
        costumer.setHouseNumber(rs.getInt("NUMERO"));
        costumer.setCity(rs.getString("CIDADE"));
        costumer.setState(rs.getString("ESTADO"));
        return costumer;
    }
}
