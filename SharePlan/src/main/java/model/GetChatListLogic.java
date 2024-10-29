package model;

import java.util.List;

import dao.ChatDAO;

public class GetChatListLogic {
	/**
	 * チャット内容の取得に関する処理を行うモデル
	 */
	public List<ChatData> execute(String loginGroup_name) {
		// chatテーブルから全てのチャット情報を取得
		ChatDAO dao = new ChatDAO();//chatテーブルと接続
		List<ChatData> chatList = dao.findAll(loginGroup_name);//chatテーブルの内容をリストに保管

		return chatList;
		}
}
