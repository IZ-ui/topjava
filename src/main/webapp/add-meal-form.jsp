<!DOCTYPE html>
<html>

<head>
    <title>Add Meal</title>

</head>

<body>

    <h3>Add Student</h3>

    <form action="meals" method="GET">

        <input type="hidden" name="command" value="ADD" />

        <table>
            <tbody>
            <tr>
                <td><label>Date:</label></td>
                <td><input type="datetime-local" name="dateTime" /></td>
            </tr>

            <tr>
                <td><label>Desc:</label></td>
                <td><input type="text" name="description" /></td>
            </tr>

            <tr>
                <td><label>Cals:</label></td>
                <td><input type="text" name="calories" /></td>
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