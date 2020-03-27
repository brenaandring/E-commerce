<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Welcome</title>
    </head>

    <body>
        <h1>Welcome, <c:out value="${user.firstName}" /></h1>
        <a href="/logout">Logout</a>
    </body>
</html>
