package waterfall.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.sun.rowset.CachedRowSetImpl;

import waterfall.model.Message;

public class DBUtil {
	private DataSource dataSource;
	
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
			e.printStackTrace();
		} finally {
			close(rs, stmt, con);
		}
		
		return crs;
		
	}
	
	public void dbExecuteUpdate(String query) {
		Connection con = null;
		Statement stmt = null;
		
		try {
			con = dataSource.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e ) {
			e.printStackTrace();
		} finally {
			close(stmt, con);
		}
	}
	
	public void dbExecuteUpdatePrepStmt(String query, Message msg) {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			
			ps.setString(1,msg.getMessage());
			ps.setString(2, msg.getAuthor());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps,con);
		}
	}
}
