<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Support Login</title>
</head>
<body>
	<h2>Login</h2>
	You must login to acces the customer support site <br/><br/>
	<%
		if((Boolean)request.getAttribute("loginFailed")) {
	%> <b>User name and password entered are incorrect. Please try again</b><br/><br/>
	
	<% }  %>
	
	<form method="POST" <c:url value="/login" />">
		Username
		<input type="text" name="username" /><br/><br/>
		Password
		<input type="text" name="password" /><br/><br/>
		<input type="submit" value="Log in">
	</form>
</body>
</html>