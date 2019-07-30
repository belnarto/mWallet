<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        table {
            table-layout: fixed; /* Фиксированная ширина ячеек */
            width: 50%; /* Ширина таблицы */
        }

        <%@ include file="../../resources/css/w3.css" %>
    </style>

    <link rel="icon" type="image/png" href="https://www.onpage.com/wp-content/uploads/wallet-e1518717250505.png"/>
    <title>Users</title>

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

<div style="width:1265px" class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-container w3-padding">
        <form autocomplete="off" name="findUserByNamePart" method="get" onsubmit="get_action(this);"
              class="w3-selection w3-padding w3-center" action="/">
            <label for="namePart">Search by name: </label>
            <div class="autocomplete">
                <input type="text" name="namePart" id="namePart" placeholder="Name"
                       class="w3-input w3-animate-input w3-border w3-round-large" style="width: 100%">
            </div>

            <button type="submit" class="w3-btn w3-blue w3-round-large">Submit</button>

        </form>

        <script type="text/javascript">
            function get_action(form) {
                var userName = document.getElementById("namePart").value;
                var url = "http://localhost:8080/api/v1/users?namePart=" + userName
                var xhr = new XMLHttpRequest();
                var usersObjectArray;
                var usersStringArray;
                var userId;
                var matches = document.cookie.match(new RegExp(
                    "(?:^|; )" + "JwtToken".replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
                ))
                var token = matches ? decodeURIComponent(matches[1]) : undefined;
                xhr.open('GET', url, false);
                xhr.setRequestHeader("Authorization", "Bearer " + token);
                xhr.onload = function () {
                    usersObjectArray = JSON.parse(this.response);
                    usersStringArray = usersObjectArray.map(function(item) {
                        return item['id'];
                    });
                };
                xhr.send(null);
                userId = usersStringArray[0];

                form.action = "/users/" + userId;
            }
        </script>

        <script>
            function findUser(namePart) {
                var url = "http://localhost:8080/api/v1/users?namePart=" + namePart
                var xhr = new XMLHttpRequest();
                var usersObjectArray;
                var usersStringArray;
                var matches = document.cookie.match(new RegExp(
                    "(?:^|; )" + "JwtToken".replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
                ))
                var token = matches ? decodeURIComponent(matches[1]) : undefined;
                xhr.open('GET', url, false);
                xhr.setRequestHeader("Authorization", "Bearer " + token);
                xhr.onload = function () {
                    usersObjectArray = JSON.parse(this.response);
                    usersStringArray = usersObjectArray.map(function(item) {
                        return item['name'];
                    });
                    return usersStringArray;
                };
                xhr.send(null);
                return usersStringArray;
            }
        </script>

        <script>
            function autocomplete(inp) {
                /*the autocomplete function takes two arguments,
                the text field element and an array of possible autocompleted values:*/
                var currentFocus;
                /*execute a function when someone writes in the text field:*/
                inp.addEventListener("input", function(e) {
                    var a, b, i, val = this.value
                    var arr;
                    /*close any already open lists of autocompleted values*/
                    closeAllLists();
                    if (!val) { return false;}
                    currentFocus = -1;
                    /*create a DIV element that will contain the items (values):*/
                    a = document.createElement("DIV");
                    a.setAttribute("id", this.id + "autocomplete-list");
                    a.setAttribute("class", "autocomplete-items");
                    /*append the DIV element as a child of the autocomplete container:*/
                    this.parentNode.appendChild(a);
                    if (val.length > 2) {
                        arr = findUser(val);
                    }
                    /*for each item in the array...*/
                    for (i = 0; i < arr.length; i++) {
                        /*check if the item starts with the same letters as the text field value:*/
                        if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                            /*create a DIV element for each matching element:*/
                            b = document.createElement("DIV");
                            /*make the matching letters bold:*/
                            b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                            b.innerHTML += arr[i].substr(val.length);
                            /*insert a input field that will hold the current array item's value:*/
                            b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                            /*execute a function when someone clicks on the item value (DIV element):*/
                            b.addEventListener("click", function(e) {
                                /*insert the value for the autocomplete text field:*/
                                inp.value = this.getElementsByTagName("input")[0].value;
                                /*close the list of autocompleted values,
                                (or any other open lists of autocompleted values:*/
                                closeAllLists();
                            });
                            a.appendChild(b);
                        }
                    }
                });
                /*execute a function presses a key on the keyboard:*/
                inp.addEventListener("keydown", function(e) {
                    var x = document.getElementById(this.id + "autocomplete-list");
                    if (x) x = x.getElementsByTagName("div");
                    if (e.keyCode == 40) {
                        /*If the arrow DOWN key is pressed,
                        increase the currentFocus variable:*/
                        currentFocus++;
                        /*and and make the current item more visible:*/
                        addActive(x);
                    } else if (e.keyCode == 38) { //up
                        /*If the arrow UP key is pressed,
                        decrease the currentFocus variable:*/
                        currentFocus--;
                        /*and and make the current item more visible:*/
                        addActive(x);
                    } else if (e.keyCode == 13) {
                        /*If the ENTER key is pressed, prevent the form from being submitted,*/
                        e.preventDefault();
                        if (currentFocus > -1) {
                            /*and simulate a click on the "active" item:*/
                            if (x) x[currentFocus].click();
                        }
                    }
                });
                function addActive(x) {
                    /*a function to classify an item as "active":*/
                    if (!x) return false;
                    /*start by removing the "active" class on all items:*/
                    removeActive(x);
                    if (currentFocus >= x.length) currentFocus = 0;
                    if (currentFocus < 0) currentFocus = (x.length - 1);
                    /*add class "autocomplete-active":*/
                    x[currentFocus].classList.add("autocomplete-active");
                }
                function removeActive(x) {
                    /*a function to remove the "active" class from all autocomplete items:*/
                    for (var i = 0; i < x.length; i++) {
                        x[i].classList.remove("autocomplete-active");
                    }
                }
                function closeAllLists(elmnt) {
                    /*close all autocomplete lists in the document,
                    except the one passed as an argument:*/
                    var x = document.getElementsByClassName("autocomplete-items");
                    for (var i = 0; i < x.length; i++) {
                        if (elmnt != x[i] && elmnt != inp) {
                            x[i].parentNode.removeChild(x[i]);
                        }
                    }
                }
                /*execute a function when someone clicks in the document:*/
                document.addEventListener("click", function (e) {
                    closeAllLists(e.target);
                });
            }

            /*initiate the autocomplete function on the "myInput" element, and pass along the countries array as possible autocomplete values:*/
            autocomplete(document.getElementById("namePart"));
        </script>
    </div>

    <div class="w3-container w3-padding">
        <div class="w3-card-4">
            <div class="w3-container w3-center w3-light-blue">
                <h2>Users:</h2>
            </div>
            <table style="width:1200px" class="w3-table w3-centered">

                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="120">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="150">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="150">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="100">
                <%--suppress HtmlDeprecatedAttribute --%>
                <col width="240">

                <tr class="w3-tr">
                    <th class="w3-hover-sand">Id:</th>
                    <th class="w3-hover-sand">Name:</th>
                    <th class="w3-hover-sand">Login:</th>
                    <th class="w3-hover-sand">Role:</th>
                    <th class="w3-hover-sand">Last update:</th>
                    <th class="w3-hover-sand">Operations:</th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr class="w3-hover-sand">
                            <%--suppress HtmlDeprecatedAttribute --%>
                        <td valign="center" class="w3-hover-sand w3-center"><br>${user.id}</td>
                        <td style="text-align:center" class="w3-hover-sand"><br>${user.name}</td>
                        <td style="text-align:center" class="w3-hover-sand"><br>${user.login}</td>
                        <td style="text-align:center" class="w3-hover-sand"><br>${user.role.roleEnum}</td>
                        <td style="text-align:center" class="w3-hover-sand">
                            <br>${user.updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</td>
                        <td class="w3-hover-sand w3-center">
                            <p>
                            <form action="/users/${user.id}/wallets">
                                <input class="w3-btn w3-hover w3-round-large" type="submit" value="Wallets">
                            </form>
                            <button class="w3-btn w3-hover w3-round-large"
                                    onclick="location.href='/users/${user.id}/updateUser'">Update
                            </button>
                            <form action="/users/${user.id}/deleteUser" method="post">
                                <input class="w3-btn w3-hover w3-round-large" type="submit" value="Delete">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>

                        </td>
                    </tr>
                </c:forEach>
            </table>

            <button class="w3-btn w3-blue w3-round-large w3-margin-bottom"
                    onclick="location.href='/users/addUser'">Add new user
            </button>
        </div>
    </div>
</div>

</body>
</html>
