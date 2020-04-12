<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Product Information</title>
    </head>
    <body>
        <h1>Product Information</h1>

        <sec:authorize access="hasRole('ADMIN')">
            <a href="/admin">Admin Dashboard</a> |
        </sec:authorize>

        <a href="/">Home</a>

        <p>Title: ${item.title}</p>
        <p>Description: ${item.description}</p>
        <p>Price: ${item.price}</p>
        <p>Image: ${item.image}</p>

        <sec:authorize access="hasRole('ADMIN')">
            <p><a href="">Edit</a> |
                <a href="/items/delete/${item.id}">Delete</a>
            </p>
        </sec:authorize>
    </body>
</html>
