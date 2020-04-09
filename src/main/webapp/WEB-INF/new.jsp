<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add A New Product</title>
    </head>
    <body>
        <h1>Add A New Product!</h1>
        <form method="POST" action="/items">
            <p>
                <label for="title">Title:</label>
                <input type="text" id="title" name="title"/>
            </p>
            <p>
                <label for="description">Description:</label>
                <textarea type="text" id="description" name="description"></textarea>
            </p>
            <p>
                <label for="price">Price:</label>
                <input type="number" id="price" name="price"/>
            </p>
            <p>
                <label for="image">Image:</label>
                <input type="file" id="image" name="image"/>
            </p>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Create!"/>
        </form>

    </body>
</html>
