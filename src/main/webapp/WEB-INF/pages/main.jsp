<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@ include file="../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Main</title>

</head>
<body class="w3-light-grey">

<div class="w3-container w3-grey w3-opacity w3-padding">
    <h4>Welcome to wallet app!</h4>
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

<div class="w3-container w3-center">
    <c:if test="${not empty added}">
        <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
            <span onclick="this.parentElement.style.display='none'"
                  class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">×</span>
            <h5>User '${user.login}' added!</h5>
        </div>
    </c:if>

    <div class="w3-bar w3-padding-large w3-padding-24">

        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/mWallet/users/${userId}'">My
                    user
                </button>
            </c:when>
            <c:otherwise>
                <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/mWallet/login'">My user
                </button>
            </c:otherwise>
        </c:choose>


        <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/mWallet/users'">All users</button>
        <button class="w3-btn w3-hover-light-blue w3-round-large" onclick="location.href='/mWallet/currencies'">Currencies
        </button>
    </div>
</div>
<div id="chat-page" class="hidden">
    <div class="chat-container">
        <div class="chat-header">
            <h6>Notifications:</h6>
        </div>
        <ul id="messageArea">
        </ul>
        <form id="messageForm" name="messageForm">
            <div class="form-group">
                <div class="input-group clearfix">
                    <input type="text" id="message" placeholder="Type a message to bot" autocomplete="off"
                           class="form-control"/>
                    <button type="submit" class="primary">Send</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script>
    <%@ include file="../resources/js/notification.js" %>
</script>

</body>
</html>
