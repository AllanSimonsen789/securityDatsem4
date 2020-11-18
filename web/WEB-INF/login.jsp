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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>Login</title>
        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
    <body>
        <div align="center">
            <h2>Login</h2>

            <form action="/index.jsp">
                <input type="submit" value="Back to frontpage" class="btn btn-primary"/>
            </form>

            <form action="/login" method="post">
                <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
                Email: <input type="text" name="email" width="30" value = "<c:out value="${email}"/>" required/></br></br>
                Password: <input type="password" name="password" width="10" required/></br>
                <div style="margin-top: 20px" class="g-recaptcha"
                     data-sitekey="6LceeOEZAAAAAD_L11JeMvfL--daZlljXe64RVYY"></div></br>
                <input type="submit" value="Login" class="btn btn-primary"/>
            </form>

            <p style="color:red"><c:out value="${errorMessage}" /></p>
        </div>
    </body>
</html>
