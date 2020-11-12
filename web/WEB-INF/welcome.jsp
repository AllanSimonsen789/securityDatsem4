<%--
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
    <title>Welcome</title>
</head>
<body>
    <h1>Welcome!</h1>
    <p>Dear: <c:out value="${username}" /></p>
    <p>Your Email is <c:out value="${email}" /></p>
    <p>Your id is <c:out value="${id}" /></p>

    <% if(request.getAttribute("created") != null){ %>
    <p><c:out value="${created}" /></p>
    <%}%>

    </body>
</html>
