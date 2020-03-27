<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Registration Page</title>
    </head>
    <body>
        <h1>Register!</h1>

        <p><form:errors path="user.*"/></p>

        <form:form method="POST" action="/registration" modelAttribute="user">
            <p>
                <form:label path="firstName">First Name:</form:label>
                <form:input type="text" path="firstName"/>
            </p>
            <p>
                <form:label path="lastName">Last Name:</form:label>
                <form:input type="text" path="lastName"/>
            </p>
            <p>
                <form:label path="email">Email:</form:label>
                <form:input type="email" path="email"/>
            </p>
            <p>
                <form:label path="password">Password:</form:label>
                <form:password path="password"/>
            </p>
            <p>
                <form:label path="passwordConfirmation">Password Confirmation:</form:label>
                <form:password path="passwordConfirmation"/>
            </p>
            <p>
                <form:label path="address">Address:</form:label>
                <form:input type="text" path="address"/>
            </p>
            <p>
                <form:label path="city">City:</form:label>
                <form:input type="text" path="city"/>
            </p>
            <p>
                <form:label path="state">State:</form:label>
                <form:input type="text" path="state"/>
            </p>
            <p>
                <form:label path="zip">Zip:</form:label>
                <form:input type="text" path="zip"/>
            </p>
            <input type="submit" value="Register!"/>
        </form:form>
    </body>
</html>
