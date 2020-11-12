<%--
  Created by IntelliJ IDEA.
  User: rando & allan <3
  Date: 24/09/2020
  Time: 14:36
  To change this template use File | Settings | File Templates.
  Reference: https://cheatsheetseries.owasp.org/cheatsheets/Error_Handling_Cheat_Sheet.html
--%>
<%@ page language="java" isErrorPage="true" contentType="text/html;charset=UTF-8" %>
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
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Error Page</title>
</head>
<body>
<% if(response.getStatus() >= 500){ %>
Internal server error, error code is <%=response.getStatus() %><br>
Please go to <a href="/index.jsp">home page</a> And try again later ;)

<%}else if (response.getStatus() == 404){%>
Unknown URL<br>
Please go to <a href="/index.jsp">home page</a>

<%}else if (response.getStatus() >= 400){%>
Unknown Error: error code is <%=response.getStatus()%><br>
Please go to <a href="/index.jsp">home page</a>
<%}

%>
</body>
</html>