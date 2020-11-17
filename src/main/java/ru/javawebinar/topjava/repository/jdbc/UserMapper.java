package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return takeUser(resultSet);
    }

    public User takeUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        int id = resultSet.getInt("id");
        user.setId(id);
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRegistered(resultSet.getDate("registered"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setCaloriesPerDay(resultSet.getInt("calories_per_day"));
        Set<Role> userRoles = new HashSet<>();
        while (!resultSet.isAfterLast() && resultSet.getInt("id") == id && resultSet.getString("role") != null) {
            userRoles.add(Role.valueOf(resultSet.getString("role")));
            resultSet.next();
        }
        user.setRoles(userRoles);
        return user;
    }
}
