package model;

import java.io.Serializable;
import java.sql.Timestamp;
/*
 * chatテーブルのJavaBeans
 */
public class ChatData implements Serializable{

	private int id;
	private String user_name;
	private String group_name;
	private String chat;
	private Timestamp chat_day;

	
	public ChatData() {}
	
	//Chat用
	public ChatData(String user_name, String group_name, String chat) {
		this.user_name = user_name;
		this.group_name = group_name;
		this.chat = chat;
	}
	
	//Chat用
	public ChatData(int id, String user_name, String group_name, String chat, Timestamp chat_day) {
		this.id = id;
		this.user_name = user_name;
		this.group_name = group_name;
		this.chat = chat;
		this.chat_day = chat_day;
	}


	public int getId() {
		return id;
	}


	public String getUser_name() {
		return user_name;
	}


	public String getChat() {
		return chat;
	}

	public Timestamp getChat_day() {
		return chat_day;
	}

	public String getGroup_name() {
		return group_name;
	}
	
	
}
