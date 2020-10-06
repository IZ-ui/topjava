<!DOCTYPE html>
<html>

<head>
    <title>Update Meal</title>


</head>

<body>

    <h3>Update Meal</h3>

    <form action="meals" method="GET">

        <input type="hidden" name="command" value="UPDATE" />

        <input type="hidden" name="mealId" value="${THE_MEAL.id}" />

        <table>
            <tbody>
            <tr>
                <td><label>Date:</label></td>
                <td><input type="datetime-local" name="dateTime"
                           value="${THE_MEAL.dateTime}" /></td>
            </tr>

            <tr>
                <td><label>Desc:</label></td>
                <td><input type="text" name="description"
                           value="${THE_MEAL.description}"/></td>
            </tr>

            <tr>
                <td><label>Cals:</label></td>
                <td><input type="text" name="calories"
                           value="${THE_MEAL.calories}"/></td>
            </tr>

            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save" class="save" /></td>
            </tr>

            </tbody>
        </table>
    </form>

    <p>
        <a href="meals">Back to List</a>
    </p>

</body>

</html>