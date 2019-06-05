<%@ page import="java.util.List" %>
<%@ page import="com.vironit.mWallet.models.Currency" %>
<%--Created by IntelliJ IDEA.
  User: Belnarto
  Date: 31.05.2019
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<html>
<head>
    <link rel="stylesheet" href="../res/w3.css">
    <link rel="icon" type="image/png" href="<c:url value="../res/favicon.png"/>"/>
    <title>Currencies</title>
    <style>
        table {
            table-layout: fixed; /* Фиксированная ширина ячеек */
            width: 50%; /* Ширина таблицы */
        }
    </style>
</head>
<body>
<div class="w3-container w3-grey w3-opacity w3-left-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'"><b>Back to main</b></button>
</div>
<div style="width:665px" class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-container w3-padding">
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Currencies:</h2>
            </div>
            <table style="width:600px" class="w3-table w3-centered" >
                <tr class="w3-tr">
                    <th class="w3-hover-sand">Name:</th>
                    <th class="w3-hover-sand">Rate:</th>
                    <th class="w3-hover-sand">Operations:</th>
                </tr>
                <%
                    List<Currency> currencies = (List<Currency>) request.getAttribute("currencies");

                    if (currencies != null && !currencies.isEmpty()) {

                        for (Currency c : currencies) {
                            out.println("<tr class=\"w3-hover-sand\">");
                            out.println("<td valign=\"center\" class=\"w3-hover-sand w3-center\"><br><br>" + c.getName() + "</td>");
                            out.println("<td style=\"text-align:center\" class=\"w3-hover-sand\"><br><br>" + c.getRate() + "</td>");
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
            <button class="w3-btn w3-blue w3-round-large w3-margin-bottom" onclick="location.href='/currencies/addCurrency'">Add new currency</button>
        </div>
    </div>
</div>
</body>

</html>
