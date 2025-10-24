package br.com.feluz.dao;

import br.com.feluz.dao.generics.GenericDAO;
import br.com.feluz.dao.interfaces.ICostumerDAO;
import br.com.feluz.domain.Costumer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CostumerDAO extends GenericDAO<Costumer, Long> implements ICostumerDAO {

    public CostumerDAO() {
        super();
    }

    @Override
    public Class<Costumer> getTipoClasse() {
        return Costumer.class;
    }

    @Override
    public void updateData(Costumer entity, Costumer entityCadastrado) {
        entityCadastrado.setName(entity.getName());
        entityCadastrado.setCpf(entity.getCpf());
        entityCadastrado.setTel(entity.getTel());
        entityCadastrado.setAddress(entity.getAddress());
        entityCadastrado.setHouseNumber(entity.getHouseNumber());
        entityCadastrado.setState(entity.getCity());
        entityCadastrado.setState(entity.getState());
    }

    @Override
    protected String getQueryInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_CLIENTE (ID, NOME, CPF, TEL, EMAIL, ENDERECO, NUMERO, CIDADE, ESTADO) ");
        sb.append("VALUES (nextval('SQ_CLIENTE'),?,?,?,?,?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected void setParamsInsert(PreparedStatement stmInsert, Costumer entity) throws SQLException {
        stmInsert.setString(1, entity.getName());
        stmInsert.setLong(2, entity.getCpf());
        stmInsert.setLong(3, entity.getTel());
        stmInsert.setString(4, entity.getEmail());
        stmInsert.setString(5, entity.getAddress());
        stmInsert.setLong(6, entity.getHouseNumber());
        stmInsert.setString(7, entity.getCity());
        stmInsert.setString(8, entity.getState());
    }

    @Override
    protected String getQueryUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_CLIENTE ");
        sb.append("SET NOME = ?, CPF = ?, TEL = ?, EMAIL = ?, ENDERECO = ?, NUMERO = ?, CIDADE = ?, ESTADO = ? ");
        sb.append("WHERE CPF = ?");
        return sb.toString();
    }

    @Override
    protected void setParamsUpdate(PreparedStatement stmUpdate, Costumer entity) throws SQLException {
        stmUpdate.setString(1, entity.getName());
        stmUpdate.setLong(2, entity.getCpf());
        stmUpdate.setLong(3, entity.getTel());
        stmUpdate.setString(4, entity.getEmail());
        stmUpdate.setString(5, entity.getAddress());
        stmUpdate.setLong(6, entity.getHouseNumber());
        stmUpdate.setString(7, entity.getCity());
        stmUpdate.setString(8, entity.getState());
        stmUpdate.setLong(9, entity.getCpf());

    }

    @Override
    protected void setParamsSelect(PreparedStatement stmSelect, Long value) throws SQLException {
        stmSelect.setLong(1, value);
    }

    @Override
    protected String getQueryDelete() {
        return "DELETE FROM TB_CLIENTE WHERE CPF = ?";
    }

    @Override
    protected void setParamsDelete(PreparedStatement stmDelete, Long value) throws SQLException {
        stmDelete.setLong(1, value);
    }
}
