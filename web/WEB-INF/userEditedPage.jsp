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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>Successful edit</title>
    </head>
    <body>
        <div align="center">
            <h1>Successful</h1>

            <p>Your user information was updated!</p>

            <p>Username: <c:out value="${username}" /></p>

            <p>User email: <c:out value="${email}" /></p>

            <p style="color:red"><c:out value="${errorMessage}" /></p>

            <form action="/forum">
                <input type="submit" value="Go to the Forum" class="btn btn-primary"/>
            </form>

            <form action="/index.jsp">
                <input type="submit" value="Back to frontpage" class="btn btn-primary"/>
            </form>
        </div>
    </body>
</html>
