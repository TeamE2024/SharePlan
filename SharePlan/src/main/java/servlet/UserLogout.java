package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserLogout
 */
@WebServlet("/UserLogout")
public class UserLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションスコープを破棄
		HttpSession loginUser = request.getSession(false); // 既存のセッションを取得（無ければnull）

		if (loginUser != null) {
			loginUser.invalidate(); // セッションを破棄
			System.out.println("ユーザセッション破棄完了");
		} else {
			System.out.println("ユーザセッションは存在しない");
		}

		// 個人画面にリダイレクト
		response.sendRedirect("index.jsp");
	}
}
