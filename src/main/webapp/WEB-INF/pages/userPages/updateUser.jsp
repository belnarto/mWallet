<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <style>
        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Update user</title>
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
<div style="width:700px" class="w3-container w3-center w3-margin-bottom w3-padding">

    <div class="w3-container w3-padding">
        <%--@elvariable id="updated" type="java.lang.String"--%>
        <c:if test="${not empty updated}">
            <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
            <span onclick="this.parentElement.style.display='none'"
                  class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">×</span>
                <h5>User '${user.login}' updated!</h5>
            </div>
        </c:if>
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Update user:</h2>
            </div>
            <form name="updateUserForm" method="post" class="w3-selection w3-padding w3-center">

                <input type="hidden" name="id" value="${user.id}"><br/>

                <label>New name:
                    <c:forEach var="fieldError" items="${fieldErrors}">
                        <c:if test="${fieldError.field.equalsIgnoreCase(\"name\")}">
                            <span style="color:red">${fieldError.defaultMessage}</span>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${user != null}">
                            <input type="text" name="name" class="w3-input w3-animate-input w3-border w3-round-large"
                                   value="${user.name}" style="width: 100%"><br/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="name" class="w3-input w3-animate-input w3-border w3-round-large"
                                   style="width: 100%"><br/>
                        </c:otherwise>
                    </c:choose>
                </label>

                <label>New login:
                    <c:forEach var="fieldError" items="${fieldErrors}">
                        <c:if test="${fieldError.field.equalsIgnoreCase(\"login\")}">
                            <span style="color:red">${fieldError.defaultMessage}</span>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${user != null}">
                            <input type="text" name="login" class="w3-input w3-animate-input w3-border w3-round-large"
                                   value="${user.login}" style="width: 100%"><br/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="login" class="w3-input w3-animate-input w3-border w3-round-large"
                                   style="width: 100%"><br/>
                        </c:otherwise>
                    </c:choose>
                </label>

                <label>New password:
                    <c:forEach var="fieldError" items="${fieldErrors}">
                        <c:if test="${fieldError.field.equalsIgnoreCase(\"password\")}">
                            <span style="color:red">${fieldError.defaultMessage}</span>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${user != null}">
                            <input type="password" name="password"
                                   class="w3-input w3-animate-input w3-border w3-round-large"
                                   value="${user.password}" style="width: 100%"><br/>
                        </c:when>
                        <c:otherwise>
                            <input type="password" name="password"
                                   class="w3-input w3-animate-input w3-border w3-round-large"
                                   style="width: 100%"><br/>
                        </c:otherwise>
                    </c:choose>
                </label>

                <label>Role:
                    <select name="role">
                        <c:forEach var="role" items="${roles}">
                            <option>${role.roleEnum}</option>
                        </c:forEach>
                        <option selected="selected">
                            ${user.role.roleEnum}
                        </option>
                    </select>
                </label>

                <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            </form>
        </div>
    </div>
</div>
</body>
</html>
