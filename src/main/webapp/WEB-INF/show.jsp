<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Product Information</title>
    </head>
    <body>
        <h1>Product Information</h1>

        <sec:authorize access="hasRole('ADMIN')">
            <a href="${pageContext.request.contextPath}/admin">Admin Dashboard</a> |
        </sec:authorize>

        <a href="${pageContext.request.contextPath}/">Home</a>

        <p>Title: ${item.title}</p>
        <p>Quantity: ${item.quantity}</p>
        <p>Description: ${item.description}</p>
        <p>Price: ${item.price}</p>
        <p>Image: <img src="data:image/jpeg;base64,${itemImage}" alt="uploaded item image"></p>

        <p><form:errors path="review.*"/></p>

        <form:form method="POST" action="/items/${item.id}/review" modelAttribute="review">
            <table>
                <tr>
                    <td><form:label path="comment">Your Review:</form:label></td>
                    <td><form:input path="comment"/></td>
                </tr>
                <tr>
                    <td><form:label path="rating"/>Rate: </td>
                    <td><form:select path="rating" multiple="false">
                            <form:options items="${ratings}"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Leave a Review"/>
                    </td>
                </tr>
            </table>
        </form:form>

        <table>
            <tr>
                <th>Rating</th>
                <th>Review</th>
            </tr>
            <c:forEach items="${item.reviews}" var="review">
                <tr>
                    <td>${review.rating}</td>
                    <td>${review.comment}</td>
                    <td><sec:authorize access="hasRole('ADMIN')">
                        <p><a href="/review/delete/${review.id}">Delete</a></p>
                    </sec:authorize></td>
                </tr>
            </c:forEach>
        </table>
        <sec:authorize access="hasRole('ADMIN')">
            <p><a href="/items/edit/${item.id}">Edit</a> |
                <a href="/items/delete/${item.id}">Delete</a>
            </p>
        </sec:authorize>
    </body>
</html>
