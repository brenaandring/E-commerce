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

        <table>
            <thead>
                <tr>
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
                        <td><a href="/items/${item.id}">${item.title}</a></td>
                        <td>${item.description}</td>
                        <td>${item.price}</td>
                        <td>${item.image} blank</td>
                        <td><a href="/items/edit/${item.id}">Edit</a> |
                            <a href="/items/delete/${item.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
