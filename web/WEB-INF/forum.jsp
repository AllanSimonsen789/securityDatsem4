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
    <title>Title</title>
</head>
<body>
    <p>Hello there</p>
    <c:out value="${arraylen}" /></br>
    <c:forEach var="post" items="${postlist}">
        </br></br>
        <strong>Title: <c:out value="${post.getPostTitle()}"/></strong></br>
        ID: <c:out value="${post.getPostID()}"/> Posted by: <c:out value="${post.getUsername()}"/></br>
        Message Content: </br>
        <c:out value="${post.getContens()}"/></br>
        Posted on: <c:out value="${post.getCreationDate()}"/></br>
    </c:forEach>
</body>
</html>
