<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Please Register</title>
    </head>
    <body>
        <h1>Register</h1>
        <p><form:errors path="user.*"/></p>

        <form:form method="POST" action="/registration" modelAttribute="user">
            <table>
                <tr>
                    <td><form:label path="email">Email:</form:label></td>
                    <td><form:input path="email" name="username"/></td>
                </tr>
                <tr>
                    <td><form:label path="firstName">First Name:</form:label></td>
                    <td><form:input path="firstName"/></td>
                </tr>
                <tr>
                    <td><form:label path="lastName">Last Name:</form:label></td>
                    <td><form:input path="lastName"/></td>
                </tr>
                <tr>
                    <td><form:label path="password">Password:</form:label></td>
                    <td><form:password path="password"/></td>
                </tr>
                <tr>
                    <td><form:label path="passwordConfirmation">Password Confirmation:</form:label></td>
                    <td><form:password path="passwordConfirmation"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Register!"/></td>
                </tr>
            </table>
        </form:form>
    </body>
</html>
