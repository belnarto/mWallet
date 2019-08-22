<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>Log in with your account</title>
    <style>
        <%@ include file="../resources/css/w3.css" %>
    </style>
    <style>
        <%@ include file="../resources/css/bootstrap.min.css" %>
    </style>
    <style>
        <%@ include file="../resources/css/common.css" %>
    </style>
    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
</head>

<body>

<div class="w3-container w3-grey w3-opacity w3-padding">
    <button class="w3-btn w3-round-large w3-left" onclick="location.href='/mWallet/mWallet/main'"><b>Back to main</b></button>
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
                    <button class="w3-btn w3-round-large" onclick="location.href='/mWallet/mWallet/logout'"><b>Logout</b></button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
            </c:when>
            <c:otherwise>
                <button class="w3-btn w3-round-large" onclick="location.href='/mWallet/mWallet/login'"><b>Login</b></button>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="container">
    <form method="POST" action="login" class="form-signin form-horizontal">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${logout}</span>
            <%--suppress HtmlFormInputWithoutLabel --%>
            <input name="username" type="text" class="form-control" placeholder="Username"/>
            <%--suppress HtmlFormInputWithoutLabel --%>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <label class="w3-text-black"><input name="remember-me" type="checkbox"> Remember me?</label>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="${pageContext.request.contextPath}/users/addUser">Create an account</a></h4>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
</div>

</body>
</html>