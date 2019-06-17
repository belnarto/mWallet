<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        <%@ include file="../../resources/w3.css" %>
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
<div class="w3-container w3-grey w3-opacity w3-left-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../../..'"><b>Back to main</b></button>
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
                <col width="150">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="100">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="240">

                <tr class="w3-tr">
                    <th class="w3-hover-sand">Id:</th>
                    <th class="w3-hover-sand">Name:</th>
                    <th class="w3-hover-sand">Login:</th>
                    <th class="w3-hover-sand">Password:</th>
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
                        <td style="text-align:center" class="w3-hover-sand"><br>${user.password}</td>
                        <td style="text-align:center" class="w3-hover-sand"><br>${user.role.roleEnum}</td>
                        <td style="text-align:center" class="w3-hover-sand">
                            <br>${user.updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</td>
                        <td class="w3-hover-sand w3-center">
                            <p>
                            <form action="users/${user.id}/wallets">
                                <input class="w3-btn w3-hover w3-round-large" type="submit" value="Wallets">
                            </form>
                            <form action="users/editUser">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input class="w3-btn w3-hover w3-round-large" type="submit" value="Edit">
                            </form>
                            <form action="users/deleteUser">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input class="w3-btn w3-hover w3-round-large" type="submit" value="Delete">
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

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h3>You are logged as : ${pageContext.request.userPrincipal.name}
        | <a href="<c:url value="/logout" />"> Logout</a></h3>
</c:if>

</body>
</html>
