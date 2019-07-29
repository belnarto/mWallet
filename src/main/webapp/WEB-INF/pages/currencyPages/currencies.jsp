<%@ page import="java.util.List" %>
<%@ page import="com.vironit.mwallet.model.entity.Currency" %>
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
    <title>Currencies</title>
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

<div style="width:665px" class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-container w3-padding">
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Currencies:</h2>
            </div>
            <jsp:useBean id="currencies" scope="request" type="java.util.List"/>
            <c:choose>
                <c:when test="${not empty currencies}">
                    <table style="width:600px" class="w3-table w3-centered">
                        <tr class="w3-tr">
                            <th class="w3-hover-sand">Name:</th>
                            <th class="w3-hover-sand">Rate:</th>
                            <th class="w3-hover-sand">Operations:</th>
                        </tr>
                        <c:forEach var="currency" items="${currencies}">
                        <tr class="w3-hover-sand">
                                <%--suppress HtmlDeprecatedAttribute --%>
                            <td valign="center" class="w3-hover-sand w3-center"><br>${currency}</td>
                            <td style="text-align:center" class="w3-hover-sand"><br>${currency.getRate()}</td>
                            <td class="w3-hover-sand w3-center">
                                <form action="${pageContext.request.contextPath}/currencies/editCurrency">
                                    <input type="hidden" name="currencyId" value="${currency.getId()}"/>
                                    <input class="w3-btn w3-hover w3-round-large" type="submit" value="Edit"/>
                                </form>
                                <form action="${pageContext.request.contextPath}/currencies/deleteCurrency"
                                      method="post">
                                    <input type="hidden" name="currencyId" value="${currency.getId()}"/>
                                    <input class="w3-btn w3-hover w3-round-large" type="submit" name="Delete"
                                           value="Delete"/>
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
                        <h5>There are no currencies!</h5>
                    </div>
                </c:otherwise>
            </c:choose>
            <button class="w3-btn w3-blue w3-round-large w3-margin-bottom"
                    onclick="location.href='/currencies/addCurrency'">Add new currency
            </button>
        </div>
    </div>
</div>
</body>

</html>
