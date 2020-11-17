<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 24/09/2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
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
        <h1>Welcome <c:out value="${user.getUserName()}"/>!</h1>

        <c:choose>
            <c:when test="${user.getImageURL() != null}">
                <img alt="profilepic" src="${user.getImageURL()}"/></br>
            </c:when>
            <c:otherwise>
                <img alt="profilepic" src="https://res.cloudinary.com/dmk5yii3m/image/upload/v1578904570/defaut_vignette_carre_xavv98.jpg"/></br>
            </c:otherwise>
        </c:choose>

        <p>User ID: <c:out value="${user.getUserID()}"/></p>

        <p>Email: <c:out value="${user.getEmail()}"/></p>

        <c:if test="${requestScope.created != null}">
            <p><c:out value="${created}"/></p>
        </c:if>

        <p>Welcome to debateit, to continue your journey head over to the forum or go back to the main page</p>

        <form action="/forum">
            <input type="submit" value="Go to the Forum"/>
        </form>

        <form action="/edit">
            <input type="submit" value="Edit user"/>
        </form>

        <form action="/index.jsp">
            <input type="submit" value="Back to frontpage"/>
        </form>

        <p>Upload Profile Pic</p>

        <form action="/profile" method="post" enctype="multipart/form-data">
            <input type="file" name="file" size="50"/> <br/>
            <input type="submit" value="Upload File"/>
        </form>
    </body>
</html>
