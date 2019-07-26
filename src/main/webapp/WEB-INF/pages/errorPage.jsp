<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        <%@ include file="../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Error</title>
</head>
<body>

<div class="w3-container w3-grey w3-opacity w3-padding">
    <button class="w3-btn w3-round-large w3-left" onclick="location.href='/main'"><b>Back to main</b></button>
</div>

<div style="width:1265px" class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-container w3-padding">
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h1>${errorTitle}</h1>
                <h2>${errorMsg}</h2>
            </div>
        </div>
    </div>
</div>

</body>
</html>