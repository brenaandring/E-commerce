<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Welcome</title>
    </head>

    <body>
        <form id="logoutForm" method="POST" action="/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Logout!"/>
        </form>

        <h1>Welcome <c:out value="${currentUser.firstName}"></c:out>!</h1>
    </body>
</html>
