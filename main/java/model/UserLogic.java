package model;

import dao.User_infDAO;

public class UserLogic {
	public boolean login(String user_name, String password) {
		User user = new User(user_name, password);

		User_infDAO user_infDAO = new User_infDAO();

		return user_infDAO.findByLogin(user);

	}

	public boolean logincheck(String user_name) {

		User_infDAO user_infDAO = new User_infDAO();
		
		return user_infDAO.findByName(user_name);

	}

	public boolean register(String user_name, String password) {

		User user = new User(user_name, password);

		User_infDAO user_infDAO = new User_infDAO();

		return user_infDAO.userRegister(user);

	}

}
