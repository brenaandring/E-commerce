<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Product Information</title>
    </head>
    <body>
        <h1>Product Information</h1>
        <a href="/admin">Admin Dashboard</a>

        <p>Title: ${item.title}</p>
        <p>Description: ${item.description}</p>
        <p>Price: ${item.price}</p>
        <p>Image: ${item.image}</p>

        <p><a href="">Edit</a> |
            <a href="/items/delete/${item.id}">Delete</a>
        </p>

    </body>
</html>
