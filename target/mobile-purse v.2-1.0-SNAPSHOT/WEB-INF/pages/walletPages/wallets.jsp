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
    <title>Wallets</title>
</head>
<body>
    <h2>Wallets:</h2>
    <table>
        <tr>
            <th>id</th>
            <th>balance</th>
            <th>currency</th>
            <th>actions</th>
        </tr>
        <c:forEach var="wallet" items="${wallets}">
            <tr>
                <td>${wallet.id}</td>
                <td>${wallet.balance}</td>
                <td>${wallet.currency}</td>
                <td>
                    <a href="/users/${id}/wallets/addBalance/${wallet.id}">add money</a>
                    <a href="/users/${id}/wallets/reduceBalance/${wallet.id}">spend money</a>
                    <a href="/users/${id}/wallets/transferMoney/${wallet.id}">transfer money</a>
                    <a href="/users/${id}/wallets/editWallet/${wallet.id}">edit</a>
                    <a href="/users/${id}/wallets/deleteWallet/${wallet.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h3>Operations:</h3>
    <ul>
        <li>
            <c:url value="/users/${id}/wallets/addWallet" var="add"/>
            <a href="${add}">Add new wallet</a>
        </li>
    </ul>
    <br>
    <a href="/">Back to main</a>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h3>You are logged as : ${pageContext.request.userPrincipal.name}
            | <a href="<c:url value="/logout" />" > Logout</a></h3>
    </c:if>

</body>
</html>
