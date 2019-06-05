<%--
  Created by IntelliJ IDEA.
  User: Belnarto
  Date: 31.05.2019
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<html>
<head>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <title>Currencies</title>
</head>
<body>
    <h2>Currencies:</h2>
    <table>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>rate</th>
            <th>actions</th>
        </tr>
        <c:forEach var="com.vironit.mWallet.currency" items="${currencies}">
            <tr>
                <td>${com.vironit.mWallet.currency.id}</td>
                <td>${com.vironit.mWallet.currency.name}</td>
                <td>${com.vironit.mWallet.currency.rate}</td>
                <td>
                    <a href="/currencies/editCurrency/${com.vironit.mWallet.currency.id}">edit</a>
                    <a href="/currencies/deleteCurrency/${com.vironit.mWallet.currency.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h3>Operations:</h3>
    <ul>
        <li>
            <c:url value="/currencies/addCurrency" var="add"/>
            <a href="${add}">Add new com.vironit.mWallet.currency</a>
        </li>
    </ul>
    <br>
    <a href="/${com.vironit.mWallet.currency.id}">Back to main</a>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h3>You are logged as : ${pageContext.request.userPrincipal.name}
            | <a href="<c:url value="/logout" />" > Logout</a></h3>
    </c:if>

</body>
</html>
