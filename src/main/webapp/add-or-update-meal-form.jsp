<!DOCTYPE html>
<html>

<head>
    <title>Add/Update Meal</title>

</head>

<body>

<h3>Add/Update Meal</h3>

<form action="meals" method="POST">

    <input type="hidden" name="mealId" value="${meal.id}"/>

    <table>
        <tbody>
        <tr>
            <td><label>Date:</label></td>
            <td><input type="datetime-local" name="dateTime"
                       value="${meal.dateTime}"/></td>
        </tr>

        <tr>
            <td><label>Description:</label></td>
            <td><input type="text" name="description"
                       value="${meal.description}"/></td>
        </tr>

        <tr>
            <td><label>Calories:</label></td>
            <td><input type="number" name="calories"
                       value="${meal.calories}"/></td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Save" class="save"/></td>
        </tr>

        </tbody>
    </table>
</form>

<p>
    <a href="meals">Back to List</a>
</p>

</body>

</html>