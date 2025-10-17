package br.com.feluz.dao.generic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.feluz.dao.generic.jdbc.ConnectionFactory;
import br.com.feluz.domain.Product;

public class ProductDAO implements IProductDAO {

    @Override
    public Integer register(Product product) throws Exception {
        Connection dataBase = null;
        PreparedStatement stm = null;
        try {
            dataBase = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            stm = dataBase.prepareStatement(sql);
            addParameterInsert(stm, product);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeDB(dataBase, stm, null);
        }
    }

    @Override
    public Product find(String nome) throws Exception {
        Connection dataBase = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Product product = null;
        try {
            dataBase = ConnectionFactory.getConnection();
            String sql = getSqlSelect();
            stm = dataBase.prepareStatement(sql);
            addParameterSelect(stm, nome);
            rs = stm.executeQuery();

            if (rs.next()) {
                product = new Product();
                Long productId = rs.getLong("ID");
                String name = rs.getString("NOME");
                Double price = rs.getDouble("PRECO");
                product.setId(productId);
                product.setName(name);
                product.setPrice(price);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeDB(dataBase, stm, rs);
        }
        return product;
    }

    @Override
    public Integer update(Product product) throws Exception {
        Connection dataBase = null;
        PreparedStatement stm = null;
        try {
            dataBase = ConnectionFactory.getConnection();
            String sql = getSqlUpdate();
            stm = dataBase.prepareStatement(sql);
            addParameterUpdate(stm, product);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeDB(dataBase, stm, null);
        }
    }

    @Override
    public Integer remove(Product product) throws SQLException {
        Connection dataBase = null;
        PreparedStatement stm = null;
        try {
            dataBase = ConnectionFactory.getConnection();
            String sql = getSqlRemove();
            stm = dataBase.prepareStatement(sql);
            addParameterRemove(stm, product);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeDB(dataBase, stm, null);
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        Connection dataBase = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();
        Product product = null;
        try {
            dataBase = ConnectionFactory.getConnection();
            String sql = getSqlSelectAll();
            stm = dataBase.prepareStatement(sql);
            rs = stm.executeQuery();

            while(rs.next()) {
                product = new Product();
                Long id = rs.getLong("ID");
                String nome = rs.getString("NOME");
                Double preco = rs.getDouble("PRECO");
                product.setId(id);
                product.setName(nome);
                product.setPrice(preco);
                productList.add(product);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeDB(dataBase, stm, rs);
        }
        return productList;
    }

    private void addParameterInsert(PreparedStatement stm, Product product) throws SQLException {
        stm.setString(1, product.getName());
        stm.setDouble(2, product.getPrice());
    }

    private String getSqlInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO PRODUTO (ID, NOME, PRECO) ");
        sb.append("VALUES (nextval('PRODUTO_ID_SEQ'),?,?) ");
        return sb.toString();
    }

    private void addParameterSelect(PreparedStatement stm, String nome) throws SQLException {
        stm.setString(1, nome);
    }

    private String getSqlSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM PRODUTO ");
        sb.append("WHERE NOME = ? ");
        return sb.toString();
    }


    private void addParameterUpdate(PreparedStatement stm, Product product) throws SQLException {
        stm.setString(1, product.getName());
        stm.setDouble(2, product.getPrice());
        stm.setLong(3, product.getId());
    }

    private String getSqlUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE PRODUTO ");
        sb.append("SET NOME = ?, PRECO = ? ");
        sb.append("WHERE ID = ? ");
        return sb.toString();
    }

    private void addParameterRemove(PreparedStatement stm, Product product) throws SQLException {
        stm.setLong(1, product.getId());
    }

    private String getSqlRemove() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM PRODUTO ");
        sb.append("WHERE ID = ? ");
        return sb.toString();
    }

    private String getSqlSelectAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM PRODUTO");
        return sb.toString();
    }

    private void closeDB(Connection dataBase, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (dataBase != null && !dataBase.isClosed()) {
                dataBase.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}