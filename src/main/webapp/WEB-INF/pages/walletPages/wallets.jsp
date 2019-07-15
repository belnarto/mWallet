<%@ page import="com.vironit.mwallet.models.Wallet" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <style>
        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Wallets</title>
</head>
<body>

<div class="w3-container w3-grey w3-opacity w3-padding">

    <c:choose>
        <c:when test="${currURL.equals(\"myWallets\")}">
            <button class="w3-btn w3-round-large w3-left" onclick="location.href='/'"><b>Back to main</b></button>
        </c:when>
        <c:otherwise>
            <button class="w3-btn w3-round-large w3-left" onclick="location.href='/users'"><b>Back to users</b></button>
        </c:otherwise>
    </c:choose>

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
                <h2>Wallets:</h2>
            </div>

            <jsp:useBean id="wallets" scope="request" type="java.util.List"/>
            <c:choose>
                <c:when test="${not empty wallets}">
                    <table style="width:1200px" class="w3-table w3-centered">

                        <tr class="w3-tr">
                            <th class="w3-hover-sand">Id:</th>
                            <th class="w3-hover-sand">Balance:</th>
                            <th class="w3-hover-sand">Currency:</th>
                            <th class="w3-hover-sand">Status:</th>
                            <th class="w3-hover-sand">Operations:</th>
                        </tr>
                        <c:forEach var="wallet" items="${wallets}">
                        <tr class="w3-hover-sand">
                            <td style="text-align:center" class="w3-hover-sand"><br>${wallet.id}</td>
                            <td style="text-align:center" class="w3-hover-sand"><br>${wallet.balance}</td>
                            <td style="text-align:center" class="w3-hover-sand"><br>${wallet.currency}</td>
                            <td style="text-align:center" class="w3-hover-sand"><br>${wallet.status}</td>
                            <td class="w3-hover-sand w3-center">
                                <form action="${pageContext.request.contextPath}/users/${id}/wallets/${wallet.id}/addBalance">
                                    <input class="w3-btn w3-hover w3-round-large" type="submit" value="Add money"/>
                                </form>
                                <form action="${pageContext.request.contextPath}/users/${id}/wallets/${wallet.id}/reduceBalance">
                                    <input class="w3-btn w3-hover w3-round-large" type="submit" value="Spend money"/>
                                </form>
                                <form action="${pageContext.request.contextPath}/users/${id}/wallets/${wallet.id}/transferMoney">
                                    <input class="w3-btn w3-hover w3-round-large" type="submit" value="Transfer money"/>
                                </form>
                                <form action="${pageContext.request.contextPath}/users/${id}/wallets/${wallet.id}/editWallet">
                                    <input class="w3-btn w3-hover w3-round-large" type="submit" value="Edit"/>
                                </form>
                                <form action="${pageContext.request.contextPath}/users/${id}/wallets/${wallet.id}/deleteWallet"
                                      method="post">
                                    <input class="w3-btn w3-hover w3-round-large" type="submit" value="Delete"/>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            </td>
                            </c:forEach>
                        </tr>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey">×</span>
                        <h5>There are no wallets!</h5>
                    </div>
                </c:otherwise>
            </c:choose>

            <button class="w3-btn w3-blue w3-round-large w3-margin-bottom"
                    onclick="location.href='/users/${id}/wallets/addWallet'">Add new wallet
            </button>
        </div>
    </div>
</div>
</body>
</html>
