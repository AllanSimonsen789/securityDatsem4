<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 15/11/2020
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Secret Admin Page</title>
</head>
<body>
    <h1>Secret admin page!</h1>
    <h2>No normal users are allowed!</h2>
    <form action="/index.jsp">
        <input type="submit" value="Back to frontpage" />
    </form>
</body>
</html>
