package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Todolist;

public class TodolistDAO {
	private final String URL = "jdbc:postgresql://localhost:5432/shareplan";
	private final String USER = "postgres";
	private final String PASSWORD = "test";

	public TodolistDAO() {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ユーザーネームをもとにTodoリストの取得
	 */
	public List<Todolist> getTodoList(String user_name) {
		List<Todolist> todoList = new ArrayList<>();
		String sql = "SELECT * FROM todo WHERE user_name = ? ORDER BY todo_day ASC;";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setString(1, user_name);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String list = rs.getString("list");
				Timestamp timestamp = rs.getTimestamp("todo_day");
		        LocalDateTime localDateTime = timestamp.toLocalDateTime();
		        LocalDateTime truncatedNow = localDateTime.withSecond(0).withNano(0);
		        Timestamp todo_day = Timestamp.valueOf(truncatedNow);
		        String Todo_day = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(todo_day);
				boolean complete = rs.getBoolean("complete");

				Todolist todo = new Todolist(id, user_name, list, Todo_day, complete);
				todoList.add(todo);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return todoList;
	}

	/*
	 * 追加SQL
	 */
	public boolean insertTodo(String user_name, String list, Timestamp todo_day) {
		String sql = "INSERT INTO todo (user_name,list,todo_day,complete) VALUES (?,?,date_trunc('second', cast( ? as timestamp))  ,false);";
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setString(1,user_name);
			st.setString(2,list);
			st.setTimestamp(3,todo_day);
			
			st.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
//	削除
	public boolean delete(int id) {
		
		System.out.println(id);
		String sql = "DELETE FROM todo WHERE id = ?;";
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			st.setInt(1, id);
			
			st.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

		return true;
		
	}
	
//	編集前データ確保
	public List<Todolist> getUpdate(int id) {

		List<Todolist> todoList = new ArrayList<>();

		String sql = "SELECT id ,user_name,list, todo_day,complete FROM todo WHERE id = ? ;";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int list_id = rs.getInt("id");
				String user_name = rs.getString("user_name");
				String list = rs.getString("list");
				Timestamp todo_day = rs.getTimestamp("todo_day");
				

				boolean complete = rs.getBoolean("complete");
				
				Todolist todo = new Todolist(list_id, user_name, list, todo_day,complete);
				todoList.add(todo);
				
			}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return todoList;
			}
//	編集
	public boolean setUpdate(int id,String list,Timestamp todo_day) {
		
		String sql = "UPDATE todo SET list = ? ,todo_day  = ? WHERE id = ? ;";
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			st.setString(1, list);
			st.setTimestamp(2, todo_day);
			st.setInt(3,id );
			st.executeUpdate();
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	/*
	 * IDからCompleteの値の取り出し
	 */
	public boolean getComplete(int id){
		boolean complete = true;
		
		String sql = "SELECT complete FROM todo WHERE id = ? ;";
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setInt(1, id);

			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				complete = rs.getBoolean("complete");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complete;
	}
	
	/*
	 * IDから行の編集
	 */
	public boolean setComplete(int id) {
		
		boolean complete = getComplete(id);
		
		String sql = "UPDATE todo SET complete = ? WHERE id = ? ;";
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {
			if(complete) {
				complete = false;
			}else {
				complete = true;
			}
			st.setBoolean(1, complete);
			st.setInt(2, id);

			st.executeUpdate();
			System.out.println(complete);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return true;
	}
}
