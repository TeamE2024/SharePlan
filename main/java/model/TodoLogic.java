package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import dao.TodolistDAO;
/*
 * 処理を行うクラス
 */
public class TodoLogic {

	/*
	 * 追加
	 */
	public boolean input(String list,String todo_Day,String user_name) {
		TodolistDAO dao = new TodolistDAO();
		//StringからLocalDateTimeへ変更
		LocalDateTime timestamp = LocalDateTime.parse(todo_Day);
		//LocalDateTimeからTimestampへ変更
		Timestamp todo_day = Timestamp.valueOf(timestamp);
		//データベース処理(戻り値boolean)
		return dao.insertTodo(user_name,list, todo_day);

	}
	
	/*
	 * TodoListの取得
	 */
	public List<Todolist> getTodo(String user_name) {
		TodolistDAO dao = new TodolistDAO();
		return dao.getTodoList(user_name);
	}
	
	/*
	 * 
	 */
	public List<Todolist> getEditData(String select) {
		int id = Integer.parseInt(select);
		TodolistDAO dao = new TodolistDAO();
		return dao.getUpdate(id);
	}
	
	/*
	 * 削除
	 */
	public boolean delete(String select) {
		int id = Integer.parseInt(select);
		TodolistDAO dao = new TodolistDAO();
		return dao.delete(id);
	}
	

	/*
	 * 編集登録
	 */
	public boolean edit(String select,String list,String strTodo_day) {
		int id = Integer.parseInt(select);
		//String→LocalDateTime
		LocalDateTime timestamp = LocalDateTime.parse(strTodo_day);
		//LocalDateTime→Timestamp
		Timestamp todo_day = Timestamp.valueOf(timestamp);
		TodolistDAO dao = new TodolistDAO();
		return dao.setUpdate(id, list, todo_day);
	}
	
	/*
	 * IDをもとに達成処理の更新
	 */
	public void completeCheck(String complete) {
		int id = Integer.parseInt(complete);
		TodolistDAO dao = new TodolistDAO();
		dao.setComplete(id);
	}
}
