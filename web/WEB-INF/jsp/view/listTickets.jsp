<%@ page import="java.util.Map" %>
<%
	@SuppressWarnings("unchecked")
	Map<Integer, Ticket> ticketDatabase = (Map<Integer, Ticket>) request.getAttribute("ticketDatabase"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Customer Support</title>
</head>
<body>
	<h2>Tickets</h2>
	<a href="<c:url value="/tickets">
	<c:param name="action" value="create" />
	</c:url>">
	Create Ticket</a><br/><br/>
	
	<!-- Basically the use of JSPs here is to mix all the elements of them as directives, scriplets and
	declarations along with expressions, whether they are jstl or java; i.e: c:url jstl simplifies
	creation of hiperlink in html -->
	<% if(ticketDatabase.size() == 0) {
		%><i>There are no tickets in the System</i>
	<%
	} else {
		for(int id : ticketDatabase.keySet()) {
			String idString = Integer.toString(id);
			Ticket ticket = ticketDatabase.get(id);
			%>Ticket #<%= idString %>: <a href="<c:url value="/tickets">
			<c:param name="action" value="view" />
			<c:param name="ticketId" value="<%= idString %>" />
			</c:url>"> <%= ticket.getSubject() %></a> (customer: <%= ticket.getCustomerName() %>)<br/> 
			<%
		}
	}
	%>
</body>
</html>