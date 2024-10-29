package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.User_infDAO;
import model.User;
import model.UserLogic;

/*
 * ログインに関するリクエストを処理するコントローラ
 */
@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		User_infDAO userdao = new User_infDAO();

		String name = request.getParameter("user_name");
		String pass = request.getParameter("password");
		String path = "";
		String msg = "";

		//userListをアプリケーションスコープから取得
		ServletContext application_user = getServletContext();
		List<User> userList = (List<User>) application_user.getAttribute("userList");

		// なければユーザリストを作成して全件読み込み		
		if (userList == null) {
			userList = userdao.selectAll();
			application_user.setAttribute("userList", userList);
		}
		//エラーハンドリング
		if (name == null || name.length() == 0) {
			msg += "ユーザー名が入力されておりません<br>";

			//			request.setAttribute("msg", );
		}
		if (pass == null || pass.length() == 0) {
			msg += "パスワードが入力されておりません<br>";

		}

		if (msg.isEmpty()) {
			// ログイン処理
			UserLogic userLogic = new UserLogic();
			boolean login = userLogic.login(name, pass);

			// ログイン成功時の処理
			if (login) {
				//ユーザ名をセッションスコープに保存
				HttpSession Usersession = request.getSession();
				Usersession.setAttribute("loginUser", name);
				System.out.println(Usersession.getAttribute("loginUser"));
				response.sendRedirect("Main");
				return;
			} else {
				msg += "ユーザー名またはパスワードが間違っています";
				path = "index.jsp";
			}
		} else {
			path = "index.jsp";

		}

		request.setAttribute("msg", msg);

		// ログイン結果画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

}
