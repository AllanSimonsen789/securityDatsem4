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
            <h1>DEBATE FORUM</h1>

            <form action="/index.jsp">
                <input type="submit" value="Back to frontpage" class="btn btn-primary"/>
            </form>

            <h2>Post New Debate</h2>

            <c:choose>
                <c:when test="${sessionScope.get('username') !=null}">
                    <form action="/forum" method="post">
                        Title: <input type="text" name="title" width="30" required/></br>
                        Content: </br>
                        <textarea name="content" rows="4" cols="50" placeholder="Start New Discussion Here"
                                  style="resize: none;"
                                  required></textarea></br></br>
                        <input type="submit" value="Submit New Post" class="btn btn-primary"/>
                    </form>
                </c:when>
                <c:otherwise>
                    <p>Please login to post a new debate!</p>
                </c:otherwise>
            </c:choose>

            <p style="color:blue"><c:out value="${confirmation}"/></p>

            <h2>Previous Debates</h2>

            <p>The forum has <c:out value="${arraylen}"/> posts</p>

            <c:forEach var="post" items="${postlist}">
                </br></br>
                <a style="color: inherit; width: 50%" href="/post?post=<c:out value="${post.getPostID()}"/>">
                    <div style="border-style: solid; width: 50%">
                        <strong>Title: <c:out
                                value="${post.getPostTitle()}"/></strong></br>
                        ID: <c:out value="${post.getPostID()}"/> Posted by: <c:choose>
                        <c:when test="${post.getRole() == 'admin'}">
                            <div class="certAdmin">
                                <p style="color:blue"><c:out value="${post.getUsername()}"/></p>
                                <span class="certAdminText"> Certified admin </span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${post.getUsername()}"/>
                        </c:otherwise>
                    </c:choose></br>
                        Message Content: </br>
                            <c:out value="${post.getContens()}"/></br>
                        Posted on: <c:out value="${post.getCreationDate()}"/></br>
                    </div>
                </a>
            </c:forEach>
            <p style="color:red"><c:out value="${errorMessage}"/></p>
        </div>
    </body>
    <style>
        .certAdmin {
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

        .certAdmin:hover .certAdminText {
            visibility: visible;
        }
    </style>
</html>
