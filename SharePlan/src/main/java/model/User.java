package model;

import java.io.Serializable;

/*
 * ユーザーに関する情報を持つJavaBeans
 */
public class User implements Serializable {
	private String user_name;	//ユーザー名
	private String password;	//パスワード
	private String group_name;	//グループ名

	public User() {}

	public User(String user_name, String password) {
		this.user_name = user_name;
		this.password = password;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getPassword() {
		return password;
	}
}
