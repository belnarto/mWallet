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
    <title>Users</title>
</head>
<body>
    <h2>Users:</h2>
    <table>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>actions</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>
                    <a href="/users/${user.id}/wallets">wallets</a>
                    <a href="/users/editUser/${user.id}">edit</a>
                    <a href="/users/deleteUser/${user.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h3>Operations:</h3>
    <ul>
        <li>
            <c:url value="/users/addUser" var="add"/>
            <a href="${add}">Add new user</a>
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
