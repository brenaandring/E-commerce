<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Edit Your Product</title>
    </head>
    <body>
        <p><form:errors path="item.*"/></p>

        <form:form method="POST" action="/items/${item.id}" modelAttribute="item">
            <table>
                <tr>
                    <td><form:label path="title">Title</form:label></td>
                    <td><form:input path="title"/></td>
                </tr>
                <tr>
                    <td><form:label path="quantity">Quantity</form:label></td>
                    <td><form:input type="number" path="quantity"/></td>
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
                        <input type="submit" value="Update"/>
                    </td>
                </tr>
            </table>
        </form:form>

        <form:form method="POST" action="/cancel">
            <input type="submit" value="Cancel"/>
        </form:form>
    </body>
</html>
