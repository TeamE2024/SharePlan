package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserLogic;

@WebServlet("/UserRegister")
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログイン結果画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_register.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("user_name");
		String pass = request.getParameter("password");
		String msg = "";

		if (name == null || name.length() == 0) {
			msg += "ユーザー名が入力されておりません<br>";

		}
		if (pass == null || pass.length() == 0) {
			msg += "パスワードが入力されておりません<br>";

		}

		if (name != null && name.length() != 0) {
			if (name.length() < 4 || name.length() > 16) {
				msg += "ユーザー名は4文字以上、16文字以下で入力してください<br>";
			}

		}

		if (pass != null && pass.length() != 0) {
			if (pass.length() < 4 || pass.length() > 8) {
				msg += "パスワード4文字以上、8文字以下で入力してください<br>";
			}

		}

		if (msg.isEmpty()) {
			UserLogic userLogic = new UserLogic();

			boolean namecheck = userLogic.logincheck(name);

			if (namecheck) {
				//登録処理
				boolean finalcheck = userLogic.register(name, pass);

				if (finalcheck) {
					msg += "ユーザー登録しました<br>";
				} else {
					msg += "ユーザー登録出来ませんでした<br>";
				}
			}else {
				msg += "既に登録されているユーザー名です<br>";
			}

			

		}

		request.setAttribute("msg", msg);

		// ログイン結果画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_register.jsp");
		dispatcher.forward(request, response);

	}

}
