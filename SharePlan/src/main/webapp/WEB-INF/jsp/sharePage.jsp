<%@page import="model.ScheduleData"%>
<%@page import="model.ChatData"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String group = (String) session.getAttribute("loginGroup");
//カレンダーの日付のリスト
List<LocalDate> calendarList = (List<LocalDate>) session.getAttribute("calendarList");
//完了・エラーメッセージの管理
String msg = (String) request.getAttribute("msg");
//カレンダーを選択したときに表示される予定のリスト
List<ScheduleData> planList = (List<ScheduleData>) session.getAttribute("planList");
//チェックボックスで選択した予定のみのリスト
List<ScheduleData> editPlanList = (List<ScheduleData>) request.getAttribute("editPlanList");
//チェックされた予定のみのplanListインスタンスがもつリストの要素番号を格納する配列
int[] selectedPlansInt = (int[]) request.getAttribute("selectedPlansInt");

//セッションスコープからユーザー名を取得
String loginUser_name = (String) session.getAttribute("loginUser");
//セッションスコープからグループ名を取得
String loginGroup = (String) session.getAttribute("loginGroup");
//セッションスコープに保存されたチャットリストを取得
List<ChatData> chatList = (List<ChatData>) session.getAttribute("chatList");
//セッションスコープに保存されたエラーメッセージを取得
String errorMsg = (String) session.getAttribute("errorMsg");

//質問ボックスを出すトリガー的存在(文字の内容は関係ない…)
String makeQ = (String) session.getAttribute("makeQ");
//質問
String question = (String) session.getAttribute("question");
//選択肢を入力する箱の数のリスト
List<Integer> optionNumberList = (List<Integer>) session.getAttribute("optionNumberList");
//メッセージの表示
String Emsg = (String) session.getAttribute("Emsg");

//質問と<選択肢:投票数>を保持するMap
Map<String, Map<String, Integer>> countMap = (Map<String, Map<String, Integer>>) application.getAttribute("countMap");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SharePage</title>
<link rel="stylesheet" href="./CSS/styles.css">
</head>
<body>
	<div class="container">
		<h3><%=group%>の部屋
		</h3>
		<h1>Schedule</h1>
		<div class="calender-title">
			<a href="Schedule?go=<%=calendarList.get(0).minusMonths(1)%>"
				style="display: inline" class="button_month">＜</a>
			<h2><%=calendarList.get(0).getYear()%>年<%=calendarList.get(0).getMonthValue()%>月
			</h2>
			<a href="Schedule?go=<%=calendarList.get(0).plusMonths(1)%>"
				style="display: inline" class="button_month">＞</a>
		</div>
		<div class="action-area">
			<div class="lists-back-ground">
				<div class="calendar">
					<table border="">
						<tr>
							<th style="color: #b22222">日</th>
							<th>月</th>
							<th>火</th>
							<th>水</th>
							<th>木</th>
							<th>金</th>
							<th style="color: #4169e1">土</th>
						</tr>
						<tr>
							<%
							int dayOfWeek = calendarList.get(0).getDayOfWeek().getValue() % 7;
							for (int i = 0; i < dayOfWeek; i++) { //その月の始まり曜日まで空白を作るfor文
							%>
							<td></td>
							<%
							}
							%>


							<%
							for (LocalDate day : calendarList) {
							%>
							<td><a href="Schedule?date=<%=day%>"><%=day.getDayOfMonth()%></a></td>
							<%
							if (day.getDayOfWeek().getValue() % 7 == 6) { //(6＝土曜日)次の行へ
							%>
						</tr>
						<tr>
							<%
							}
							%>
							<%
							}
							%>
						</tr>
					</table>
				</div>
			</div>
			<%
			if (msg != null) {
			%>
			<p class="error"><%=msg%></p>
			<%
			}
			%>
			<form action="Schedule" method="post" class="input-area-calendar">

				<%
				if (planList != null) {
				%>
				<%
				for (ScheduleData plan : planList) {
				%>
				<div class="inline-form">
					<input type="checkbox" name="plans"
						value="<%=planList.indexOf(plan)%>"
						<%if (selectedPlansInt != null && selectedPlansInt[0] == planList.indexOf(plan)) {%>
						checked <%}%>><%=plan.getPlan_time().toString()%> <%=plan.getPlan()%>
				</div>
				<%
				}
				%>
				<%
				}
				%>

				<%
				if (editPlanList == null) {
				%>
				<div class="input-option">
					<input type="text" name="scheduleCom" class="input-text">
				</div>
				<div class="input-option inline-form">
					<input type=date name="day" class="input-text"> <input
						type=time name="time" class="input-text">
				</div>
				<div class="input-button-area">
					<input type="submit" value="追加" name="submit" class="btn">
					<input type="submit" value="編集" name="submit" class="btn">
					<input type="submit" value="削除" name="submit" class="btn">
				</div>
				<%
				}
				%>

				<%
				if (editPlanList != null) {
				%>
				<%
				for (ScheduleData plan : editPlanList) {
				%>
				<div class="input-option">
					<input type="text" name="editCom" value="<%=plan.getPlan()%>"
						class="input-text">
				</div>
				<div class="input-option inline-form">
					<input type=date name="editDay" value="<%=plan.getPlan_day()%>"
						class="input-text"> <input type=time name="editTime"
						value="<%=plan.getPlan_time()%>" class="input-text">
				</div>
				<input type="submit" value="完了" name="submit" class="btn">
				<%
				}
				%>
				<%
				}
				%>
			</form>
		</div>
		<br> <br>



		<h1>Chat</h1>
		<div class="action-area">
			<div class="todoChat">
				<div class="lists-back-ground-todoChat">
					<table>

						<%
						if (chatList != null) {
						%>

						<%
						for (ChatData chat : chatList) {
						%>

						<%
						if (chat.getChat() != null && chat.getChat().length() != 0) {
						%>

						<%
						// フォーマットを指定してSimpleDateFormatを作成
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
						//タイムスタンプを文字列に変換
						String formattedChat_day = sdf.format(chat.getChat_day());
						%>
						<tr>
							<td><%=chat.getUser_name()%> :</td>
							<td><%=chat.getChat()%></td>
							<td><%=formattedChat_day%></td>
						</tr>
						<%
						}
						%>
						<%
						}
						%>
						<%
						}
						%>

					</table>
				</div>
			</div>
			<br>
			<div class="error">
				<%
				if (errorMsg != null && errorMsg.length() != 0) { //エラーメッセージがあれば
				%>
				<%=errorMsg%>
				<%
				}
				%>
			</div>
			<form action="Chat" method="post" class="input-option inline-form">
				<input type="text" name="chat" placeholder="チャットに参加する"
					class="input-text"> <input type="submit" value="+"
					class="btn" style="border-radius: 2rem">
			</form>

			<div>
				<%
				if (Emsg != null) {
				%>
				<%=Emsg%>
				<%
				}
				%>
				<%
				if (makeQ == null || makeQ.length() == 0) {
				%>
				<form action="MakeQuestion" method="get">
					<input type="submit" value="アンケート作成" name="makeQ" class="btn">
				</form>
				<%
				} else {
				%>
				<form action="MakeQuestion" method="post">
					<label for="question">質問:</label><br> <input type="text"
						id="question" name="question" <%if (question != null) {%>
						value="<%=question%>" <%}%>><br>
					<%
					if (optionNumberList != null && optionNumberList.size() != 0) {
						for (Integer number : optionNumberList) {
					%>
					選択肢<%=number%><br>
					<input type="text" name="text"><br>
					<%
					}
					%>
					<input type="submit" value="選択肢削除" name="submit" class="btn">
					<%
					}
					%>
					<input type="submit" value="選択肢追加" name="submit" class="btn">
					<%
					if (question == null || Emsg == null) {
					%>
					<input type="submit" value="完了" name="submit" class="btn">
					<%
					} else if (question != null && Emsg.equals("選択肢をYESとNOにしますか？")) {
					%>
					<input type="submit" value="確定" name="submit" class="btn">
					<%
					}
					%>
				</form>
				<%
				}
				%>

				<%
				if (countMap != null) {
				%>
				<form action="SelectQuestion" method="post">
					<%
					for (String inQuestion : countMap.keySet()) {
					%>
					<br>質問：<%=inQuestion%><br>
					<%
					Map<String, Integer> count = countMap.get(inQuestion);
					%>
					<%
					if (count != null) {
					%>
					<%
					for (String str : count.keySet()) {
					%>
					<input type="radio" name="select" value="<%=str%>"><%=str%>
					<%
					}
					%>
					<br>【アンケート結果】<br>
					<%
					for (String str : count.keySet()) {
					%>
					＜<%=str%>:<%=count.get(str)%>票＞
					<%
					}
					%>
					<%
					}
					%>
					<br>
					<%
					}
					%>
					<br>
					<input type="submit" value="送信" class="btn">
				</form>
				<%
				}
				%>
			</div>
		</div>
		<a href="GroupLogout">Group Logout</a> <br>
	</div>
</body>
</html>