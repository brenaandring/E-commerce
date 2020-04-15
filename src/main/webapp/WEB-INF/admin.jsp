<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Admin Dashboard</title>
    </head>
    <body>
        <h1>Welcome to the Admin Dashboard, ${admin.firstName}!</h1>

        <form:form method="POST" id="logoutForm" action="/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Logout!"/>
        </form:form>

        <form:form method="POST" action="/items/new">
            <input type="submit" value="Add Item"/>
        </form:form>

        <h1>All Items</h1>

        <table>
            <thead>
            <tr>
                <th>Item ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Price</th>
                <th>Image</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${item}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td><a href="/items/${item.id}">${item.title}</a></td>
                    <td>${item.description}</td>
                    <td>${item.price}</td>
                    <td><img src="${item.image}" alt="uploaded item image" width="200"></td>
                    <td><a href="/items/edit/${item.id}">Edit</a> |
                        <a href="/items/delete/${item.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <hr>

        <h1>All Users</h1>

        <table>
            <thead>
            <tr>
                <th>User ID</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="users">
                <tr>
                    <td>${users.id}</td>
                    <td>${users.lastName}</td>
                    <td>${users.firstName}</td>
                    <td>${users.email}</td>
                    <td>
<%--                        <a href="/users/edit/${users.id}">Edit</a> |--%>
                        <a href="/users/delete/${users.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>
