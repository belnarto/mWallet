<%--
  Created by IntelliJ IDEA.
  User: Belnarto
  Date: 31.05.2019
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <title>Add user</title>
</head>
<body>
<c:url value="/users/addUser" var="var"/>
<form action="${var}" method="POST">
    <label for="name">name</label>
    <input type="text" name="name" id="name">

    <input type="submit" value="Add user">

    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>

    <br>
    <a href="/users">Back to users</a>
</form>
</body>
</html>
