<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 12/11/2020
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;encoding=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>Register</title>
        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
    <body>
        <div align="center">
            <h2>Register</h2>

            <form action="/index">
                <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
                <input type="submit" value="Back to frontpage" class="btn btn-primary"/>
            </form>

            <form action="/register" method="post">
                <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
                Username: <input type="text" name="loginname" width="30" min="5" max="50" value="<c:out value="${loginname}" />"
                                 required/></br></br>
                Email: <input type="text" name="email" width="30" value="<c:out value="${email}" />" required/></br></br>
                Password: <input type="password" name="password" width="10" min="12" max="200" required/></br></br>
                Retype password: <input type="password" name="password2" placeholder="Retype Password" width="10" min="12" max="32"
                                        required/></br></br>
                <div class="passwprdReq">Password Requirements
                    <span class="passwprdReqText"> Atleast 1 uppercase letter <br> Atleast 1 lowercase letter <br> Atleast 1 Number <br> Atleast 12 characters long <br> Max 32 characters long </span>
                </div>
                </br>
                <div style="margin-top: 20px" class="g-recaptcha"
                     data-sitekey="6LceeOEZAAAAAD_L11JeMvfL--daZlljXe64RVYY"></div>
                </br>
                <input type="submit" value="Register" class="btn btn-primary"/>
            </form>

            <p style="color:red"><c:out value="${errorMessage}"/></p>
        </div>
    </body>
    <style>
        .passwprdReq {
            position: relative;
            display: inline-block;
            border-bottom: 1px dotted black;
        }

        .passwprdReq .passwprdReqText {
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

        .passwprdReq:hover .passwprdReqText {
            visibility: visible;
        }
    </style>
</html>
