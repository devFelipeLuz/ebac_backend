package br.com.feluz.dao;

import br.com.feluz.dao.generics.GenericDAO;
import br.com.feluz.dao.interfaces.IProductDAO;
import br.com.feluz.domain.Product;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO extends GenericDAO<Product, String> implements IProductDAO {

    public ProductDAO() {
        super();
    }

    @Override
    public Class<Product> getTipoClasse() {
        return Product.class;
    }

    @Override
    public void updateData(Product entity, Product entityCadastrado) {
        entityCadastrado.setCode(entity.getCode());
        entityCadastrado.setDescricao(entity.getDescricao());
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setValor(entity.getValor());
    }

    @Override
    protected String getQueryInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUTO (ID, CODIGO, NOME, DESCRICAO, CATEGORIA, VALOR) ");
        sb.append("VALUES (nextval('SQ_PRODUTO'),?,?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected void setParamsInsert(PreparedStatement stmInsert, Product entity) throws SQLException {
        stmInsert.setString(1, entity.getCode());
        stmInsert.setString(2, entity.getNome());
        stmInsert.setString(3, entity.getDescricao());
        stmInsert.setString(4, entity.getCategoria());
        stmInsert.setBigDecimal(5, entity.getValor());
    }

    @Override
    protected void setParamsSelect(PreparedStatement stmSelect, String value) throws SQLException {
        stmSelect.setString(1, value);
    }

    @Override
    protected String getQueryUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_PRODUTO ");
        sb.append("SET CODIGO = ?, NOME = ?, DESCRICAO = ?, CATEGORIA = ?, VALOR = ? ");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }

    @Override
    protected void setParamsUpdate(PreparedStatement stmUpdate, Product entity) throws SQLException {
        stmUpdate.setString(1, entity.getCode());
        stmUpdate.setString(2, entity.getNome());
        stmUpdate.setString(3, entity.getDescricao());
        stmUpdate.setString(4, entity.getCategoria());
        stmUpdate.setBigDecimal(5, entity.getValor());
        stmUpdate.setString(6, entity.getCode());
    }

    @Override
    protected String getQueryDelete() {
        return "DELETE FROM TB_PRODUTO WHERE CODIGO = ?";
    }

    @Override
    protected void setParamsDelete(PreparedStatement stmDelete, String value) throws SQLException {
        stmDelete.setString(1, value);
    }
}
