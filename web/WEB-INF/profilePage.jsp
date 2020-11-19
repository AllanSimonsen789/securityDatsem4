<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 24/09/2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;encoding=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>Welcome</title>
    </head>
    <body>
        <div align="center">
            <h1>Welcome <c:out value="${user.getUserName()}"/>!</h1>

            <img alt="profilepic" src="${user.getImageURL()}" style="width: 256px; height: 256px;"/></br>

            <p>User ID: <c:out value="${user.getUserID()}"/></p>

            <p>Email: <c:out value="${user.getEmail()}"/></p>

            <c:if test="${requestScope.created != null}">
                <p><c:out value="${created}"/></p>
            </c:if>

            <p>Welcome to debateit, to continue your journey head over to the forum or go back to the main page</p>

            <form action="/forum">
                <input type="submit" value="Go to the Forum" class="btn btn-primary"/>
            </form>

            <form action="/edit">
                <input type="submit" value="Edit user" class="btn btn-primary"/>
            </form>

            <form action="/index.jsp">
                <input type="submit" value="Back to frontpage" class="btn btn-primary"/>
            </form>

            <p>Upload Profile Pic</p>

            <form action="/profile" method="post" enctype="multipart/form-data">
                <input type="file" name="fileContent" multiple accept=".jpg, .png" size="50"/> <br/>
                <input type="submit" value="Upload File" class="btn btn-primary"/>
            </form>

            <p style="color:red"><c:out value="${errorMessage}"/></p>
        </div>
    </body>
</html>
