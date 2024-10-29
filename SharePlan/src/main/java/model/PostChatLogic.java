package model;

/*
 * チャットの投稿に関する処理を行うモデル
 */

import dao.ChatDAO;

public class PostChatLogic {
	public boolean execute(ChatData chatData) {
		
		ChatDAO dao = new ChatDAO();
		return dao.create(chatData);
	}
}
