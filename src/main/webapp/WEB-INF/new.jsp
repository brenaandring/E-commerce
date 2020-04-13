<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add A New Product</title>
    </head>
    <body>
        <h1>Add A New Product!</h1>
        <p><form:errors path="item.*"/></p>

        <form:form method="POST" action="/items" modelAttribute="item">
            <table>
                <tr>
                    <td><form:label path="title">Title</form:label></td>
                    <td><form:input path="title"/></td>
                </tr>
                <tr>
                    <td><form:label path="description">Description</form:label></td>
                    <td><form:input path="description"/></td>
                </tr>
                <tr>
                    <td><form:label path="price">Price</form:label></td>
                    <td><form:input type="number" path="price"/></td>
                </tr>
                <tr>
                    <td><form:label path="image">Image</form:label></td>
                    <td><form:input type="file" path="image"/></td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Create"/></td>
                </tr>
            </table>
        </form:form>

        <form:form method="POST" action="/cancel">
            <input type="submit" value="Cancel"/>
        </form:form>

        <%--        <form method="POST" action="${pageContext.request.contextPath}/items" model="item">--%>
<%--            <p>--%>
<%--                <label for="title">Title:</label>--%>
<%--                <input type="text" id="title" name="title"/>--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label for="description">Description:</label>--%>
<%--                <textarea id="description" name="description"></textarea>--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label for="price">Price:</label>--%>
<%--                <input type="number" id="price" name="price"/>--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label for="image">Image:</label>--%>
<%--                <input type="file" id="image" name="image"/>--%>
<%--            </p>--%>
<%--            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
<%--            <input type="submit" value="Create!"/>--%>
<%--        </form>--%>

    </body>
</html>
