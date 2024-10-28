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
										
		//グループ名を取得
		String loginGroup = (String)session.getAttribute("loginGroup");
		System.out.println("loginGroup確認:" + loginGroup);	
		
		//エラーメッセージをリセット
		session.removeAttribute("errorMsg");
		
		
		// 入力値チェック
		if (chat != null && chat.length() != 0 && chat.length() <= 100) {//chat情報が入っている、100文字以下の場合登録可
			
			// チャット内容をChatテーブルへ1件追加する
			ChatData chatData = new ChatData(loginUser_name, loginGroup, chat);
			PostChatLogic postChatLogic = new PostChatLogic();
			boolean chatlogic = postChatLogic.execute(chatData);
			
			if(!chatlogic) {//chatlogicがfalseの時：データベース上でのエラーの時
				// エラーメッセージをセッションスコープに保存
				session.setAttribute("errorMsg", "メッセージが投稿できませんでした");
			}
			
		}else if(chat.length() >= 101){//chat情報が100文字を超えていたらエラーメッセージを表示
			// エラーメッセージをセッションスコープに保存
			session.setAttribute("errorMsg", "100文字以下で入力してください。改行も1文字に含まれます。");
		}else if(chat == null || chat.length() == 0){
			// エラーメッセージをセッションスコープに保存
			session.setAttribute("errorMsg", "内容を入力してください。");
		}
		
		// 最新のチャットリストを取得して、スコープに保存
		//チャット内容をデータベースから取得しリストに保管
		GetChatListLogic getChatListLogics = new GetChatListLogic();
		List<ChatData> chatList = getChatListLogics.execute(loginGroup);
		session.setAttribute("chatList", chatList);						
		
		// sharePage画面にリダイレクト
		response.sendRedirect("Schedule");
		
	}
}
