<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Add user</title>

    <script>
        function validatePasswordConfirmation() {
            var passwordEntered = document.forms["addUserForm"]["password"].value;
            var passwordConfirmationEntered = document.forms["addUserForm"]["passwordConfirmation"].value;
            if (passwordEntered !== passwordConfirmationEntered) {
                alert("Passwords are not the same");
                return false;
            }
        }
    </script>

</head>
<body>
<div class="w3-container w3-grey w3-opacity w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/users'"><b>Back to users</b></button>
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
<div style="width:700px" class="w3-container w3-center w3-margin-bottom w3-padding">

    <div class="w3-container w3-padding">
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Add user:</h2>
            </div>
            <form name="addUserForm" method="post" class="w3-selection w3-padding w3-center"
                  onsubmit="return validatePasswordConfirmation()">

                <label>Name:

                    <c:forEach var="fieldError" items="${fieldErrors}">
                        <c:if test="${fieldError.field.equalsIgnoreCase(\"name\")}">
                            <span style="color:red">${fieldError.defaultMessage}</span>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${user != null}">
                            <input type="text" name="name" class="w3-input w3-animate-input w3-border w3-round-large"
                                   value="${user.name}" style="width: 100%"><br/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="name" class="w3-input w3-animate-input w3-border w3-round-large"
                                   style="width: 100%"><br/>
                        </c:otherwise>
                    </c:choose>

                </label>

                <label>Login:

                    <c:forEach var="fieldError" items="${fieldErrors}">
                        <c:if test="${fieldError.field.equalsIgnoreCase(\"login\")}">
                            <span style="color:red">${fieldError.defaultMessage}</span>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${user != null}">
                            <input type="text" name="login" class="w3-input w3-animate-input w3-border w3-round-large"
                                   value="${user.login}" style="width: 100%"><br/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="login" class="w3-input w3-animate-input w3-border w3-round-large"
                                   style="width: 100%"><br/>
                        </c:otherwise>
                    </c:choose>

                </label>

                <label>Password:

                    <c:forEach var="fieldError" items="${fieldErrors}">
                        <c:if test="${fieldError.field.equalsIgnoreCase(\"password\")}">
                            <span style="color:red">${fieldError.defaultMessage}</span>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${user != null}">
                            <input type="password" name="password"
                                   class="w3-input w3-animate-input w3-border w3-round-large"
                                   value="${user.password}" style="width: 100%"><br/>
                        </c:when>
                        <c:otherwise>
                            <input type="password" name="password"
                                   class="w3-input w3-animate-input w3-border w3-round-large"
                                   style="width: 100%"><br/>
                        </c:otherwise>
                    </c:choose>

                </label>

                <label>Password confirmation:

                    <input type="password" name="passwordConfirmation"
                           class="w3-input w3-animate-input w3-border w3-round-large"
                           style="width: 100%"><br/>

                </label>

                <label>Role:

                    <select type="role" name="role">
                        <c:forEach var="role" items="${roles}">
                            <option>${role.roleEnum}</option>
                        </c:forEach>
                    </select>

                </label>

                <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            </form>
        </div>
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
    <%@ include file="../../resources/js/notification.js" %>
</script>
</body>
</html>
