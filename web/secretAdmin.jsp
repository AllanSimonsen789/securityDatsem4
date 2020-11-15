<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 15/11/2020
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%
    if (session.getAttribute("username") !=null) {
        User u = (User) session.getAttribute("username");
        if (u.getRole().equals("admin")){
            // show page
            // success action...
        } else {
            response.sendRedirect("/index.jsp");
        }
    } else {
        // log noget her...
        // Nogen prøvede at komme ind på admin page.
        response.sendRedirect("/index.jsp");
    }
%>
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
    <h3>Skal nok routes igennem en admin servlet. Så kan vi måske også lægge et filter ned over den servlet.
        Så vi kan garantere at man kun kan tilgå den hvis man har admin role på useren som ligger i ens session.</h3>
    <form action="/index.jsp">
        <input type="submit" value="Back to frontpage" />
    </form>
</body>
</html>
