<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String msg = (String) request.getAttribute("msg");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Share Plan</title>
<link rel="stylesheet" href="./CSS/styles.css">
</head>
<body style="overflow: hidden">
	<!-- ログインフォーム。ユーザーIDとパスワードの入力を行う -->
	<div class="container" style="overflow: hidden">
		<br>
		<script type="text/javascript" src="./JavaScript/script.js"></script>
		<h1>Login</h1>
		<div class="action-area">
			<%if (msg != null) {%>
			<p class="error"><%=msg%></p>
			<%}%>
			<div class="input-area">
				<div class="lists-back-ground">
					<form action="UserLogin" method="post">

						<div class="lists-table">
							<table>
								<tr>
									<td>ユーザー名：</td>
									<td><input type="text" name="user_name" class="input-text"></td>
								</tr>
								<tr>
									<td>パスワード：</td>
									<td><input type="password" name="password"
										class="input-text"></td>
								</tr>
							</table>
						</div>
						<div class="input-button-area">
							<input type="submit" value="ログイン" class="btn">
						</div>
					</form>
				</div>
			</div>
			<div class="input-button-area">
				<form action="UserRegister" method="get">
					<input type="submit" value="ユーザー登録" class="btn">
				</form>
			</div>
		</div>
	</div>
</body>
</html>