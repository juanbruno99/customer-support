<%--@elvariable id="ticketId" type="java.lang.String"--%>
<%--@elvariable id="ticket" type="com.tikcom.domain.Ticket"--%>

<%
	Ticket ticket = (Ticket) request.getAttribute("ticket");
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Support</title>
</head>
<body>
	<a href="<c:url value="/login?logout" />">Logout</a>

	<h2>Ticket #${ticketId} : ${ticket.subject} </h2>
	<i>Customer Name - ${ticket.customerName} </i><br/><br/>
	${ticket.body}<br/><br/>
	<%
		if(ticket.getNumberOfAttachments() > 0)
		{
			%>Attachments: <%
			int i = 0;
			for(Attachment a : ticket.getAttachments())
			{
				if(i>0)
					out.print(", "); %>
					<!-- Se usan jstl tags y jsp expression, c:url para construir hiperlink en este caso -->
					<a href="<c:url value="/tickets">
					<c:param name="action" value="download" />
					<c:param name="ticketId" value="${ticketId}" />
					<c:param name="attachment" value="<%= a.getName() %>" />					
					</c:url>"><%= a.getName() %>
					</a> <%
			}
						
		}
	%>
	<a href="<c:url value="/tickets" />">Return to list tickets</a>
</body>
</html>