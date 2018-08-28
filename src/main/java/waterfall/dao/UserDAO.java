package waterfall.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import waterfall.model.ParameterObject;
import waterfall.util.DBUtil;

public class UserDAO {
	private DBUtil dbutil;
	
	public UserDAO(DataSource dataSource) {
		dbutil = new DBUtil(dataSource);
	}
	
	public boolean checkByNameAndPass(String name, String password) {
		String query = "SELECT * FROM users WHERE name=? and password=?";
		ParameterObject po = new ParameterObject();
		po.setValue1(name);
		po.setValue2(password);
		
		ResultSet rs = dbutil.dbExecuteQueryPrepStmt(query, po);
		
		try {
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
