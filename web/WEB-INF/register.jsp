<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 12/11/2020
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Register</title>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
    <form action="/register" method="post">
        username: <input type="text" name="loginname" width="30"/>
        email: <input type="text" name="email" width="30"/>
        password: <input type="password" name="password" width="10"/>
        <div class="g-recaptcha"
             data-sitekey="6LceeOEZAAAAAD_L11JeMvfL--daZlljXe64RVYY"></div>
        <input type="submit" value="Register"/>
    </form>
    <p style="color:red"><c:out value="${errorMessage}" /></p>
</body>
</html>
