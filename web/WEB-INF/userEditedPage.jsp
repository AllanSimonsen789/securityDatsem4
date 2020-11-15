<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 14/11/2020
  Time: 20:12
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
        response.sendRedirect("/index.jsp");
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Successful edit</title>
</head>
<body>
    <h1>Successful</h1>
    <p>Your user information was updated!</p>
    <p>Username: <c:out value="${username}" /></p>
    <p>User email: <c:out value="${email}" /></p>
    <p style="color:red"><c:out value="${errorMessage}" /></p>
    <form action="/forum">
        <input type="submit" value="Go to the Forum" />
    </form>
    <form action="/index.jsp">
        <input type="submit" value="Back to frontpage" />
    </form>
</body>
</html>
