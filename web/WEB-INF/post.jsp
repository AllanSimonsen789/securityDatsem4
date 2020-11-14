<%--
  Created by IntelliJ IDEA.
  User: allan
  Date: 13-11-2020
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Debate Forum</title>
</head>
<body>
    <h1><c:out value="${post.getPostTitle()}"/></h1>
    <h3>ID: <c:out value="${post.getUserID()}"/> - Posted By: <c:out value="${post.getUsername()}"/> - Posted On: <c:out value="${post.getCreationDate()}"/></h3>
    <p><c:out value="${post.getContens()}"/></p>

    <c:forEach var="reply" items="${replies}">
        </br></br>
        <strong>Reply: <c:out value="${reply.getUsername()}"/></strong> Posted on: <c:out value="${post.getCreationDate()}"/></br>
            Reply Content: </br>
                <c:out value="${reply.getContens()}"/></br></br>
    </c:forEach>
    <p style="color:red"><c:out value="${confirmation}" /></p>

    <h2>Make a reply</h2></br>
    <form action="/post?post=<c:out value="${post.getPostID()}"/>" method="post">
        <textarea name="content" rows="4" cols="50" placeholder="Make a reply here"></textarea></br>
        <input type = "hidden" name = "userid" value = "1">
        <input type = "hidden" name = "postid" value = "<c:out value="${post.getPostID()}"/>">
        <input type="submit" value="Submit Reply"/>
    </form>
</body>
</html>
