<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Users</title>
    <style>
        table {
            table-layout: fixed; /* Фиксированная ширина ячеек */
            width: 50%; /* Ширина таблицы */
        }
    </style>
</head>
<body>

<div class="w3-container w3-grey w3-opacity w3-padding">
    <button class="w3-btn w3-round-large w3-left" onclick="location.href='/main'"><b>Back to main</b></button>
    <div class="w3-right">
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <h4>You are logged as : ${pageContext.request.userPrincipal.name}.</h4>
                <c:choose>
                    <c:when test="${pageContext.request.isUserInRole('ADMIN')}">
                        <h4>You role is : ADMIN.</h4>
                    </c:when>
                    <c:otherwise>
                        <h4>You role is : DEFAULT.</h4>
                    </c:otherwise>
                </c:choose>
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button class="w3-btn w3-round-large" onclick="location.href='/logout'"><b>Logout</b></button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </c:when>
            <c:otherwise>
                <button class="w3-btn w3-round-large" onclick="location.href='/login'"><b>Login</b></button>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div style="width:1265px" class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-container w3-padding">
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Users:</h2>
            </div>
            <table style="width:1200px" class="w3-table w3-centered">

                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="120">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="150">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="150">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="100">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="240">

                <tr class="w3-tr">
                    <th class="w3-hover-sand">Id:</th>
                    <th class="w3-hover-sand">Name:</th>
                    <th class="w3-hover-sand">Login:</th>
                    <th class="w3-hover-sand">Role:</th>
                    <th class="w3-hover-sand">Last update:</th>
                    <th class="w3-hover-sand">Operations:</th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr class="w3-hover-sand">
                            <%--suppress HtmlDeprecatedAttribute --%>
                        <td valign="center" class="w3-hover-sand w3-center"><br>${user.id}</td>
                        <td style="text-align:center" class="w3-hover-sand"><br>${user.name}</td>
                        <td style="text-align:center" class="w3-hover-sand"><br>${user.login}</td>
                        <td style="text-align:center" class="w3-hover-sand"><br>${user.role.roleEnum}</td>
                        <td style="text-align:center" class="w3-hover-sand">
                            <br>${user.updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</td>
                        <td class="w3-hover-sand w3-center">
                            <p>
                            <form action="/users/${user.id}/wallets">
                                <input class="w3-btn w3-hover w3-round-large" type="submit" value="Wallets">
                            </form>
                            <button class="w3-btn w3-hover w3-round-large"
                                    onclick="location.href='/users/${user.id}/updateUser'">Update
                            </button>
                            <form action="/users/${user.id}/deleteUser" method="post">
                                <input class="w3-btn w3-hover w3-round-large" type="submit" value="Delete">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>

                        </td>
                    </tr>
                </c:forEach>
            </table>

            <button class="w3-btn w3-blue w3-round-large w3-margin-bottom"
                    onclick="location.href='/users/addUser'">Add new user
            </button>
        </div>
    </div>
</div>

</body>
</html>
