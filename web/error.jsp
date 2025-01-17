<%--
  Created by IntelliJ IDEA.
  User: rando & allan <3
  Date: 24/09/2020
  Time: 14:36
  To change this template use File | Settings | File Templates.
  Reference: https://cheatsheetseries.owasp.org/cheatsheets/Error_Handling_Cheat_Sheet.html
--%>
<%@ page language="java" isErrorPage="true" contentType="text/html;encoding=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<%--%>
<%--String errorMessage = exception.getMessage();--%>
<%--//Log the exception via the content of the implicit variable named "exception"--%>
<%--//...--%>
<%--//We build a generic response with a JSON format because we are in a REST API app context--%>
<%--//We also add an HTTP response header to indicate to the client app that the response is an error--%>
<%--response.setHeader("X-ERROR", "true");--%>
<%--response.setStatus(200);--%>
<%--%>--%>
<%--{"message":"An error occurred, please retry"}--%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <title>Error Page</title>
    </head>
    <body>
        <img alt="Error"
             src="https://starecat.com/content/wp-content/uploads/windows-xp-task-failed-successfully-warning-information.jpg"
             style="width: 200px; height: 130px;"></br>

<%--        <c:choose>--%>
<%--            <c:when test="${requestScope['javax.servlet.error.status_code'] >= 500}">--%>
<%--                Internal server error, error code is ${requestScope.getStatus()}<br>--%>
<%--                Please go to <a href="/index.jsp">home page</a> And try again later ;)--%>
<%--            </c:when>--%>
<%--            <c:when test="${requestScope['javax.servlet.error.status_code'] == 404}">--%>
<%--                Unknown URL, error code is ${requestScope.getStatus()}<br>--%>
<%--                Please go to <a href="/index.jsp">home page</a>--%>
<%--            </c:when>--%>
<%--            <c:when test="${requestScope['javax.servlet.error.status_code'] == 403}">--%>
<%--                No access, error code is ${requestScope.getStatus()}<br>--%>
<%--                Please go to <a href="/index.jsp">home page</a>--%>
<%--            </c:when>--%>
<%--            <c:when test="${requestScope['javax.servlet.error.status_code'] >= 400}">--%>
<%--                Unknown Error: error code is ${requestScope.getStatus()}<br>--%>
<%--                Please go to <a href="/index.jsp">home page</a>--%>
<%--            </c:when>--%>
<%--        </c:choose>--%>

        <% if (response.getStatus() >= 500) { %>
        Internal server error, error code is <%=response.getStatus() %><br>
        Please go to <a href="/index.jsp">home page</a> And try again later ;)
        <%} else if (response.getStatus() == 404) {%>
        Unknown URL, error code is <%=response.getStatus() %><br>
        Please go to <a href="/index.jsp">home page</a>
        <%} else if (response.getStatus() == 403) {%>
        No access, error code is <%=response.getStatus() %><br>
        Please go to <a href="/index.jsp">home page</a>
        <%} else if (response.getStatus() >= 400) {%>
        Unknown Error: error code is <%=response.getStatus()%><br>
        Please go to <a href="/index.jsp">home page</a>
        <%}%>
    </body>
</html>