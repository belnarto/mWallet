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
    <title>Main</title>
</head>
<body>
    <h2>Welcome to wallet app!</h2>
    <ul>
        <li><a href="/myWallets">My wallets</a></li>
        <li><a href="/users">Users</a></li>
        <li><a href="/currencies">Currencies</a></li>
    </ul>

    <c:choose>
        <c:when test="${pageContext.request.userPrincipal.name != null}">
            <h3>You are logged as : ${pageContext.request.userPrincipal.name}
                | <a href="<c:url value="/logout" />" > Logout</a></h3>
        </c:when>
        <c:otherwise>
            <a href="/registry">Registry</a>
        </c:otherwise>
    </c:choose>

</body>
</html>
