<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 24/09/2020
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;encoding=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Debateit</title>
</head>
<body>
    <div align="center">
        <h1>Debateit</h1>

        <h3>Welcome to Debateit.</h3>

        <p>To make a post or reply you need to login or register as a user</p>

        <c:choose>
            <c:when test="${sessionScope.get('username') ==null}">
                <form action="/login" METHOD="get">
                    <input type="submit" value="Login" class="btn btn-primary"/>
                </form>
            </c:when>
            <c:otherwise>
                <form action="/logout">
                    <input type="submit" value="Logout" class="btn btn-primary"/>
                </form>
                <form action="/profile">
                    <input type="submit" value="Go To Profile Page" class="btn btn-primary"/>
                </form>
                <%--   If admin show btn--%>
                <c:if test="${sessionScope.username.role.equals('admin')}">
                    <form action="/secretAdminPage">
                        <input type="submit" value="Go To Admin Page" class="btn btn-primary"/>
                    </form>
                </c:if>
            </c:otherwise>
        </c:choose>

        <c:if test="${sessionScope.get('username') ==null}">
            <form action="/register">
                <input type="submit" value="Register As New User" class="btn btn-primary"/>
            </form>
        </c:if>

        <form action="/forum">
            <input type="submit" value="Go To Forum" class="btn btn-primary"/>
        </form>
    </div>
</body>
</html>
