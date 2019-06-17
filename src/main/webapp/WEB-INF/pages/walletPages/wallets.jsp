<%@ page import="com.vironit.mWallet.models.Wallet" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        <%@ include file="../../resources/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Wallets</title>
</head>
<body>
<div class="w3-container w3-grey w3-opacity w3-left-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/users'"><b>Back to users</b></button>
</div>
<div style="width:1265px" class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-container w3-padding">
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Wallets:</h2>
            </div>
            <table style="width:1200px" class="w3-table w3-centered">
                <tr class="w3-tr">
                    <th class="w3-hover-sand">Id:</th>
                    <th class="w3-hover-sand">Balance:</th>
                    <th class="w3-hover-sand">Currency:</th>
                    <th class="w3-hover-sand">Status:</th>
                    <th class="w3-hover-sand">Operations:</th>
                </tr>
                <%
                    //noinspection unchecked
                    List<Wallet> wallets = (List<Wallet>) request.getAttribute("wallets");

                    if (wallets != null && !wallets.isEmpty()) {

                        for (Wallet wallet : wallets) {
                            out.println("<tr class=\"w3-hover-sand\">");
                            out.println("<td valign=\"center\" class=\"w3-hover-sand w3-center\"><br>" + wallet.getId() + "</td>");
                            out.println("<td style=\"text-align:center\" class=\"w3-hover-sand\"><br>" + wallet.getBalance() + "</td>");
                            out.println("<td style=\"text-align:center\" class=\"w3-hover-sand\"><br>" + wallet.getCurrency() + "</td>");
                            out.println("<td style=\"text-align:center\" class=\"w3-hover-sand\"><br>" + wallet.getStatus() + "</td>");
                            out.println("<td class=\"w3-hover-sand w3-center\">");

                            out.println("<p><form action=\"/users/" + request.getAttribute("id") + "/wallets/" + wallet.getId() + "/addBalance\" >");
                            out.println("<input class=\"w3-btn w3-hover w3-round-large\" type=\"submit\" value=\"Add money\"/>");
                            out.println("</form>");

                            out.println("<form action=\"/users/" + request.getAttribute("id") + "/wallets/" + wallet.getId() + "/reduceBalance\" >");
                            out.println("<input class=\"w3-btn w3-hover w3-round-large\" type=\"submit\" value=\"Spend money\"/>");
                            out.println("</form>");

                            out.println("<form action=\"/users/" + request.getAttribute("id") + "/wallets/" + wallet.getId() + "/transferMoney\" >");
                            out.println("<input class=\"w3-btn w3-hover w3-round-large\" type=\"submit\" value=\"Transfer money\"/>");
                            out.println("</form>");

                            out.println("<form action=\"/users/" + request.getAttribute("id") + "/wallets/" + wallet.getId() + "/editWallet\" >");
                            out.println("<input class=\"w3-btn w3-hover w3-round-large\" type=\"submit\" value=\"Edit wallet\"/>");
                            out.println("</form>");

                            out.println("<form action=\"/users/" + request.getAttribute("id") + "/wallets/" + wallet.getId() + "/deleteWallet\" >");
                            out.println("<input class=\"w3-btn w3-hover w3-round-large\" type=\"submit\" value=\"Delete wallet\"/>");
                            out.println("</form></p>");

                            out.println("</td>");
                            out.println("</tr>");
                        }

                    } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"
                            +
                            "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                            "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n" +
                            "   <h5>There are no wallets!</h5>\n" +
                            "</div>");
                %>
            </table>
            <button class="w3-btn w3-blue w3-round-large w3-margin-bottom"
                    onclick="location.href='/users/${id}/wallets/addWallet'">Add new wallet
            </button>
        </div>
    </div>
</div>
</body>
</html>
