<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Login Page</title>
    </head>
    <body>
        <c:if test="${logoutMessage != null }">
            <p>${logoutMessage}</p>
        </c:if>
        <h1>Login</h1>

        <c:if test="${errorMessage != null}">
            <p>${errorMessage}</p>
        </c:if>
        <form method="POST" action="/login">

            <p>
                <label for="email">Email:</label>
                <input type="text" id="email" name="username"/>
            </p>

            <p>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password"/>
            </p>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Login!"/>
        </form>
    </body>
</html>
