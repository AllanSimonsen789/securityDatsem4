<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 14/11/2020
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;encoding=UTF-8" language="java" %>
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

            <h2>Edit profile</h2>
            <form action="/index">
                <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
                <input type="submit" value="Back to frontpage" class="btn btn-primary"/>
            </form>

            <p>Change username, contact email or password.</p>

            <form action="/edit" method="post">
                <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
                New username: <input type="text" name="newUsername" width="30" value="<c:out value="${newUsername}" />"/></br></br>
                New email: <input type="text" name="newEmail" width="30" value="<c:out value="${newEmail}" />"/></br></br>
                New password: <input type="password" name="newPassword" width="10" min="12" max="200"/></br></br>
                Retype password: <input type="password" name="newPassword2" placeholder="Retype Password" width="10" min="12"
                                        max="200"/></br></br>
                <div class="passwprdReq">Password Requirements
                    <span class="passwprdReqText"> Atleast 1 uppercase letter <br> Atleast 1 lowercase letter <br> Atleast 1 Number <br> Atleast 12 characters long </span>
                </div>
                </br>
                <div style="margin-top: 20px" class="g-recaptcha"
                     data-sitekey="6LceeOEZAAAAAD_L11JeMvfL--daZlljXe64RVYY"></div>
                <input type="submit" value="Edit" class="btn btn-primary"/>
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
