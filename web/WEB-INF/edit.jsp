<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 14/11/2020
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
    <form action="/edit" method="post">
        New username: <input type="text" name="newUsername" width="30"/>
        New email: <input type="text" name="newEmail" width="30"/>
        New password: <input type="password" name="newPassword" width="10"/>
        <div class="g-recaptcha"
             data-sitekey="6LceeOEZAAAAAD_L11JeMvfL--daZlljXe64RVYY"></div>
        <input type="submit" value="Edit"/>
    </form>
    <p style="color:red"><c:out value="${errorMessage}" /></p>
</body>
</html>
