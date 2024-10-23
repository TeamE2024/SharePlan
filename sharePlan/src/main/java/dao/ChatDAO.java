package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.ChatData;

public class ChatDAO {	
	/*
	 * shareplanテーブルを担当するDAO
	 */
		private final String URL = "jdbc:postgresql://localhost:5432/shareplan";
		private final String USER = "postgres";
		private final String PASSWORD = "test";

		// コンストラクタ
		public ChatDAO() {
			/* JDBCドライバの準備 */
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * Chatテーブルへチャット内容を1件追加する
		 */
	public boolean create(ChatData chatdata) {
		
		String sql = "INSERT INTO chat(user_name, group_name, chat, chat_day) ";
		sql += "VALUES(?, ?, ?, date_trunc('second',CURRENT_TIMESTAMP)); ";
		System.out.println("INSERT文の確認：" + sql);
		
	/* 2) PostgreSQLへの接続 */
	try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
		PreparedStatement pStmt = con.prepareStatement(sql);) {

		/* 3) SQL文の?部分を置き換え */
		pStmt.setString(1, chatdata.getUser_name());
		pStmt.setString(2, chatdata.getGroup_name());
		pStmt.setString(3, chatdata.getChat());
		

		/* 4) SQL文の実行 */
		int result = pStmt.executeUpdate();
		if (result != 1) {	// 追加件数が1ではない = 追加に失敗したということ
			return false;
		}
	} catch (Exception e) {
		e.printStackTrace();

		return false;
	}

	return true;
	}
	
	
	/**
	 * Chatテーブルからグループ名を条件として全レコードを取得する
	 */
	public List<ChatData>findAll(String loginGroup_name) {
		List<ChatData> chatList = new ArrayList<>();

		/* 1) SQL文の準備 */
		String sql = "";
		sql += "SELECT * ";
		sql	+= "FROM chat ";
		sql	+= "WHERE group_name = ? ";
		sql	+= "ORDER BY ID DESC; ";
		
		//SQL文確認用
		System.out.println("findAll():確認"+ sql);
				
		/* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement pStmt = con.prepareStatement(sql);) {

 			pStmt.setString(1, loginGroup_name);
 			
		    /* SELECT文の実行 */
			ResultSet rs = pStmt.executeQuery();

			/* 結果をリストに移し替える */
			while (rs.next()) {
				int id = rs.getInt("id");
				String user_name = rs.getString("user_name");
				String group_name = rs.getString("group_name");
				String chat = rs.getString("chat");
				Timestamp chat_day = rs.getTimestamp("chat_day");
				
				
				//内容確認用
				System.out.println(id);
				System.out.println(user_name);
				System.out.println(chat);
				System.out.println(chat_day);
				
				ChatData chatdata = new ChatData(id, user_name, group_name, chat, chat_day);
				chatList.add(chatdata);
			}
		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

		return chatList;
	}
	
	
	
}