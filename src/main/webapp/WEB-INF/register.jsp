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
            <p>
                <form:label path="email">Email:</form:label>
                <form:input path="email" name="username"/>
            </p>
            <p>
                <form:label path="firstName">First Name:</form:label>
                <form:input path="firstName"/>
            </p>
            <p>
                <form:label path="lastName">Last Name:</form:label>
                <form:input path="lastName"/>
            </p>
            <p>
                <form:label path="password">Password:</form:label>
                <form:password path="password"/>
            </p>
            <p>
                <form:label path="passwordConfirmation">Password Confirmation:</form:label>
                <form:password path="passwordConfirmation"/>
            </p>

            <input type="submit" value="Register!"/>
        </form:form>
    </body>
</html>
