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
    <title>Add currency</title>
</head>
<body>
    <div class="w3-container w3-grey w3-opacity w3-left-align w3-padding">
        <button class="w3-btn w3-round-large" onclick="location.href='/currencies'"><b>Back to currencies</b></button>
    </div>
    <div style="width:700px" class="w3-container w3-center w3-margin-bottom w3-padding">

            <div class="w3-container w3-padding">
                <%
                    if (request.getAttribute("currency") != null) {
                        out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                                "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                                "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                                "   <h5>Currency '" + request.getAttribute("currency") + "' added!</h5>\n" +
                                "</div>");
                    }
                %>
                <div class="w3-card-4">
                    <div class="w3-container w3-center w3-light-blue">
                        <h2>Add currency:</h2>
                    </div>
                    <form method="post" class="w3-selection w3-padding w3-center">
                        <label>Name:
                            <input type="text" name="name" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 100%"><br />
                        </label>
                        <label>Rate:
                            <input type="rate" name="rate" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 100%"><br />
                        </label>
                        <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
                    </form>
                </div>
            </div>
    </div>
</body>

</html>
