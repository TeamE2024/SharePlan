package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.ScheduleData;
/*
 * DB接続クラス
 */
public class ScheduleDAO {

	private final String URL = "jdbc:postgresql://localhost:5432/shareplan";
	private final String USER = "postgres";
	private final String PASS = "test";
	
	public ScheduleDAO() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 予定追加SQL
	 */
	public boolean input(String com,Date date,Time time,String group_name) {
		String sql = "";
		sql += "INSERT INTO schedule (group_name,plan,plan_day,plan_time) VALUES (?,?,?,?);";
		try (Connection con = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			st.setString(1,group_name);
			st.setString(2,com);
			st.setDate(3, date);
			st.setTime(4, time);
			
			int input = st.executeUpdate();
			
			//1件の登録ができたときtrueを返す
			if(input == 1) {
				return true;
			}else {
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("DB接続時にエラー発生");
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * 予定取得SQL
	 */
	public List<ScheduleData> getAllPlan(String group_name){
		List<ScheduleData> planList = null;
		String sql = "SELECT * FROM schedule WHERE group_name = ? ORDER BY plan_day;";
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			st.setString(1,group_name);
			
			//SQLの実行
			ResultSet rs = st.executeQuery();
			planList = inputList(rs);
			return planList;
			
		} catch (Exception e) {
			System.out.println("DB接続時にエラー発生");
			e.printStackTrace();
			return planList;
		}
	}
	
	/*
	 * 取得した予定のリスト生成
	 */
	public List<ScheduleData> inputList(ResultSet rs) throws Exception{
		List<ScheduleData> planList = new ArrayList<>();

		while(rs.next()) {
			int id = rs.getInt("id");
			String group_name = rs.getString("group_name");
			String plan = rs.getString("plan");
			Date plan_day = rs.getDate("plan_day");
			Time time = rs.getTime("plan_time");
			LocalTime plan_time = time.toLocalTime();
			ScheduleData data = new ScheduleData(id,group_name,plan,plan_day,plan_time);
			planList.add(data);
		}
		
		return planList;
	}
	
	/*
	 * 削除SQL
	 */
	public boolean deleteDAO(int[] idArray) {
		String sql = "";
		for(int i = 0; i< idArray.length; i++) {
			sql += "DELETE FROM schedule WHERE id = ?; ";
		}
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement st = con.prepareStatement(sql);) {
		
			int QmarkCount = 1;
			for(int i = 0; i < idArray.length; i++) {
				st.setInt(QmarkCount,idArray[i]);
				QmarkCount++;
			}
			
			int input = st.executeUpdate();
			System.out.println(input);
			
			if (input == 1) {
				return true;
				
			}else {
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("DB接続時にエラー発生");
			e.printStackTrace();
			return false;
		}	
	}
	
	/*
	 * 変更SQL
	 */
	public boolean updatePlan(int id,String com,Date date,Time time) {
		 String sql = "UPDATE schedule ";
		 sql += "SET plan = ? , plan_day = ? , plan_time = ? ";
		 sql += "WHERE id = ?;";
		 
		 try (Connection con = DriverManager.getConnection(URL, USER, PASS);
					PreparedStatement st = con.prepareStatement(sql);) {

				st.setString(1,com);
				st.setDate(2, date);
				st.setTime(3, time);
				st.setInt(4, id);
				
				int input = st.executeUpdate();
				
				//1件の登録ができたときtrueを返す
				if(input == 1) {
					return true;
				}else {
					return false;
				}
				
			} catch (Exception e) {
				System.out.println("DB接続時にエラー発生");
				e.printStackTrace();
				return false;
			}
		 
	}
	

	
}
