<%--
  Created by IntelliJ IDEA.
  User: allan
  Date: 13-11-2020
  Time: 15:26
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
        <title>Debate Forum</title>
    </head>
    <body>
        <div align="center">
            <h1>Forum Post</h1>

            <form action="/index">
                <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
                <input type="submit" value="Back to frontpage" class="btn btn-primary"/>
            </form>

            <form action="/forum">
                <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
                <input type="submit" value="Back to the forum" class="btn btn-primary"/>
            </form>

            <h2><c:out value="${post.getPostTitle()}"/></h2>

            <h3>ID: <c:out value="${post.getUserID()}"/> - Posted By: <c:choose>
                <c:when test="${post.getRole() == 'admin'}">
                    <div class="certAdmin">
                        <p style="color:blue"><c:out value="${post.getUsername()}"/></p>
                        <span class="certAdminText"> Certified admin </span>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:out value="${post.getUsername()}"/>
                </c:otherwise>
            </c:choose></br> - Posted On: <c:out value="${post.getCreationDate()}"/></h3>

            <p><c:out value="${post.getContens()}"/></p>

            <c:forEach var="reply" items="${replies}">
                </br></br>
                <div style="border-style: solid; width: 50%">
                <strong>Reply:<c:choose>
                    <c:when test="${reply.getRole() == 'admin'}">
                        <div class="certAdminReply">
                            <p style="color:blue"><c:out value="${reply.getUsername()}"/></p>
                            <span class="certAdminText"> Certified admin </span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${reply.getUsername()}"/>
                    </c:otherwise>
                </c:choose></strong> Posted on: <c:out value="${reply.getCreationDate()}"/>
                    Reply Content: </br>
                        <c:out value="${reply.getContens()}"/></br></br>
                </div>
            </c:forEach>

            <p style="color:blue"><c:out value="${confirmation}" /></p>

            <h2>Make a reply</h2>

            <c:choose>
                <c:when test="${sessionScope.get('username') !=null}">
                    <form action="/post" method="post">
                        <input type="hidden" name="web_token" value="<c:out value="${web_csrf_token}" />">
                        <textarea name="content" style="resize: none;" rows="4" cols="50" placeholder="Make a reply here" required></textarea></br>
                        <input type = "hidden" name = "postid" value = "<c:out value="${post.getPostID()}"/>"></br>
                        <input type="submit" value="Submit Reply" class="btn btn-primary"/>
                    </form>
                </c:when>
                <c:otherwise>
                    <p>Please login to reply to this debate!</p>
                </c:otherwise>
            </c:choose>
            <p style="color:red"><c:out value="${errorMessage}" /></p>
        </div>
    </body>
    <style>
        .certAdmin {
            position: relative;
            display: inline-block;
            border-bottom: 1px dotted black;
            height: 37px;
        }
        .certAdminReply{
            position: relative;
            display: inline-block;
            border-bottom: 1px dotted black;
            height: 21px;
        }

        .certAdmin .certAdminText {
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
        .certAdminReply .certAdminText {
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

        .certAdmin:hover .certAdminText {
            visibility: visible;
        }
        .certAdminReply:hover .certAdminText {
            visibility: visible;
        }
    </style>
</html>
