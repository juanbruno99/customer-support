<%@ page import="java.util.Map" %>
<%--@elvariable id="ticketDatabase" type="java.util.mal<Integer, com.wrox.Ticket>" --%>
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
	
	<!-- Scriptlets and such replaced by jstl tags -->
	<!-- Use of choose, combined with when and otherwise as if else syntax in tags -->
        <c:choose>
            <c:when test="${fn:length(ticketDatabase) == 0}">
                <i>There are no tickets in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${ticketDatabase}" var="entry">
                    Ticket ${entry.key}: <a href="<c:url value="/tickets">
                        <c:param name="action" value="view" />
                        <c:param name="ticketId" value="${entry.key}" />
                    </c:url>"><c:out value="${entry.value.subject}" /></a>
                    (customer: <c:out value="${entry.value.customerName}" />)<br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
</body>
</html>