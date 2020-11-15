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
    <h1>DEBATE FORUM</h1>
    <h2>Post New Debate</h2>
    <c:choose>
        <c:when test="${sessionScope.get('username') !=null}">
            <form action="/forum" method="post">
                Title: <input type="text" name="title" width="30"/></br>
                Content: </br>
                <textarea name="content" rows="4" cols="50" placeholder="Start New Discussion Here"></textarea></br>
                <input type="submit" value="Submit New Post"/>
            </form>
        </c:when>
        <c:otherwise>
            <p>Please login to post a new debate!</p>
        </c:otherwise>
    </c:choose>
    <p style="color:red"><c:out value="${confirmation}" /></p>
    <h2>Previous Debates</h2>
    <p>The forum has <c:out value="${arraylen}" /> posts</p></br>
    <c:forEach var="post" items="${postlist}">
        </br></br>
        <a href = "/post?post=<c:out value="${post.getPostID()}"/>"><strong>Title: <c:out value="${post.getPostTitle()}"/></strong></br>
        ID: <c:out value="${post.getPostID()}"/> Posted by: <c:out value="${post.getUsername()}"/></br>
        Message Content: </br>
        <c:out value="${post.getContens()}"/></br>
        Posted on: <c:out value="${post.getCreationDate()}"/></a></br>
    </c:forEach>
</body>
</html>
