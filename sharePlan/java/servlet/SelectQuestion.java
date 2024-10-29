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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ServletContext application = getServletContext();
		
		session.removeAttribute("Emsg");
		
		//ユーザー名の取得
		String user_name = (String)session.getAttribute("loginUser");
		user_name ="mm";
		
		//選択された質問の取得
		String select = request.getParameter("select");
		
		//ラジオボタンが選択されてない時
		if (select == null || select.length() == 0) {
			session.setAttribute("Emsg", "選択されていません");
		
		//ラジオボタンを選択された時 
		}else {
			//ユーザー名と質問を保持するMap
			Map<String, String> countUser = (Map<String, String>)application.getAttribute("countUser");
			
			//質問とQandA(選択肢:投票数)を保持するMap
			Map<String,Map<String,Integer>> countMap = (Map<String,Map<String,Integer>>)application.getAttribute("countMap");
			//質問
			String question = (String)session.getAttribute("question");
			
			//countUserの存在の確認／無ければ生成・Mapにユーザー名と選択肢を入れる
			if (countUser == null) {
				countUser = new HashMap<String,String>();
				countUser.put(user_name, question);
				application.setAttribute("countUser", countUser);
				
				//質問がMapに存在するか&&その質問の中に選択された選択肢があるか
				if (countMap.containsKey(question) && countMap.get(question).containsKey(select)) {
		            int count = countMap.get(question).get(select);
		            countMap.get(question).put(select, count + 1);
		        } 
			} else {
				//ユーザー名と質問を保持するマップのユーザー名の確認
				if (countUser.containsKey(user_name) && countUser.get(user_name) == question) {
					session.setAttribute("Emsg", "既に投票済みです");
					
				} else {
					countUser.put(user_name, question);
					application.setAttribute("countUser", countUser);
					
					//質問がMapに存在するか&&その質問の中に選択された選択肢があるか
					if (countMap.containsKey(question) && countMap.get(question).containsKey(select)) {
			            int count = countMap.get(question).get(select);
			            countMap.get(question).put(select, count + 1);
			        } 
				}
			}
			application.setAttribute("countMap", countMap);
		}

		response.sendRedirect("makeQuestion.jsp");
	}
}
