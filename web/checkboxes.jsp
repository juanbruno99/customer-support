<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Checkboxes JSP page</title>
</head>
<body>
	<!-- JSP calls another jsp, servlet, to deal with inputs -->
	<form action="checkboxesSubmit.jsp" method="post">
		Select the fruits you would like to eat: <br />
		
		<input type="checkbox" name="fruit" value="banana" /> Banana <br />
		<input type="checkbox" name="fruit" value="apple" /> Apple <br />
		<input type="checkbox" name="fruit" value="orange" /> Orange <br />
		<input type="checkbox" name="fruit" value="guava" /> Guava <br />
	
	</form>
</body>
</html>