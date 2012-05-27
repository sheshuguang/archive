<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="webpage/css/style.css" />
<title></title>
</head>
<body>
<form action="login.action" method="post">
	用户名：<input type="text" id="accountcode" name="accountcode" />
	密码:<input type="password" id="password" name="password"/>
	<input type="submit" value="submit" />
</form>
<br>
	${requestScope.str }
</body>
</html>