<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        <%@ include file="../../resources/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Add wallet</title>
</head>
<body>
<div class="w3-container w3-grey w3-opacity w3-left-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/users/${id}/wallets'"><b>Back to wallets</b></button>
</div>
<div style="width:700px" class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-container w3-padding">
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Add wallet:</h2>
            </div>
            <c:url value="/users/${id}/wallets/addWallet" var="var"/>
            <form action="${var}" method="POST" class="w3-selection w3-padding w3-center">

                <label>Currency:
                    <select name="name">
                        <c:forEach var="name" items="${currencies}">
                            <option>${name}</option>
                        </c:forEach>
                    </select>
                </label>

                <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>

            </form>
        </div>
    </div>
</div>


</body>
</html>
