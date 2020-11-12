<%--
  Created by IntelliJ IDEA.
  User: rando
  Date: 24/09/2020
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
    <title>Hello World</title>
  </head>
  <body>
  <h1>HelloWorld!</h1>
  <p>Hello world - setup!</p>
  <form action="/login">
    <input type="submit" value="login" />
  </form>
  <form action="/register">
    <input type="submit" value="register" />
  </form>
  </body>
</html>
