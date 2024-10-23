package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GroupDAO;
import model.Group;
import model.GroupLoginLogic;

/*
 * ログインに関するリクエストを処理するコントローラ
 */
@WebServlet("/GroupLogin")
public class GroupLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		GroupDAO groupdao = new GroupDAO();

		// groupListをアプリケーションスコープから取得
		ServletContext application = getServletContext();
		List<Group> groupList = (List<Group>) application.getAttribute("groupList");

		// なければグループリストを作成して全件読み込み
		if (groupList == null) {
			groupList = groupdao.selectAll();
			application.setAttribute("groupList", groupList);
		}

		//ユーザから入力された情報
		String input_group_name = request.getParameter("group_login");

		// ログイン処理
		if (input_group_name == null || input_group_name.trim().isEmpty()) {
			request.getSession().setAttribute("group_error_message", "グループ名を入力してください");
			
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/Main");
//			dispatcher.forward(request, response);
			response.sendRedirect("Main");
			return;
		}

		// ログインプロセス
		GroupLoginLogic groupLoginLogic = new GroupLoginLogic();
		boolean isGroupLogin = groupLoginLogic.execute(input_group_name, groupList);
		System.out.println(isGroupLogin); //debug用

		// ログイン成功時の処理
		if (isGroupLogin) {
			// グループ名をセッションスコープに保存
			HttpSession session = request.getSession();
			session.setAttribute("loginGroup", input_group_name);

			response.sendRedirect("Schedule");
		} else {
			
			request.getSession().setAttribute("group_error_message", "グループ名が一致しません");
			
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/Main");
//			dispatcher.forward(request, response);
			response.sendRedirect("Main");
		}

	}
}
