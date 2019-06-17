<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        <%@ include file="../resources/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Main</title>
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-left-align">
    <h1>Welcome to wallet app!</h1>
</div>

<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/myWallets'">My wallets
        </button>
        <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/users'">All users</button>
        <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/currencies'">Currencies
        </button>
    </div>
</div>

</body>
</html>
