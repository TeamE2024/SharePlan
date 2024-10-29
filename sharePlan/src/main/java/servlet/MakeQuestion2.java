package servlet;

/*
 * アンケートサーブレット
 */
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.QuestionLogic;

@WebServlet("/MakeQuestion2")
public class MakeQuestion2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * アンケート作成submitボタンで呼ばれる
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ServletContext application = getServletContext();
		//メッセージの取得
		String Emsg = (String) session.getAttribute("Emsg");
		//"アンケート作成トリガー取得
		String makeQ = request.getParameter("makeQ");
		//質問と<選択肢,投票数>を保持するMapの取得
		Map<String, Map<String, Integer>> countMap = (Map<String, Map<String, Integer>>) application.getAttribute("countMap");

		//スコープ保持時2回目以降の"アンケート作成"を押下した時
		if (Emsg != null && countMap != null && makeQ != null) {
			if (!Emsg.equals("選択されていません") && !Emsg.equals("既に投票済みです")) {
				session.setAttribute("makeQ", makeQ);
				//削除処理
				application.removeAttribute("countMap");
				session.removeAttribute("Emsg");
				session.removeAttribute("question");
				session.removeAttribute("optionNumberList");
				application.removeAttribute("countUser");
			}else {
				Emsg = "現在の質問が消えますが、新しく質問されますか？";
				session.setAttribute("Emsg", Emsg);
			}
		} else {
			//2回目以降の"アンケート作成"を押下した時の警告文
			if (countMap != null) {
				Emsg = "現在の質問が消えますが、新しく質問されますか？";
				session.setAttribute("Emsg", Emsg);
			} else { //countMapがnull＝初期アンケート作成時
				session.setAttribute("makeQ", makeQ);
			}
		}
		response.sendRedirect("makeQuestion.jsp");

	}

	/*
	 * アンケート作成時の分岐
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ServletContext application = getServletContext();
		
		//分岐のパラメータ取得
		String submit = request.getParameter("submit");
		//質問に入力されたパラメータ取得
		String question = request.getParameter("question");
		//選択肢に入力されたパラメータを配列で取得
		String[] textArray = request.getParameterValues("text");

		//選択肢の個数が入ったリストの取得(選択肢枠を作成するために必要)
		List<Integer> optionNumberList = (List<Integer>) session.getAttribute("optionNumberList");
		
		Map<String, Map<String, Integer>> countMap = (Map<String, Map<String, Integer>>) application.getAttribute("countMap");
		QuestionLogic questionLogic = new QuestionLogic(countMap,optionNumberList);

		switch (submit) {
		
		case "選択肢追加":
			optionNumberList = questionLogic.addOption();
			session.setAttribute("optionNumberList", optionNumberList);
			session.setAttribute("question", question);
			session.removeAttribute("Emsg");
			break;
			
		case "選択肢削除":
			optionNumberList = questionLogic.removeOption();
			session.setAttribute("optionNumberList", optionNumberList);
			session.setAttribute("question", question);
			session.removeAttribute("Emsg");
			break;
			
		case "完了":
			String Emsg = questionLogic.completeQuestion(question, textArray);
			if (Emsg != null && Emsg.length() != 0) {
				session.setAttribute("Emsg", Emsg);
				session.setAttribute("question", question);
			} else {
				countMap = questionLogic.getCountMap();
				application.setAttribute("countMap", countMap);
				session.removeAttribute("makeQ");
			}
			break;
			
		case "確定":
			questionLogic.finalizeQuestion(question);
			countMap = questionLogic.getCountMap();
			application.setAttribute("countMap", countMap);
			session.removeAttribute("makeQ");
			session.removeAttribute("Emsg");
			break;
			
		default:
			session.setAttribute("Emsg", "不明なアクションです。");
			break;
		}

		// ビューへ遷移するためのロジック
		response.sendRedirect("makeQuestion.jsp");
	}
}
