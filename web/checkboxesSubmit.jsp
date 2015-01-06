<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String[] fruits = request.getParameterValues("fruit");
%>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>checkboxesSubmit jsp servlet</title>
</head>
<body>
	<h2>Your Selections</h2>
	<%
		if(fruits==null) {
			
	%>
		You did not select any fruits!
	<%
		}
		else
		{
	%> <ul> <%
		for(String fruit : fruits) {
			//Use of jsp implicit output writer variable that populates response
			out.println("<li>" + fruit + "</li>");
		}
	
	%></ul>
	<%
		}
	%>	
</body>
</html>