/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author tonyd
 *
 */
public abstract class AbstractDAO<T> {
	protected ArrayList<Object> parameters;
	private Database dbManager;
	public AbstractDAO() {
		try {
			dbManager = Database.getInstance();
		} catch (Exception e) {
			System.err.println("AbstractDAO:" + e.getMessage());
		}
	}
	public Connection getConnection() throws SQLException {
		if (dbManager != null) {
			return dbManager.getConnection();
		}
		return null;
	}
	
	protected PreparedStatement prepareStatement(Connection conn, String queryId) throws SQLException {
		return conn.prepareStatement(queryId);
	}
	
	protected  ArrayList<T> getAll(String query)  {
		ResultSet result;
		ArrayList<T> all = new ArrayList<T>();
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			result = pst.executeQuery();
			
			while(result.next()) {
				all.add( makeBean(result));
			}
			return all;
			
		} catch (SQLException e) {e.printStackTrace();}
	   return all;
	}
	
	protected void CRUD(String query, Object parameter) throws SQLException {
		
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setObject(1, parameter);
			pst.executeUpdate();
		}catch (SQLException e) {throw e; }
	}
	
	protected void CRUD(String query, ArrayList<Object> parameters) throws SQLException {
		int paramIndex =0;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			for(Object parameter : parameters) {
				pst.setObject(paramIndex, parameter);
				paramIndex++;
				
			}
			pst.executeUpdate();
		}catch (SQLException e) {throw e;}
	}
	
	protected boolean exists(String query, Object parameter) {
		ResultSet result;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setObject(1, parameter);
			result = pst.executeQuery();
			if(result.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	protected boolean exists(String query, ArrayList<Object> parameters) {
		ResultSet result;
		int paramIndex = 1;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			for(Object parameter : parameters) {
				pst.setObject(paramIndex, parameter);
				paramIndex++;
				
			}
			result = pst.executeQuery();
			if(result.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	protected T findOne(String query, Object parameter) {
		T ris = null;
		ResultSet result;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setObject(1, parameter);
			result = pst.executeQuery();
			if(result.next()) {
				return makeBean(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}
	
	protected abstract T makeBean(ResultSet rs) throws SQLException;


}
