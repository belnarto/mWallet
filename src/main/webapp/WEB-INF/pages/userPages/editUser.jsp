<%@ page import="com.vironit.mWallet.models.User" %>
<%@ page import="com.vironit.mWallet.models.Role" %>
<%@ page import="com.vironit.mWallet.services.RoleService" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Edit user</title>
</head>
<body>
<div class="w3-container w3-grey w3-opacity w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/users'"><b>Back to users</b></button>
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
                <button class="w3-btn w3-round-large" onclick="location.href='/logout'"><b>Logout</b></button>
            </c:when>
            <c:otherwise>
                <button class="w3-btn w3-round-large" onclick="location.href='/login'"><b>Login</b></button>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<div style="width:700px" class="w3-container w3-center w3-margin-bottom w3-padding">

    <div class="w3-container w3-padding">
        <%
            User user = (User) request.getAttribute("user");

            if (request.getAttribute("updated") != null) {
                out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                        "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                        "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                        "   <h5>User '" + ((User) request.getAttribute("user")).getLogin() + "' updated!</h5>\n" +
                        "</div>");
            }
        %>
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Edit user:</h2>
            </div>
            <form method="post" class="w3-selection w3-padding w3-center">
                <% out.println("<input type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\"><br/>"); %>
                <label>New name:
                    <% out.println("<input type=\"text\" name=\"name\" class=\"w3-input w3-animate-input w3-border w3-round-large\" value=\"" + user.getName() + "\" style=\"width: 100%\"><br/>"); %>
                </label>
                <label>New login:
                    <% out.println("<input type=\"text\" name=\"login\" class=\"w3-input w3-animate-input w3-border w3-round-large\" value=\"" + user.getLogin() + "\" style=\"width: 100%\"><br/>"); %>
                </label>
                <label>New password:
                    <% out.println("<input type=\"text\" name=\"password\" class=\"w3-input w3-animate-input w3-border w3-round-large\" value=\"keep_old_pass\" style=\"width: 100%\"><br/>"); %>
                </label>
                <label>Role:
                    <select name="newRoleName">
                        <c:forEach var="role" items="${roles}">
                            <option>${role.roleEnum}</option>
                        </c:forEach>
                        <option selected="selected">
                            ${user.role.roleEnum}
                        </option>
                    </select>
                </label>
                <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
