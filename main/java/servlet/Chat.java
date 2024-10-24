package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ChatData;
import model.GetChatListLogic;
import model.Group;
import model.PostChatLogic;
import model.User;

/*
 * つぶやきに関するリクエストを処理するコントローラ
 */
@WebServlet("/Chat")
public class Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String chat = request.getParameter("chat");
		
		// セッションスコープに保存された情報を取得
		HttpSession session = request.getSession();
		//ユーザー名をセッションスコープから取得
		String loginUser_name = (String)session.getAttribute("loginUser");
		System.out.println("loginUser_name確認" + loginUser_name);
										
		//グループ名を取得：2回目は退室した後、入室した際に上書きされる。
		String loginGroup = (String)session.getAttribute("loginGroup");
		System.out.println("loginGroup確認:" + loginGroup);		
		
		
		// 入力値チェック
		if (chat != null && chat.length() != 0 && chat.length() <= 100) {//chat情報が入っている、100文字以下の場合登録可
			System.out.println("if文入る" + chat);
			
			// チャット内容をChatテーブルへ1件追加する
			ChatData chatData = new ChatData(loginUser_name, loginGroup, chat);
			PostChatLogic postChatLogic = new PostChatLogic();
			boolean chatlogic = postChatLogic.execute(chatData);
			
			if(!chatlogic) {//chatlogicがfalseの時：データベース上でのエラーの時
				// エラーメッセージをリクエストスコープに保存
				request.setAttribute("errorMsg", "メッセージが投稿できませんでした");
			}
			
		}else if(chat.length() >= 101){//chat情報が100文字を超えていたらエラーメッセージを表示
			// エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg", "100文字以下で入力してください。改行も1文字に含まれます。");
		}else if(chat == null || chat.length() == 0){
			// エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg", "内容を入力してください。");
		}
		
		// 最新のチャットリストを取得して、リクエストスコープに保存
		//チャット内容をデータベースから取得しリストに保管
		GetChatListLogic getChatListLogics = new GetChatListLogic();
		List<ChatData> chatList = getChatListLogics.execute(loginGroup);
		request.setAttribute("chatList", chatList);						
		
		// メイン画面にフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/sharePage.jsp");
		dispatcher.forward(request, response);
		
	}
}
