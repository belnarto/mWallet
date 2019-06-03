<%--
  Created by IntelliJ IDEA.
  User: Belnarto
  Date: 31.05.2019
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <title>Registry</title>
</head>
<body>
<c:url value="/registry" var="var"/>
<form action="${var}" method="POST">
    <label for="name">name</label>
    <input type="text" name="name" id="name">

    <label for="login">login</label>
    <input type="text" name="login" id="login">

    <label for="password">password</label>
    <input type="text" name="password" id="password">

    <input type="submit" value="Add user">

    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>

    <br>
    <a href="/">Back to main</a>
</form>
</body>
</html>
