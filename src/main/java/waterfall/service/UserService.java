package waterfall.service;

import javax.sql.DataSource;

import waterfall.dao.UserDAO;

public class UserService {
	private UserDAO userDao;
	
	public UserService(DataSource dataSource) {
		this.userDao = new UserDAO(dataSource);
	}
	
	public boolean checkByNameAndPass(String name, String password) {
		 return userDao.checkByNameAndPass(name, password);
	}
}
