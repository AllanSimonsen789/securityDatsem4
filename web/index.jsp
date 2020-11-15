<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 24/09/2020
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
      <title>Hello World</title>
  </head>
  <body>
    <h1>HelloWorld!</h1>
    <p>Hello world - setup!</p>
    <c:choose>
        <c:when test="${sessionScope.get('username') ==null}">
            <form action="/login">
                <input type="submit" value="Login"/>
            </form>
        </c:when>
        <c:otherwise>
            <form action="/logout">
                <input type="submit" value="Logout"/>
            </form>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${sessionScope.get('username') ==null}">
            <form action="/register">
                <input type="submit" value="Register As New User"/>
            </form>
        </c:when>
        <c:otherwise>
            <form action="/edit">
                <input type="submit" value="Edit User"/>
            </form>
        </c:otherwise>
    </c:choose>
    <form action="/forum">
        <input type="submit" value="Go To Forum"/>
    </form>
  </body>
</html>
