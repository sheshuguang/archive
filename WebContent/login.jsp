<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="webpage/css/style.css" />
<script type="text/javascript" src="webpage/js/jquery-1.7.1.js"></script>
<title></title>
<!-- 
<script language="javascript">
	$(function() {
		$.ajaxSetup({
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			timeout : pageTimeout,
			cache : false,
			complete : function(XHR, TS) {
				alert("dd");
				var resText = XHR.responseText;
				if (resText == "{sessionState:0}") {
					var nav = judgeNavigator();
					if (nav.indexOf("IE:6") > -1) {
						window.opener = null;
						window.close();
						window.open(jsContextPath + '/login.jsp', '');
					} else {
						window.open(jsContextPath + '/login.jsp', '_top');
					}
				}
			}
		});
	})
</script>
 -->
</head>
<body>
	<form action="login.action" method="post">
		用户名：<input type="text" id="accountcode" name="accountcode"
			value="admin" /> 密码:<input type="password" id="password"
			name="password" value="password" /> <input type="submit"
			value="submit" />
	</form>
	<br> ${requestScope.str }
</body>
</html>