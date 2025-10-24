package br.com.feluz.dao;

import br.com.feluz.dao.factory.InventoryFactory;
import br.com.feluz.dao.generics.GenericDAO;
import br.com.feluz.dao.interfaces.IInventoryDAO;
import br.com.feluz.dao.jdbc.ConnectionDB;
import br.com.feluz.domain.Inventory;
import br.com.feluz.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InventoryDAO extends GenericDAO<Inventory, String> implements IInventoryDAO {

    public InventoryDAO() {
        super();
    }

    @Override
    public Class<Inventory> getTipoClasse() {
        return Inventory.class;
    }

    @Override
    public void updateData(Inventory entity, Inventory entityCadastrado) {
        entityCadastrado.setCode(entity.getCode());
        entityCadastrado.setProduct(entity.getProduct());
        entityCadastrado.setAvailableQuantity(entity.getAvailableQuantity());
        entityCadastrado.setLocation(entity.getLocation());
    }

    @Override
    protected String getQueryInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_ESTOQUE (ID, ID_PRODUTO_FK, CODIGO, QUANTIDADE_DISPONIVEL, LOCAL) ");
        sb.append("VALUES (nextval('SQ_ESTOQUE'),?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected void setParamsInsert(PreparedStatement stmInsert, Inventory entity) throws SQLException {
        stmInsert.setLong(1, entity.getProduct().getId());
        stmInsert.setString(2, entity.getCode());
        stmInsert.setInt(3, entity.getAvailableQuantity());
        stmInsert.setString(4, entity.getLocation());
    }

    private String getQuerySelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT E.ID AS ID_ESTOQUE, E.CODIGO AS CODIGO_ESTOQUE, E.QUANTIDADE_DISPONIVEL, E.LOCAL, ");
        sb.append("P.ID AS ID_PRODUTO, P.CODIGO AS CODIGO_PRODUTO, P.NOME, P.DESCRICAO, P.VALOR, P.CATEGORIA ");
        sb.append("FROM TB_ESTOQUE E ");
        sb.append("INNER JOIN TB_PRODUTO P ON P.ID = E.ID_PRODUTO_FK ");
        return sb.toString();
    }

    @Override
    protected void setParamsSelect(PreparedStatement stmSelect, String value) throws SQLException {
        stmSelect.setString(1, value);
    }

    @Override
    protected String getQueryUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_ESTOQUE ");
        sb.append("SET ID_PRODUTO_FK = ?, CODIGO = ?, QUANTIDADE_DISPONIVEL = ?, LOCAL = ? ");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }

    @Override
    protected void setParamsUpdate(PreparedStatement stmUpdate, Inventory entity) throws SQLException {
        stmUpdate.setLong(1, entity.getProduct().getId());
        stmUpdate.setString(2, entity.getCode());
        stmUpdate.setInt(3, entity.getAvailableQuantity());
        stmUpdate.setString(4, entity.getLocation());
        stmUpdate.setString(5, entity.getCode());
    }

    @Override
    protected String getQueryDelete() {
        return "DELETE FROM TB_ESTOQUE WHERE CODIGO = ?";
    }

    @Override
    protected void setParamsDelete(PreparedStatement stmDelete, String value) throws SQLException {
        stmDelete.setString(1, value);
    }

    @Override
    public Inventory find(String codigo) throws DAOException, SQLException {
        Connection dataBase = ConnectionDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            StringBuilder sb = new StringBuilder(getQuerySelect());
            sb.append("WHERE E.CODIGO = ?");
            stm = dataBase.prepareStatement(sb.toString());
            setParamsSelect(stm, codigo);
            rs = stm.executeQuery();

            if (rs.next()) {
                Inventory stock = InventoryFactory.convert(rs);
                return stock;

            }
        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, rs);
        }
        return null;
    }

    @Override
    public Collection<Inventory> findAll() throws SQLException, DAOException {
        List<Inventory> stockList = new ArrayList<>();
        Connection dataBase = ConnectionDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = dataBase.prepareStatement(getQuerySelect());
            rs = stm.executeQuery();

            while (rs.next()) {
                Inventory stock = InventoryFactory.convert(rs);
                stockList.add(stock);
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, rs);
        }
        return stockList;
    }
}
