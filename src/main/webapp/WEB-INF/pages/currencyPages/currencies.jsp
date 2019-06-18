<%@ page import="java.util.List" %>
<%@ page import="com.vironit.mWallet.models.Currency" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
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
                <button class="w3-btn w3-round-large" onclick="location.href='/logout'"><b>Logout</b></button>
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
            <table style="width:600px" class="w3-table w3-centered">
                <tr class="w3-tr">
                    <th class="w3-hover-sand">Name:</th>
                    <th class="w3-hover-sand">Rate:</th>
                    <th class="w3-hover-sand">Operations:</th>
                </tr>
                <%
                    //noinspection unchecked
                    List<Currency> currencies = (List<Currency>) request.getAttribute("currencies");

                    if (currencies != null && !currencies.isEmpty()) {

                        for (Currency c : currencies) {
                            out.println("<tr class=\"w3-hover-sand\">");
                            out.println("<td valign=\"center\" class=\"w3-hover-sand w3-center\"><br>" + c.getName() + "</td>");
                            out.println("<td style=\"text-align:center\" class=\"w3-hover-sand\"><br>" + c.getRate() + "</td>");
                            out.println("<td class=\"w3-hover-sand w3-center\">");

                            out.println("<form action=\"/currencies/editCurrency\" >");
                            out.println("<input type=\"hidden\" name=\"currencyId\" value=\"" + c.getId() + "\" />");
                            out.println("<input class=\"w3-btn w3-hover w3-round-large\" type=\"submit\" value=\"Edit\"/>");
                            out.println("</form>");

                            out.println("<form action=\"/currencies/deleteCurrency\" method=\"post\">");
                            out.println("<input type=\"hidden\" name=\"currencyId\" value=\"" + c.getId() + "\" />");
                            out.println("<input class=\"w3-btn w3-hover w3-round-large\" type=\"submit\" name=\"Delete\" value=\"Delete\" />");
                            out.println("</form>");

                            out.println("</td>");
                            out.println("</tr>");
                        }

                    } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"
                            +
                            "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                            "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n" +
                            "   <h5>There are no currencies!</h5>\n" +
                            "</div>");
                %>
            </table>
            <button class="w3-btn w3-blue w3-round-large w3-margin-bottom"
                    onclick="location.href='/currencies/addCurrency'">Add new currency
            </button>
        </div>
    </div>
</div>
</body>

</html>
