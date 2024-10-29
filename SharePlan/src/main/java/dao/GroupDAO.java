package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Group;

/*
 * group_infテーブルへのアクセスを担当する
 * DAOクラスです。
 */
public class GroupDAO {
    private final String URL = "jdbc:postgresql://localhost:5432/shareplan";
    private final String USER = "postgres";
    private final String PASSWORD = "test";

	// コンストラクタ
	public GroupDAO() {
        /* JDBCドライバの準備 */
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * group_infテーブルから全てのデータを検索
	 */
	public List<Group> selectAll() {
        /* 1) SQL文の準備 */
		String sql = "SELECT * FROM group_inf;";

		List<Group> groupList = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 4) 結果をリストに移し替える */
			groupList = makeGroupList(rs);

 		} catch (Exception e) {
			e.printStackTrace();
		}

 		return groupList;
    }
	
	/*
	 * group_infテーブルからグループ名の情報を返す
	 */
	public List<Group> selectGroupMei(String group_name){
		/* 1) SQL文の準備 */
		String sql = "SELECT group_name FROM group_inf WHERE group_name = ?;";

		List<Group> groupList = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, group_name); //※入力された文字列を引っ張ってくる
 			
			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果をリストに移し替える */
			groupList = makeGroupList(rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
 		return groupList;
	}

	/*
	 * group_infテーブルにグループ名を追加します。
	 * （PreparedStatement方式）
	 */
	public void insertGroup(String group_name) {
		/* 1) SQL文の準備 */
		String sql = "INSERT INTO group_inf (group_name) VALUES( ? );";

        /* 2) PostgreSQLへの接続 */
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, group_name);

			/* 4) SQL文の実行 */
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/*
	 * 検索結果をリストで返す
	 */
	public List<Group> makeGroupList(ResultSet rs) throws Exception {
		// 全検索結果を格納するリストを準備
		List<Group> groupList = new ArrayList<Group>();

		while (rs.next()) {
			// 1行分のデータを読込む
			String group_name = rs.getString("group_name");
	        
			Group group = new Group(group_name);

			// リストに1行分のデータを追加する
			groupList.add(group);
		}
		return groupList;
	}
}