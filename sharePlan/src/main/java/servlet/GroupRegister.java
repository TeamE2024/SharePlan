package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GroupDAO;
import model.Group;
import model.GroupCreateLogic;

/*
 * ユーザ登録に関するリクエストを処理するコントローラ
 */
@WebServlet("/GroupRegister")
public class GroupRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		GroupDAO groupDao = new GroupDAO();
		String input_group_name = request.getParameter("group_create"); //ユーザから入力された情報
		
		// groupListをアプリケーションスコープから取得
		ServletContext application = getServletContext();
		List<Group> groupList = (List<Group>) application.getAttribute("groupList");

		// なければグループリストを作成して全件読み込み
		if (groupList == null) {
			groupList = groupDao.selectAll();
			application.setAttribute("groupList", groupList);
		}

		// 正規表現パターン
		boolean hasUpperCase = input_group_name.matches(".*[A-Z].*"); // 大文字
		boolean hasLowerCase = input_group_name.matches(".*[a-z].*"); // 小文字
		boolean hasDigit = input_group_name.matches(".*[0-9].*"); // 数字
		boolean hasSpecialChar = input_group_name.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"); // 記号

		//ShohinDAOのメソッドを利用してデータベースへの登録を行う
		StringBuilder group_error_messageSb = new StringBuilder();

		if (input_group_name.isEmpty()) {
			group_error_messageSb.append("Group名を入力してください<br>");
		} else if (input_group_name.length() > 16 || input_group_name.length() < 10) {
			group_error_messageSb.append("Group名は10文字以上16文字以下で入力してください<br>");
		}

		if (!hasUpperCase) {
			group_error_messageSb.append("Group名に大文字を含んでください<br>");
		}
		if (!hasLowerCase) {
			group_error_messageSb.append("Group名に小文字を含んでください<br>");
		}
		if (!hasDigit) {
			group_error_messageSb.append("Group名に数字を含んでください<br>");
		}
		if (!hasSpecialChar) {
			group_error_messageSb.append("Group名に記号を含んでください<br>");
		}
		if(!GroupCreateLogic.execute(input_group_name, groupList)) {
			group_error_messageSb.append("既にグループが存在します");
		}

		//エラーメッセージがある場合はフォワード
		if (group_error_messageSb.length() != 0) {
	        request.getSession().setAttribute("group_error_message", group_error_messageSb.toString());
			response.sendRedirect("Main");
			//			RequestDispatcher dispatcher = request.getRequestDispatcher("/Main");
//			dispatcher.forward(request, response);
			return;
		}

		
		// グループ登録後の処理
		if (group_error_messageSb.length() == 0) {
		    try {
		        groupDao.insertGroup(input_group_name);
		        // 新しいグループをアプリケーションスコープに追加
		        groupList.add(new Group(input_group_name));
		        application.setAttribute("groupList", groupList); // 更新をアプリケーションスコープに反映
		        
		        group_error_messageSb.append("登録が完了しました<br>");
		        request.getSession().setAttribute("group_error_message", group_error_messageSb.toString());
		    } catch (Exception e) {
		        request.setAttribute("group_error_message", "登録処理時に問題が発生しました");
		        e.printStackTrace(); // エラーログに詳細を記録
		    }
		}
		response.sendRedirect("Main");
//		// フォワード処理
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/Main");
//		dispatcher.forward(request, response);
	}

}