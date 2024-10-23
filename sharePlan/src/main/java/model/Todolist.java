package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Todolist {
	private String user_name; //ユーザー
	private String list; //内容
	private int id; //listのid
	private Timestamp todo_day; //指定日
	private LocalDateTime timestamp;//指定日
	private String Todo_day;
	private Date date;
	private boolean complete;//完了

	public Todolist() {}

	public Todolist(int id, String user_name, String list, Timestamp todo_day, boolean complete) {
		this.user_name = user_name;
		this.list = list;
		this.todo_day = todo_day;
		this.id = id;
		this.complete = complete;
	}
	public Todolist(int id, String user_name, String list, String Todo_day, boolean complete) {
		this.user_name = user_name;
		this.list = list;
		this.Todo_day = Todo_day;
		this.id = id;
		this.complete = complete;
	}

	public Todolist(String list, Timestamp todo_day) {
		this.list = list;
		this.todo_day = todo_day;
	}

	public Todolist(int id, String user_name, String list, Timestamp todo_day) {
		this.user_name = user_name;
		this.list = list;
		this.todo_day = todo_day;
		this.id = id;
	}

	public Todolist(int id, String user_name, String list, Date date,boolean complete) {
		this.user_name = user_name;
		this.list = list;
		this.date = date;
		this.id = id;
		this.complete = complete;
	}

	public String getTodo_day() {
		return Todo_day;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getList() {
		return list;
	}

	public int getId() {
		return id;
	}

	public Timestamp gettodo_day() {
		return todo_day;
	}

	public boolean isComplete() {
		
		return complete;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public Date getDate() {
		return date;
	}

}
