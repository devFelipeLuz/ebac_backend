package br.com.feluz.dao;

import br.com.feluz.dao.factory.ProductQuantityFactory;
import br.com.feluz.dao.factory.SaleFactory;
import br.com.feluz.dao.generics.GenericDAO;
import br.com.feluz.dao.jdbc.ConnectionDB;
import br.com.feluz.dao.interfaces.ISaleDAO;
import br.com.feluz.domain.ProductQuantity;
import br.com.feluz.domain.Sale;
import br.com.feluz.exceptions.DAOException;
import br.com.feluz.exceptions.TipoChaveNaoEncontradaException;

import java.sql.*;
import java.util.*;

public class SaleDAO extends GenericDAO<Sale, String> implements ISaleDAO {
    ProductQuantityDAO productQuantityDAO = new ProductQuantityDAO();

    @Override
    public Class<Sale> getTipoClasse() {
        return Sale.class;
    }

    @Override
    public void updateData(Sale entity, Sale entityCadastrado) {
        entityCadastrado.setCodigo(entity.getCodigo());
        entityCadastrado.setStatus(entity.getStatus());
    }

    @Override
    protected String getQueryInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_VENDA (ID, CODIGO, ID_CLIENTE_FK, VALOR_TOTAL, DATA_VENDA, STATUS_VENDA) ");
        sb.append("VALUES (nextval('SQ_VENDA'),?,?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected void setParamsInsert(PreparedStatement stmInsert, Sale entity) throws SQLException {
        stmInsert.setString(1, entity.getCodigo());
        stmInsert.setLong(2, entity.getCliente().getId());
        stmInsert.setBigDecimal(3, entity.getValorTotal());
        stmInsert.setTimestamp(4, Timestamp.from(entity.getDataVenda()));
        stmInsert.setString(5, entity.getStatus().name());
    }

    @Override
    protected void setParamsSelect(PreparedStatement stmSelect, String value) throws SQLException {
        stmSelect.setString(1, value);
    }

    @Override
    protected String getQueryUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_VENDA ");
        sb.append("SET CODIGO = ?, ID_CLIENTE_FK = ?, VALOR_TOTAL = ?, DATA_VENDA = ?, STATUS_VENDA = ? ");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }

    @Override
    protected void setParamsUpdate(PreparedStatement stmUpdate, Sale entity) throws SQLException {
        stmUpdate.setString(1, entity.getCodigo());
        stmUpdate.setLong(2, entity.getCliente().getId());
        stmUpdate.setBigDecimal(3, entity.getValorTotal());
        stmUpdate.setTimestamp(4, Timestamp.from(entity.getDataVenda()));
        stmUpdate.setString(5, entity.getStatus().name());
        stmUpdate.setString(6, entity.getCodigo());
    }

    @Override
    protected String getQueryDelete() {
        return "DELETE FROM TB_VENDA WHERE CODIGO = ?";
    }

    @Override
    protected void setParamsDelete(PreparedStatement stmDelete, String value) throws SQLException {
        stmDelete.setString(1, value);
    }

    @Override
    public Boolean register(Sale entity) throws TipoChaveNaoEncontradaException, DAOException {
        Connection dataBase = null;
        PreparedStatement stm = null;
        try {
            dataBase = ConnectionDB.getConnection();
            stm = dataBase.prepareStatement(getQueryInsert(), Statement.RETURN_GENERATED_KEYS);
            setParamsInsert(stm, entity);
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getLong(1));
                    }
                }

                for (ProductQuantity prod : entity.getProdutos()) {
                    prod.setSale(entity);
                    productQuantityDAO.register(prod);
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO AO CADASTRAR O OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, null);
        }
        return false;
    }

    @Override
    public Sale find(String value) throws DAOException, SQLException {
        StringBuilder sb = sqlBaseSelect();
        sb.append("WHERE V.CODIGO = ? ");
        Connection dataBase = ConnectionDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = dataBase.prepareStatement(sb.toString());
            setParamsSelect(stm, value);
            rs = stm.executeQuery();

            if (rs.next()) {
                Sale sale = SaleFactory.convert(rs);
                findAssociationSaleProduct(dataBase, sale);
                return sale;
            }
        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, rs);
        }
        return null;
    }

    @Override
    public Collection<Sale> findAll() throws DAOException, SQLException {
        List<Sale> list = new ArrayList<>();
        StringBuilder sb = sqlBaseSelect();
        Connection dataBase = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            dataBase = ConnectionDB.getConnection();
            stm = dataBase.prepareStatement(sb.toString());
            rs = stm.executeQuery();

            while (rs.next()) {
                Sale sale = SaleFactory.convert(rs);
                //findAssociationSaleProduct(dataBase, sale);
                list.add(sale);
            }

        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, rs);
        }
        return list;
    }

    @Override
    public void update(Sale entity) throws SQLException, DAOException {
        Connection dataBase = ConnectionDB.getConnection();
        PreparedStatement stm = null;

        try {
            dataBase.setAutoCommit(false);

            stm = dataBase.prepareStatement(getQueryUpdate());
            setParamsUpdate(stm, entity);
            stm.executeUpdate();
            stm.close();

            stm = dataBase.prepareStatement("DELETE FROM TB_PRODUTO_QUANTIDADE WHERE ID_VENDA_FK = ?");
            stm.setLong(1, entity.getId());
            stm.executeUpdate();
            stm.close();

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO TB_PRODUTO_QUANTIDADE (ID_VENDA_FK, ID_PRODUTO_FK, QUANTIDADE, VALOR_TOTAL)" + "\n");
            sb.append("VALUES (?,?,?,?)");
            stm = dataBase.prepareStatement(sb.toString());

            for (ProductQuantity prodQ : entity.getProdutos()) {
                stm.setLong(1, entity.getId());
                stm.setLong(2, prodQ.getProduto().getId());
                stm.setInt(3, prodQ.getQuantidade());
                stm.setBigDecimal(4, prodQ.getValorTotal());
                stm.addBatch();
            }
            stm.executeBatch();

            dataBase.commit();

        } catch (SQLException e) {
            try {
                dataBase.rollback();
                throw new DAOException("ERRO AO ATUALIZAR O OBJETO (Transação desfeita)", e);
            } catch (SQLException rollBackEx) {
                throw new DAOException("ERRO FATAL: Rollback falhou após erro de UPDATE", rollBackEx);
            }
        } finally {
            try {
                if (dataBase != null) {
                    dataBase.setAutoCommit(true);
                }
            } catch (SQLException e) {

            }
            closeConnectionDB(dataBase, stm, null);
        }
    }

    @Override
    public void remove(String valor) throws DAOException, SQLException {
        Connection dataBase = ConnectionDB.getConnection();
        PreparedStatement stm = null;

        try {
            stm = dataBase.prepareStatement(getQueryDelete());
            setParamsDelete(stm, valor);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERRO AO REMOVER OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, null);
        }
    }

    @Override
    public void finishSale(Sale sale) throws DAOException, SQLException {
        Connection dataBase = ConnectionDB.getConnection();
        PreparedStatement stm = null;

        try {
            String sql = "UPDATE TB_VENDA SET STATUS_VENDA = ? WHERE ID = ?";
            stm = dataBase.prepareStatement(sql);
            stm.setString(1, Sale.Status.CONCLUIDA.name());
            stm.setLong(2, sale.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERRO AO ATUALIZAR O OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, null);
        }
    }

    @Override
    public void cancelSale(Sale sale) throws DAOException, SQLException {
        Connection dataBase = ConnectionDB.getConnection();
        PreparedStatement stm = null;

        try {
            String sql = "UPDATE TB_VENDA SET STATUS_VENDA = ? WHERE ID = ?";
            stm = dataBase.prepareStatement(sql);
            stm.setString(1, Sale.Status.CANCELADA.name());
            stm.setLong(2, sale.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERRO AO ATUALIZAR O OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, null);
        }
    }

    private void findAssociationSaleProduct(Connection dataBase, Sale sale) throws DAOException {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT PQ.ID AS ID_PRODUTO_QUANTIDADE, PQ.QUANTIDADE, PQ.VALOR_TOTAL, ");
            sb.append("P.ID AS ID_PRODUTO, P.CODIGO AS CODIGO_PRODUTO, P.NOME, P.DESCRICAO, P.CATEGORIA, P.VALOR, ");
            sb.append("V.ID AS ID_VENDA, V.CODIGO AS CODIGO_VENDA, V.ID_CLIENTE_FK, V.VALOR_TOTAL, V.DATA_VENDA, V.STATUS_VENDA ");
            sb.append("FROM TB_PRODUTO_QUANTIDADE PQ ");
            sb.append("INNER JOIN TB_PRODUTO P ON P.ID = PQ.ID_PRODUTO_FK ");
            sb.append("INNER JOIN TB_VENDA V ON PQ.ID_VENDA_FK = V.ID ");
            sb.append("WHERE PQ.ID_VENDA_FK = ?");
            stm = dataBase.prepareStatement(sb.toString());
            stm.setLong(1, sale.getId());
            rs = stm.executeQuery();
            List<ProductQuantity> products = new ArrayList<>();

            while (rs.next()) {
                ProductQuantity prodQ = ProductQuantityFactory.convert(rs);
                prodQ.setSale(sale);
                products.add(prodQ);
            }
            System.out.println(products.size());
            sale.setProdutos(products);
            sale.recalcValorTotalVenda();

        } catch (SQLException e) {
            throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
        } finally {
            closeConnectionDB(dataBase, stm, rs);
        }
    }

    private StringBuilder sqlBaseSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT V.ID AS ID_VENDA, V.CODIGO AS CODIGO_VENDA, V.VALOR_TOTAL, V.DATA_VENDA, V.STATUS_VENDA, ");
        sb.append("C.ID AS ID_CLIENTE, C.NOME, C.CPF, C.TEL, C.EMAIL, C.ENDERECO, C.NUMERO, C.CIDADE, C.ESTADO ");
        sb.append("FROM TB_VENDA V ");
        sb.append("INNER JOIN TB_CLIENTE C ON V.ID_CLIENTE_FK = C.ID ");
        return sb;
    }
}
