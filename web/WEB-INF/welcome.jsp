<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 24/09/2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%
    if (session.getAttribute("username") !=null) {
        // Mby edit this to check a users role? set the session attribute to a role? To check if the user is
        // Admin or normal user?
        // Login
        // success action...
    } else {
        // log noget her...
        response.sendRedirect("/WEB-INF/login.jsp");
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Welcome</title>
</head>
<body>
    <h1>Welcome <c:out value="${username}" />!</h1>
    <p>User ID: <c:out value="${id}" /></p>
    <p>Email: <c:out value="${email}" /></p>
    <% if(request.getAttribute("created") != null){ %>
    <p><c:out value="${created}" /></p>
    <%}%>
    <p>Welcome to debateit, to continue your journey head over to the forum or go back to the main page</p>
    <form action="/forum">
        <input type="submit" value="Go to the Forum" />
    </form>
    <form action="/index.jsp">
        <input type="submit" value="Back to frontpage" />
    </form>

    </body>
</html>
