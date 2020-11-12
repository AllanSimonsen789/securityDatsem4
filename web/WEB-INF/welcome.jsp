<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 24/09/2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <h1>Welcome!</h1>
    <p>Dear: ${username}</p>
    <p>Your Email is ${email}</p>
    <p>Your id is ${id}</p>


<%--    <c:choose>--%>
<%--        <c:when test="${created}">--%>
<%--            <p>${created}</p>--%>
<%--        </c:when>--%>
<%--    </c:choose>--%>
<%--    https://docs.oracle.com/cd/E19879-01/819-3669/bnakl/index.html--%>

<%--    <c:if test="${!empty param.created}">--%>
<%--        <p>${created}</p>--%>
<%--    </c:if>--%>

</body>
</html>
