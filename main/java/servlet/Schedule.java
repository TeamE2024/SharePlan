package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommentLogic;
import model.MakeCalendar;
import model.ScheduleData;

/*
 * スケジュールサーブレット
 */
@WebServlet("/Schedule")
public class Schedule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//グループ名の取得
		HttpSession session = request.getSession();
		String group_name = (String)session.getAttribute("loginGroup");
		
		//カレンダー生成
		MakeCalendar calendar = new MakeCalendar();
		List<LocalDate> calendarList = calendar.getCalenderDays();
//		//カレンダーリストをスコープへ
		session.setAttribute("calendarList", calendarList);
		
		
		//カレンダーリストをスコープから取り出す
		session = request.getSession();
		calendarList = (List<LocalDate>) session.getAttribute("calendarList");
		

		//処理クラスのインスタンスの作成
		CommentLogic logic = new CommentLogic();
		

		//カレンダーの日付を選んだ時の戻り値を取得
		String date = request.getParameter("date");	//2024-10-09
		//日付にあわせた予定のリスト(初期画面は当日日付)
		List<ScheduleData> planList = logic.outputSeparateDate(date,group_name);
		session.setAttribute("planList", planList);

		
		//カレンダーうえの矢印を選んだ時の戻り値の取得
		String go = request.getParameter("go");
		if (go != null) { 
			calendar = new MakeCalendar();
			List<LocalDate> newCalendarList = calendar.chenageCalender(go);
			session.setAttribute("calendarList",newCalendarList);
			planList = null;
			session.setAttribute("planList", planList);
		}
		
		// 最新のチャットリストを取得して、表示するため
		//チャット内容をデータベースから取得しリストに保管
		GetChatListLogic getChatListLogics = new GetChatListLogic();
		List<ChatData> chatList = getChatListLogics.execute(group_name);
		request.setAttribute("chatList", chatList);		
		
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sharePage.jsp");
		dispatcher.forward(request, response);
	}


	/*
	 * フォームから分岐→(追加・編集・削除)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//グループ名の取得
		HttpSession session = request.getSession();
		String group_name = (String)session.getAttribute("loginGroup");
				
		
		//分岐する値の取得
		String submit = request.getParameter("submit");
		
		//処理をするクラスインスタンスの作成
		CommentLogic logic = new CommentLogic();

		/*
		 * 分岐・追加
		 */
		if (submit.equals("追加")) {

			String scheduleCom = request.getParameter("scheduleCom");	//予定内容
			String day = request.getParameter("day");					//予定日付
			String time = request.getParameter("time");					//予定時間
			
			if (scheduleCom != null && scheduleCom.length() != 0) {
				//予定の登録処理
				boolean finalCheck = logic.input(scheduleCom, day,time,group_name);
				
				if (finalCheck) {
					request.setAttribute("msg", "追加完了");
				}else {
					request.setAttribute("msg", "追加できませんでした");
				}
				
			}else {
				request.setAttribute("msg", "予定が入力されていません");
			}
			
		} else {
			//チェックボックスの取得(戻り値はplanListの要素番号が格納)
			String[] selectedPlans = request.getParameterValues("plans");

			if (selectedPlans != null) {
				//同じ日付のみの予定が入ったリストの取得
				List<ScheduleData> planList = (List<ScheduleData>) session.getAttribute("planList");
				
				//String配列をint配列へ変更
				int[] selectedPlansInt = new int[selectedPlans.length];
				for (int i = 0; i < selectedPlans.length; i++) {
					selectedPlansInt[i] = Integer.parseInt(selectedPlans[i]);
				}
				
				//チェックボックスで選択した予定のみのリストの作成
				List<ScheduleData> editPlanList = logic.readyToEdit(selectedPlansInt,planList);

				/*
				 * 分岐・編集表示と完了処理
				 */
				if (submit.equals("編集")) {
					//編集は1つずつ行う
					if(selectedPlans.length == 1) {
						request.setAttribute("editPlanList", editPlanList);
						request.setAttribute("selectedPlansInt", selectedPlansInt);
					}else {
						request.setAttribute("msg", "編集は1つのみ選択してください");
					}
					
				}
			if(submit.equals("完了")) {
					//編集された値の取得
					String editCom = request.getParameter("editCom");		//変更内容
					String editDay = request.getParameter("editDay");		//変更日付
					String editTime = request.getParameter("editTime");		//変更時間
					//編集登録処理
					boolean finalCheck = logic.editPlan(editPlanList.get(0).getId(),editCom,editDay,editTime);
					if(finalCheck) {
						request.setAttribute("msg", "編集完了");
						planList = null;	
						session.setAttribute("planList",planList);
					} else {
						request.setAttribute("msg", "編集が登録できませんでした");
					}
				}

				/*
				 * 分岐・削除
				 */
				if (submit.equals("削除")) {
					//削除処理
					boolean finalCheck = logic.deleteData(editPlanList);
				
					if (finalCheck) {
						request.setAttribute("msg", "削除完了");
					
						planList = null;	
						session.setAttribute("planList",planList);
					} else {
						request.setAttribute("msg", "削除できませんでした");
					}
					
				}
				
			} else {
				request.setAttribute("msg", "選択されていません");
			}
			
		}
		
		//グループ名を取得
		String loginGroup = (String)session.getAttribute("loginGroup");
		System.out.println("loginGroup確認:" + loginGroup);		
		
		// 最新のチャットリストを取得して、表示するため
		//チャット内容をデータベースから取得しリストに保管
		GetChatListLogic getChatListLogics = new GetChatListLogic();
		List<ChatData> chatList = getChatListLogics.execute(loginGroup);
		request.setAttribute("chatList", chatList);		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sharePage.jsp");
		dispatcher.forward(request, response);
	}

}
