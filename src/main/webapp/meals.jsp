<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table border="1">

    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <c:forEach var="tempMealTo" items="${mealTos}">

        <tr>
            <td>
                <text style="color: #ff0000"><javatime:format value="${tempMealTo.dateTime}" style="MS"/></text>
            </td>
            <td>
                <text style="color: #ff0000">${tempMealTo.description}</text>
            </td>
            <td>
                <text style="color: #ff0000"> ${tempMealTo.calories} </text>
            </td>
        </tr>

    </c:forEach>

</table>


</body>
</html>