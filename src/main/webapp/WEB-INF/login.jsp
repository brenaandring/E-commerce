<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <p><c:out value="${error}" /></p>
        <form method="post" action="/login">
            <p>
                <label for="email">Email</label>
                <input type="text" id="email" name="email"/>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>
            </p>
            <input type="submit" value="Login!"/>
        </form>
    </body>
</html>
