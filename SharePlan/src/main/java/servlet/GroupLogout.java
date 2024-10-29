package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * ログアウトに関するリクエストを処理するコントローラ
 */
@WebServlet("/GroupLogout")
public class GroupLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションスコープを破棄
        HttpSession session = request.getSession(false); // 既存のセッションを取得（無ければnull）
        
        if (session != null) {
        	session.removeAttribute("loginGroup");
        	session.removeAttribute("chatList");
            System.out.println("グループのセッションオブジェクト破棄完了");
        } else {
            System.out.println("グループセッションは存在しない");
        }

		// 個人画面にリダイレクト
		response.sendRedirect("Main");
	}
}
