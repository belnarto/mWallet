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
    <title>Add money</title>
</head>
<body>
<c:url value="/users/${id}/wallets/addBalance/${wallet.id}" var="var"/>
<form action="${var}" method="POST">
    <label for="amountToAdd">amount to add</label>
    <input type="text" name="amountToAdd" id="amountToAdd">

    <input type="submit" value="Add money">

    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>

    <br>
    <a href="/users/${id}/wallets">Back to wallets</a>
</form>
</body>
</html>
