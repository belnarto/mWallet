<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Transfer money</title>
</head>
<body>
<div class="w3-container w3-grey w3-opacity w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/mWallet/users/${userId}/wallets'"><b>Back to wallets</b>
    </button>
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
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Transfer money:</h2>
            </div>
            <form autocomplete="off" name="transferMoneyForm" method="post" class="w3-selection w3-padding w3-center">
                <label for="amountToTransfer">Amount to transfer</label>
                <input type="text" name="amountToTransfer" id="amountToTransfer"
                       class="w3-input w3-animate-input w3-border w3-round-large" style="width: 100%">

                <label for="targetWalletId">Target wallet id</label>
                <div class="autocomplete w3-input w3-animate-input" style="width: 100%">
                    <input type="text" name="targetWalletId" id="targetWalletId"
                           class="w3-input w3-animate-input w3-border w3-round-large" style="width: 100%">
                </div>

                <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

            <script>
                <%@ include file="../../resources/js/findUserByWalletId.js" %>
            </script>

        </div>
    </div>
</div>

</body>
</html>
