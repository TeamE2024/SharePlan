<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Todolist"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="model.Group"%>
<%@ page import="servlet.UserLogout"%>
<%
String message = (String) request.getAttribute("message");
String label = (String) request.getAttribute("label");
String beforeList = (String) request.getAttribute("beforeList");
LocalDateTime beforeDate = (LocalDateTime) request.getAttribute("beforeDate");
List<Todolist> todoList = (List<Todolist>) session.getAttribute("todoList");
//リクエストスコープに保存されたエラーメッセージを取得
String group_error_message = (String) request.getAttribute("group_error_message");
%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Main</title>
<link rel="stylesheet" href="./CSS/styles.css">
</head>
<body>
	<div class="container">
		<h1>To do List</h1>
		<div class="action-area">
			<form action="Main" method="post" class="todoChat">
				<div class="lists-back-ground-todoChat">
					<table>
						<%
						for (Todolist todolist : todoList) {
						%>
						<tr>
							<%
							if (todolist.isComplete()) {
							%>
							<th><input type="radio" name="select"
								value=<%=todolist.getId()%> > <%=todolist.getTodo_day()%></th>
							<td><del><%=todolist.getList()%></del></td>
							<%
							if(label != null){
							%>
							<th><input type="radio" name="select"
								value=<%=todolist.getId()%> checked> <%=todolist.getTodo_day()%></th>
							<td><del><%=todolist.getList()%></del></td>
							<%
							}
							%>

							<%
							if (todolist.isComplete()) {
							%>
							<td><input type="hidden" name="complete"
								value=<%=todolist.isComplete()%>>
								<button type="submit" name="getData" value=<%=todolist.getId()%>
									class="button_done">済</button></td>
							<%
							} else {
							%>
							<td><input type="hidden" name="complete"
								value=<%=todolist.isComplete()%>>
								<button type="submit" name="getData" value=<%=todolist.getId()%>
									class="button_month">達成</button></td>
							<%
							}
							%>
						</tr>

						<%
						} else {
						%>
						<tr>
							<th><input type="radio" name="select"
								value=<%=todolist.getId()%> > <%=todolist.getTodo_day()%></th>
							<td><%=todolist.getList()%></td>
							<%
							if(label != null){
							%>
							<th><input type="radio" name="select"
								value=<%=todolist.getId()%> checked> <%=todolist.getTodo_day()%></th>
							<td><del><%=todolist.getList()%></del></td>
							<%
							}
							%>

							<%
							if (todolist.isComplete()) {
							%>
							<td><input type="hidden" name="complete"
								value=<%=todolist.isComplete()%>>
								<button type="submit" name="getData" value=<%=todolist.getId()%>
									class="button_done">済</button></td>
							<%
							} else {
							%>
							<td><input type="hidden" name="complete"
								value=<%=todolist.isComplete()%>>
								<button type="submit" name="getData" value=<%=todolist.getId()%>
									class="button_month">達成</button></td>
							<%
							}
							%>
						</tr>
						<%
						}
						%>
						<%
						}
						%>
					</table>
				</div>

				<div class="input-area-todo">
					<div class="error">
						<%
						if (message != null) {
						%>
						<%=message%>
						<%
						}
						%>
					</div>
					<%
					if (label == null) {
					%>
					<div class="input-area">
						<label>Todo:</label><input type="text" name="list"
							class="input-text">
					</div>
					<div class="input-area">
						<label>Limit:</label><input type="datetime-local" name="todo_day"
							class="input-text">
					</div>
					<%
					} else {
					%>
					<div class="input-area">
						<label>Todo:</label><input type="text" name="list"
							value=<%=beforeList%> class="input-text">
					</div>
					<div class="input-area">
						<label>Limit:</label><input type="datetime-local" name="todo_day"
							value=<%=beforeDate%> class="input-text">
					</div>
					<%
					}
					%>

					<div class="input-option">
						<div class="input-button-area">
							<input type="submit" name="done" value="追加" class="btn">
							<input type="submit" name="done" value="削除" class="btn">
							<input type="submit" name="done" value="編集" class="btn">
							<input type="submit" name="done" value="完了" class="btn">
						</div>
					</div>
				</div>
			</form>
		</div>

		<br> <br> <br>
		<h1>Group Room</h1>
		<div class="action-area">
			<div class="lists-back-ground">
				<div class="lists-table">
					Group名は以下の条件に合うように入力してください <br> <br> ・ 10文字以上、16桁以下 <br>
					・記号、大文字小文字アルファベット、数字を含む <br>
					<%
					if (group_error_message != null) {
					%>
					<p class="error"><%=group_error_message%></p>
					<%
					}
					%>
					<table>
						<tr>
							<form action="GroupLogin" method="post"
								class="input-area inline-form">
								<td>Group Login：</td>
								<td><input type="text" name="group_login"
									class="input-text"></td>
								<td><input type="submit" value="入室" class="btn"></td>
							</form>
						</tr>
						<tr>
							<form action="GroupRegister" method="post"
								class="input-area inline-form">
								<td>Group Create：</td>
								<td><input type="text" name="group_create"
									class="input-text"></td>
								<td><input type="submit" value="追加" class="btn"></td>
							</form>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<a href="UserLogout">User Logout</a> <br>
	</div>
</body>
</html>