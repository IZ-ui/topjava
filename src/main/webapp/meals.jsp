<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br/>
<a href="add-meal-form.jsp">Add Meal</a>

<table border="1">

    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>

    <c:forEach var="mealTo" items="${mealTos}">

        <!-- set up a link for each meal -->
        <c:url var="updateLink" value="meals">
            <c:param name="command" value="LOAD" />
            <c:param name="mealId" value="${mealTo.id}" />
        </c:url>

        <!-- set up a link to delete a meal -->
        <c:url var="deleteLink" value="meals">
            <c:param name="command" value="DELETE" />
            <c:param name="mealId" value="${mealTo.id}" />
        </c:url>

        <tr style="color: ${mealTo.excess ? "red" : "green"}">
            <td>
                <javatime:format value="${mealTo.dateTime}" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="${updateLink}">Update</a></td>
            <td><a href="${deleteLink}">Delete</a></td>
        </tr>

    </c:forEach>

</table>


</body>
</html>