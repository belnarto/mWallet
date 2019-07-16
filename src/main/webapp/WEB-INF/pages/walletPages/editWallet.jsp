<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <style>
        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Edit wallet</title>
</head>
<body>
<div class="w3-container w3-grey w3-opacity w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/users/${userId}/wallets'"><b>Back to wallets</b></button>
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
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
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
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Edit wallet:</h2>
            </div>

            <form name="editWalletForm" method="post" class="w3-selection w3-padding w3-center">

                <label>Currency:

                    <select type="currency" name="currency">
                        <c:forEach var="currency" items="${currencies}">
                            <option>${currency}</option>
                        </c:forEach>
                        <option selected="selected">
                            ${wallet.currency}
                        </option>
                    </select>

                </label>

                <label>Status:

                    <select type="walletStatus" name="walletStatus">
                        <c:forEach var="status" items="${statuses}">
                            <option>${status}</option>
                        </c:forEach>
                        <option selected="selected">
                            ${wallet.walletStatus}
                        </option>
                    </select>

                </label>

                <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>

                <input type="hidden" name="id" value="${wallet.id}"/>

                <input type="hidden" name="user" value="${userId}"/>

                <input type="hidden" name="balance" value="${wallet.balance}"/>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            </form>
        </div>
    </div>
</div>


</body>
</html>
