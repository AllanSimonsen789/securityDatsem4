<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 24/09/2020
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
    <form action="/login" method="post">
        <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
        login-name: <input type="text" name="loginname" width="30"/>
        password: <input type="password" name="password" width="10"/>
        <div class="g-recaptcha"
             data-sitekey="6LceeOEZAAAAAD_L11JeMvfL--daZlljXe64RVYY"></div>
        <input type="submit" value="Login"/>
    </form>
    <p><c:out value="${csrf_success_error}" /></p>
    <p style="color:red"><c:out value="${errorMessage}" /></p>
</body>
</html>
