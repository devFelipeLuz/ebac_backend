package br.com.feluz.dao;

import br.com.feluz.dao.factory.ProductFactory;
import br.com.feluz.dao.factory.ProductQuantityFactory;
import br.com.feluz.dao.generics.GenericDAO;
import br.com.feluz.dao.interfaces.IProductQuantityDAO;
import br.com.feluz.dao.jdbc.ConnectionDB;
import br.com.feluz.domain.Inventory;
import br.com.feluz.domain.Product;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductQuantityDAO extends GenericDAO<ProductQuantity, Long> implements IProductQuantityDAO {

    @Override
    public Class<ProductQuantity> getTipoClasse() {
        return ProductQuantity.class;
    }

    @Override
    public void updateData(ProductQuantity entity, ProductQuantity entityCadastrado) {
        entityCadastrado.setQuantidade(entity.getQuantidade());
        entityCadastrado.setValorTotal(entity.getValorTotal());
    }

    @Override
    protected String getQueryInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUTO_QUANTIDADE ");
        sb.append("(ID_PRODUTO_FK, ID_VENDA_FK, QUANTIDADE, VALOR_TOTAL)");
        sb.append("VALUES (?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected void setParamsInsert(PreparedStatement stmInsert, ProductQuantity entity) throws SQLException {
        stmInsert.setLong(1, entity.getProduto().getId());
        stmInsert.setLong(2, entity.getSale().getId());
        stmInsert.setInt(3, entity.getQuantidade());
        stmInsert.setBigDecimal(4, entity.getValorTotal());
    }

    public String getQuerySelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT PQ.ID AS ID_PRODUTO_QUANTIDADE, PQ.QUANTIDADE, PQ.VALOR_TOTAL, ");
        sb.append("P.ID AS ID_PRODUTO, P.NOME, P.CODIGO AS CODIGO_PRODUTO, P.DESCRICAO, P.VALOR, P.CATEGORIA, ");
        sb.append("V.ID AS ID_VENDA, V.CODIGO AS CODIGO_VENDA, V.DATA_VENDA, V.STATUS_VENDA ");
        sb.append("FROM TB_PRODUTO_QUANTIDADE PQ ");
        sb.append("INNER JOIN TB_PRODUTO P ON P.ID = PQ.ID_PRODUTO_FK ");
        sb.append("INNER JOIN TB_VENDA V ON V.ID = PQ.ID_VENDA_FK ");
        return sb.toString();
    }

    @Override
    protected void setParamsSelect(PreparedStatement stmSelect, Long value) throws SQLException {
        stmSelect.setLong(1, value);
    }

    @Override
    protected String getQueryUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_PRODUTO_QUANTIDADE ");
        sb.append("SET QUANTIDADE = ?, VALOR_TOTAL = ? ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }

    @Override
    protected void setParamsUpdate(PreparedStatement stmUpdate, ProductQuantity entity) throws SQLException {
        stmUpdate.setInt(1, entity.getQuantidade());
        stmUpdate.setBigDecimal(2, entity.getValorTotal());
        stmUpdate.setLong(3, entity.getId());
    }

    @Override
    protected String getQueryDelete() {
        return "DELETE FROM TB_PRODUTO_QUANTIDADE WHERE ID = ?";
    }

    @Override
    protected void setParamsDelete(PreparedStatement stmDelete, Long value) throws SQLException {
        stmDelete.setLong(1, value);
    }

    @Override
    public List<ProductQuantity> findAll() throws DAOException, SQLException {
        List<ProductQuantity> list = new ArrayList<>();

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement(getQuerySelect())) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ProductQuantity prodQ = ProductQuantityFactory.convert(rs);
                    //findAssociationPQuantityProduct(dataBase, prodQ);
                    list.add(prodQ);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
        }
        return list;
    }

    private void findAssociationPQuantityProduct(Connection dataBase, ProductQuantity prodQ) throws DAOException {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT PQ.ID AS ID_PRODUTO_QUANTIDADE, P.ID AS ID_PRODUTO, P.NOME, P.CODIGO, P.DESCRICAO," + "\n");
            sb.append("P.CATEGORIA, P.VALOR, PQ.QUANTIDADE, PQ.VALOR_TOTAL" + "\n");
            sb.append("FROM TB_PRODUTO_QUANTIDADE PQ" + "\n");
            sb.append("INNER JOIN TB_PRODUTO P ON P.ID = PQ.ID_PRODUTO_FK" + "\n");
            sb.append("WHERE ID_PRODUTO = ?");
            stm = dataBase.prepareStatement(sb.toString());
            stm.setLong(1, prodQ.getProduto().getId());
            rs = stm.executeQuery();

            if (rs.next()) {
                Product product = ProductFactory.convert(rs);
                prodQ.setProduto(product);
            }

        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, rs);
        }
    }

    public List<ProductQuantity> findBySale(Long saleId) throws DAOException {
        List<ProductQuantity> list = null;

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement(getQuerySelect() + "WHERE PQ.ID_VENDA_FK = ?")) {
            stm.setLong(1, saleId);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ProductQuantity prodQ = ProductQuantityFactory.convert(rs);
                    list.add(prodQ);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR OBJETO", e);
        }
        return list;
    }

    public ProductQuantity findBySaleAndProduct(Long saleId, Long productId) throws DAOException, SQLException {

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement(getQuerySelect() + "WHERE PQ.ID_VENDA_FK = ? AND PQ.ID_PRODUTO_FK = ?")) {
            stm.setLong(1, saleId);
            stm.setLong(2, productId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    ProductQuantity prodQ = ProductQuantityFactory.convert(rs);
                    return prodQ;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
        }
        return null;
    }

    public void removeBySaleAndProductId(Long saleId, Long productId) throws DAOException, SQLException {

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement("DELETE FROM TB_PRODUTO_QUANTIDADE WHERE ID_VENDA_FK = ? AND ID_PRODUTO_FK = ?")) {
            stm.setLong(1, saleId);
            stm.setLong(2, productId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERRO AO REMOVER OBJETO", e);
        }
    }
}
