package waterfall.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.rowset.CachedRowSetImpl;

import waterfall.model.ParameterObject;

public class DBUtil {
	private DataSource dataSource;
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
	
	public DBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void close(ResultSet rs, Statement stmt, Connection con) {
		try {
			if(rs != null) 
				rs.close();
			if(stmt != null)
				stmt.close();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(Statement stmt, Connection con) {
		try {
			if(stmt != null)
				stmt.close();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(PreparedStatement ps, Connection con) {
		try {
			if(ps != null)
				ps.close();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(ResultSet rs, PreparedStatement ps, Connection con) {
		try {
			if(rs != null) 
				rs.close();
			if(ps != null)
				ps.close();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet dbExecuteQuery(String query)  {
		logger.debug("Executing update query: '{}'", query);
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		
		try {
			con = dataSource.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		} catch (SQLException e) {
			logger.error("Error occured during the execution of the query: '{}'", query, e);
		} finally {
			logger.debug("Closing execution");
			close(rs, stmt, con);
		}
		
		return crs;
		
	}
	
	public void dbExecuteUpdate(String query) {
		logger.debug("Executing update query: '{}'", query);
		Connection con = null;
		Statement stmt = null;
		
		try {
			con = dataSource.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e ) {
			logger.error("Error occured during the execution of the update query: '{}'", query, e);
		} finally {
			logger.debug("Closing execution");
			close(stmt, con);
		}
	}
	
	public void dbExecuteUpdatePrepStmt(String query, ParameterObject po) {
		logger.debug("Executing update query with prepared statement: '{}' with values {}", query, po);
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			
			ps.setString(1,po.getValue1());
			ps.setString(2,po.getValue2());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error occured during the execution of the update query with prepared statement: '{}' with values {}", query, po, e);
		} finally {	
			logger.debug("Closing execution");
			close(ps,con);
		}
	}
	
	public ResultSet dbExecuteQueryPrepStmt(String query, ParameterObject po) {
		logger.debug("Executing query with prepared statement: '{}' with values {}", query, po);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			
			ps.setString(1,po.getValue1());
			ps.setString(2,po.getValue2());
			rs = ps.executeQuery();
			
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		} catch (SQLException e) {
			logger.error("Error occured during the execution of the query with prepared statement: '{}' with values {}", query, po, e);
		} finally {	
			logger.debug("Closing execution");
			close(rs,ps,con);
		}
		
		return crs;
	}
}
