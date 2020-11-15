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
    <h2>Register</h2>
    <form action="/index.jsp">
        <input type="submit" value="Back to frontpage" />
    </form>
    <form action="/register" method="post">
        username: <input type="text" name="loginname" width="30" min="5" max="50" required/></br></br>
        email:     <input type="text" name="email" width="30" required/></br></br>
        password: <input type="password" name="password" width="10" min="12" max="200" required/></br></br>
        Retype password: <input type="password" name="password2" placeholder="Retype Password" width="10"min="12" max="200" required/></br></br>
        <div class = "tooltip">Password Requirements
            <span class = "tooltiptext"> Atleast 1 uppercase letter <br> Atleast 1 lowercase letter <br> Atleast 1 Number <br> Atleast 12 characters long </span>
        </div></br>
        <div style="margin-top: 20px" class="g-recaptcha"
             data-sitekey="6LceeOEZAAAAAD_L11JeMvfL--daZlljXe64RVYY"></div></br>
        <input type="submit" value="Register"/>
    </form>
    <p style="color:red"><c:out value="${errorMessage}" /></p>
</body>
<style>
    .tooltip {
        position: relative;
        display: inline-block;
        border-bottom: 1px dotted black;
    }

    .tooltip .tooltiptext {
        visibility: hidden;
        width: 300px;
        background-color: black;
        color: #fff;
        text-align: center;
        border-radius: 6px;
        padding: 5px 0;

        /* Position the tooltip */
        position: absolute;
        z-index: 1;
    }

    .tooltip:hover .tooltiptext {
        visibility: visible;
    }
</style>
</html>
