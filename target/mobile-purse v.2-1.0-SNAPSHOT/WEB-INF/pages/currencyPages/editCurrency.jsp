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
    <title>Edit currency</title>
</head>
<body>
<c:url value="/currencies/editCurrency" var="var"/>
<form action="${var}" method="POST">
    <input type="hidden" name="id" value="${currency.id}">

    <label for="name">new name</label>
    <input type="text" name="name" id="name" value="${currency.name}">

    <label for="rate">new rate</label>
    <input type="text" name="rate" id="rate" value="${currency.rate}">

    <input type="submit" value="Edit currency">

    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>

    <br>
    <a href="/currencies">Back to currencies</a>
</form>
</body>
</html>
