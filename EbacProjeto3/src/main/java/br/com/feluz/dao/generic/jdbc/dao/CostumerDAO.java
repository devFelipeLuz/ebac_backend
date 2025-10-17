package br.com.feluz.dao.generic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.feluz.dao.generic.jdbc.ConnectionFactory;
import br.com.feluz.domain.Costumer;

public class CostumerDAO implements ICostumerDAO {

	@Override
	public Integer register(Costumer costumer) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlInsert();
			stm = connection.prepareStatement(sql);
			addParameterInsert(stm, costumer);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
		
	}

	@Override
	public Integer update(Costumer costumer) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlUpdate();
			stm = connection.prepareStatement(sql);
			addParameterUpdate(stm, costumer);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e; 
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public Costumer find(String code) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Costumer costumer = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelect();
			stm = connection.prepareStatement(sql);
			addParameterSelect(stm, code);
			rs = stm.executeQuery();
			
			if (rs.next()) {
				costumer = new Costumer();
				Long id = rs.getLong("ID");
				String name = rs.getString("NAME");
				String cd = rs.getString("CODE");
				costumer.setId(id);
				costumer.setName(name);
				costumer.setCode(cd);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return costumer;
	}

	@Override
	public List<Costumer> findAll() throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<Costumer> costumerList = new ArrayList<>();
		Costumer costumer = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelectAll();
			stm = connection.prepareStatement(sql);
			rs = stm.executeQuery();
			
			while(rs.next()) {
				costumer = new Costumer();
				Long id = rs.getLong("ID");
				String name = rs.getString("NAME");
				String cd = rs.getString("CODE");
				costumer.setId(id);
				costumer.setName(name);
				costumer.setCode(cd);
				costumerList.add(costumer);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return costumerList;
	}

	@Override
	public Integer remove(Costumer costumer) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlDelete();
			stm = connection.prepareStatement(sql);
			addParameterDelete(stm, costumer);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}
	
	private String getSqlDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TB_COSTUMER ");
		sb.append("WHERE CODE = ?");
		return sb.toString();
	}
	
	private void addParameterDelete(PreparedStatement stm, Costumer costumer) throws SQLException {
		stm.setString(1, costumer.getCode());
	}
	
	private String getSqlSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_COSTUMER");
		return sb.toString();
	}
	
	private String getSqlSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_COSTUMER ");
		sb.append("WHERE CODE = ?");
		return sb.toString();
	}
	
	private void addParameterSelect(PreparedStatement stm, String code) throws SQLException {
		stm.setString(1,  code);
	}
	
	private String getSqlUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TB_COSTUMER ");
		sb.append("SET NAME = ?, CODE = ? ");
		sb.append("WHERE ID = ?");
		return sb.toString();
	}
	
	private void addParameterUpdate(PreparedStatement stm, Costumer costumer) throws SQLException {
		stm.setString(1, costumer.getName());
		stm.setString(2, costumer.getCode());
		stm.setLong(3, costumer.getId());
	}
	
	private String getSqlInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_COSTUMER (ID, CODE, NAME) ");
		sb.append("VALUES (nextval('SQ_COSTUMER'),?,?)");
		return sb.toString();
	}
	
	private void addParameterInsert(PreparedStatement stm, Costumer costumer) throws SQLException {
		stm.setString(1, costumer.getCode());
		stm.setString(2, costumer.getName());
	}
	
	private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs ) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (stm != null && !stm.isClosed()) {
				stm.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
}
