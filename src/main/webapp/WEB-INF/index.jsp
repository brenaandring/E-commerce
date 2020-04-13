<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Welcome!</title>
    </head>
    <body>
        <a href="/registration">Register</a> |
        <a href="/login">Login</a> |

        <sec:authorize access="hasRole('ADMIN')">
            <a href="/admin">Admin Dashboard</a> |
        </sec:authorize>

        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Image</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${item}" var="item">
                    <tr>
                        <td><a href="/items/${item.id}">${item.title}</a></td>
                        <td>${item.description}</td>
                        <td>${item.price}</td>
                        <td>${item.image} blank</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
