package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.TodoLogic;
import model.Todolist;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		request.setCharacterEncoding("UTF-8");
		//ユーザー名の取得
		HttpSession session = request.getSession();
		String user_name = (String)session.getAttribute("loginUser");
		
		//処理クラスインスタンスの作成
		TodoLogic logic = new TodoLogic();

		// group_error_messageを取得
		String groupErrorMessage = (String) request.getSession().getAttribute("group_error_message");
		if (groupErrorMessage != null) {
			request.setAttribute("group_error_message", groupErrorMessage);
		        request.getSession().removeAttribute("group_error_message"); // クリアする
		}

		
	
		//ユーザー名をもとにTODOリストの取得
		List<Todolist> todoList = logic.getTodo(user_name);
		//セッションスコープへ登録
		session.setAttribute("todoList", todoList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		
		//ユーザー名の取得
		HttpSession session = request.getSession();
		String user_name = (String)session.getAttribute("loginUser");
		
		
		//ラジオボタンの戻り値value=todolist.getId()
		String select = request.getParameter("select");
		String done = request.getParameter("done");//追加・編集・削除・完了

		
		//submitボタンで戻り値はその列のID
		String getData = request.getParameter("getData");	//達成か達成済が選択された行IDの取得
		
		//処理メッセージの表示
		String message = "";
		
		//処理クラスのインスタンス作成
		TodoLogic logic = new TodoLogic();
		
		//submitボタンからの戻り値の分岐
		if (done != null) {
			switch (done) {

				case "追加":
					String list = request.getParameter("list");
					String todo_Day =request.getParameter("todo_day");
					
					//	エラーチェック
					if(list.length() != 0 && todo_Day.length() != 0) {
						boolean result = logic.input(list,todo_Day,user_name);
						
						if (result == true) {
							message += "追加が完了しました" + "<br>";
						}
					}else {
						message += "追加したい予定が未入力です<br>";

					}
					break;

				case "編集":
					if(select != null) {
						List<Todolist> editTodolist = logic.getEditData(select);
						
						String label = "before";
						request.setAttribute("label",label);
						
						for (Todolist todolist : editTodolist) {
							
							String beforeList = todolist.getList();
							LocalDateTime beforeDate = todolist.gettodo_day().toLocalDateTime();
								
							request.setAttribute("beforeList",beforeList);
							request.setAttribute("beforeDate",beforeDate);
						}

					} else {
						message += "選択されていません<br>";
					}
					break;
					
				case "削除":
					if (select != null) {
						boolean result = logic.delete(select);
						
						//削除の正否判定
						if (result == true) {
							message = "削除が完了しました" + "<br>";
						}
						
					} else {
						message += "選択されていません";
					}
						break;
						
				case "完了":
					String editList = (String) request.getParameter("list");
					String strTodo_day = request.getParameter("todo_day");
		
					if (editList.length() != 0 && strTodo_day.length() != 0) {
						
						boolean result = logic.edit(select,editList,strTodo_day);
						
						if (result == true) {
							message = "編集が完了しました" + "<br>";
						}
						
					} else {
						message += "追加したい予定が未入力です";
					}
					break;
		
				default:
					
					message +="処理ができません";
					break;
			}
		}
		
		//todo完了
		if(getData != null) {
			logic.completeCheck(getData);

		} 
		
		List<Todolist> todoList = logic.getTodo(user_name);
		session.setAttribute("todoList", todoList);
		request.setAttribute("message", message);
		
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request,response);
		
	}
}