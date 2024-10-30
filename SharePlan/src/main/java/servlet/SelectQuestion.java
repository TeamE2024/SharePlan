package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SelectQuestion")
public class SelectQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * 投票処理サーブレット
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ServletContext application = getServletContext();

		session.removeAttribute("Emsg");

		//ユーザー名の取得
		String user_name = (String) session.getAttribute("loginUser");

		//選択された選択肢の取得
		String select = request.getParameter("select");

		//ラジオボタンが選択されてない時
		if (select == null || select.length() == 0) {
			session.setAttribute("Emsg", "選択されていません");

			//ラジオボタンを選択された時 
		} else {
			//ユーザー名と質問を保持するMap
			Map<String, String> countUser = (Map<String, String>) application.getAttribute("countUser");

			//質問とQandA(選択肢:投票数)を保持するMap
			Map<String, Map<String, Integer>> countMap = (Map<String, Map<String, Integer>>) application.getAttribute("countMap");
			//質問
			String question = (String) session.getAttribute("question");

			//countUserの存在の確認／無ければ生成・Mapにユーザー名と選択肢を入れる
			if (countUser == null) {
				countUser = new HashMap<String, String>();
				countUser.put(user_name, question);
				application.setAttribute("countUser", countUser);
				System.out.println("countUserMapがnull");

				//質問と選択肢から投票数を取得
				if (countMap != null) {
					Map<String, Integer> QandA = countMap.get(question);
					System.out.println("countMapがnullじゃない");
					if (QandA != null && QandA.get(select) != null) {
						System.out.println("countMapのQandAがnullじゃなくてQandAに質問ある");
						int count = QandA.get(select);
						//投票数をプラス１する
						QandA.put(select, count + 1);
						application.setAttribute("countMap", countMap);
						
					}
				}
			// 初回投票
			} else if (countUser != null && !countUser.containsKey(user_name)) {
				if (countMap != null) {
					Map<String, Integer> QandA = countMap.get(question);
					System.out.println("countUserがnullじゃなくてcountMapがnullじゃない");
					if (QandA != null && QandA.get(select) != null) {
						System.out.println("countUserがnullじゃなくてcountMapのQandAがnullじゃなくてQandAに質問ある");
						int count = QandA.get(select);
						//投票数をプラス１する
						QandA.put(select, count + 1);
						application.setAttribute("countMap", countMap);
					}
				}
				countUser.put(user_name, question);
				application.setAttribute("countUser", countUser);
	
			} else {
				session.setAttribute("Emsg", "既に投票済みです");
			}
			
		}

		response.sendRedirect("Schedule");
	}
}
