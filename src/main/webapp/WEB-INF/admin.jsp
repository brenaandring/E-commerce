<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Admin Dashboard</title>
    </head>
    <body>
        <h1>Welcome to the Admin Dashboard, <c:out value="${currentUser.firstName}"></c:out>!</h1>

        <form id="logoutForm" method="POST" action="/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Logout!" />
        </form>

        <p><a href="/items/new">Add Item</a></p>

    </body>
</html>
