<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="com.yapu.system.util.Constants" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
<%
session.removeAttribute(Constants.user_in_session);
session.invalidate();
out.println("<script language='javascript'>");
out.println("window.parent.location.href='/login.jsp'");
out.println("</script>");
%>	


</body>
</html>
