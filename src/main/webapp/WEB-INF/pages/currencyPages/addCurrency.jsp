<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.vironit.mwallet.model.entity.Currency" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Add currency</title>
</head>
<body>
<div class="w3-container w3-grey w3-opacity w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/mWallet/currencies'"><b>Back to currencies</b></button>
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
                    <button class="w3-btn w3-round-large" onclick="location.href='/mWallet/logout'"><b>Logout</b></button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </c:when>
            <c:otherwise>
                <button class="w3-btn w3-round-large" onclick="location.href='/mWallet/login'"><b>Login</b></button>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<div style="width:700px" class="w3-container w3-center w3-margin-bottom w3-padding">

    <div class="w3-container w3-padding">
        <%--@elvariable id="currency" type="com.vironit.mwallet.model.entity.Currency"--%>
        <c:if test="${not empty currency}">
            <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
            <span onclick="this.parentElement.style.display='none'"
                  class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">×</span>
                <h5>Currency '${currency}' added!</h5>
            </div>
        </c:if>

        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Add currency:</h2>
            </div>
            <form method="post" class="w3-selection w3-padding w3-center">
                <label>Name:
                    <input type="text" name="name" class="w3-input w3-animate-input w3-border w3-round-large"
                           style="width: 100%"><br/>
                </label>
                <label>Rate:
                    <input type="text" name="rate" class="w3-input w3-animate-input w3-border w3-round-large"
                           style="width: 100%"><br/>
                </label>
                <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>
</body>

</html>
