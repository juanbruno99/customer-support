<!-- JSP Directive -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<!-- JSP Declaration -->
<%! 

private static final String DEFAULT_USER = "Guest"; 

%>

<!-- JSP scriptlet -->
<%
String user = request.getParameter("user");

	if(user==null)
		user = DEFAULT_USER;

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello User Application</title>
</head>

<body>
	<!-- JSP Expression -->
	Hello, <%= user %>!<br /><br />
	<form action="greeting.jsp" method="POST">
	Enter your name: <br/>
		<input type="text" name="user" /><br />
		<input type="submit" value="Submit" />
	</form>
</body>
</html>