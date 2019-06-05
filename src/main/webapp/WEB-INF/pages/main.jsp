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
    <%--<link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>--%>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <link rel="stylesheet" href="../../res/w3.css">
    <title>Main</title>
</head>
<body>
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Welcome to wallet app!</h1>
</div>

<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/myWallets'">My
            com.vironit.mWallet.wallets</button>
        <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/com.vironit.mWallet.users'">All
            com.vironit.mWallet.users</button>
        <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/currencies'">Currencies</button>
    </div>
</div>

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
