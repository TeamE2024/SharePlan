package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class User_infDAO {
	private final String URL = "jdbc:postgresql://localhost:5432/shareplan";
	private final String USER = "postgres";
	private final String PASSWORD = "test";

	// コンストラクタ
	public User_infDAO() {
		/* JDBCドライバの準備 */
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * user_infテーブルから全てのデータを検索
	 */
	public List<User> selectAll() {
        /* 1) SQL文の準備 */
		String sql = "SELECT * FROM user_inf;";

		List<User> userList = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 4) 結果をリストに移し替える */
			userList = makeUserList(rs);

 		} catch (Exception e) {
			e.printStackTrace();
		}

 		return userList;
    }

	/*
	 * user_infテーブルからユーザ名とパスワードの一致情報を返す
	 */
	
	public boolean findByLogin(User user) {

		String sql = "SELECT * FROM user_inf WHERE user_name=? AND password =?;";
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pStmt = con.prepareStatement(sql);) {

			pStmt.setString(1, user.getUser_name());
			pStmt.setString(2, user.getPassword());

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * user_infテーブルからグループ名の情報を返す
	 */
	
	public boolean findByName(String user_name) {
		String sql = "SELECT User_name FROM user_inf WHERE user_name =?;";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pStmt = con.prepareStatement(sql);) {

			pStmt.setString(1, user_name);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				return false;

			} else {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * user_infテーブルにユーザ名とパスワードを追加します。
	 * （PreparedStatement方式）
	 */
	public boolean userRegister(User user) {
		String sql = "INSERT INTO user_inf(user_name,password) VALUES (?,?);";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pStmt = con.prepareStatement(sql);) {

			pStmt.setString(1, user.getUser_name());
			pStmt.setString(2, user.getPassword());

			int regCnt = pStmt.executeUpdate();

			if (regCnt == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/*
	 * 検索結果をリストで返す
	 */
	public List<User> makeUserList(ResultSet rs) throws Exception {
		// 全検索結果を格納するリストを準備
		List<User> userList = new ArrayList<User>();

		while (rs.next()) {
			// 1行分のデータを読込む
			String user_name = rs.getString("user_name");
			String password = rs.getString("password");
	        
			User user = new User(user_name, password);

			// リストに1行分のデータを追加する
			userList.add(user);
		}
		return userList;
	}

}
