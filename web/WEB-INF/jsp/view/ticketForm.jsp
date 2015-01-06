<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Support</title>
</head>
<body>
	<a href="<c:url value="/login?logout" />" >Logout</a>
	<h2>Create Ticket</h2>
	<form action="tickets" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="action" value="create">
		Subject<br/>
		<input type="text" name="subject"><br/><br/>
		Body<br/>
		<textarea rows="5" cols="30" name="body"></textarea><br/><br/>
		<b>Attachments</b><br/>
		<input type="file" name="file1"><br/><br/>
		<input type="submit" value="submit"/>
	</form>
</body>
</html>