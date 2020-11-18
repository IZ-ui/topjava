package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

//    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final UserMapper mapper = new UserMapper();

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
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

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        } else {
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        }
        List<Role> roles = List.copyOf(user.getRoles());
        rolesInsert(roles, user.getId());
        return user;
    }

    private void rolesInsert(final List<Role> roles, int id) {
        if (roles != null) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (role, user_id) VALUES (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, roles.get(i).name());
                            ps.setInt(2, id);
                        }

                        @Override
                        public int getBatchSize() {
                            return roles.size();
                        }
                    });
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query
                ("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE id=?",
                        mapper, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query
                ("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE email=?",
                        mapper, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query
                ("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email",
                        rs -> {
                            List<User> users = new ArrayList<>();
                            rs.next();
                            while (!rs.isAfterLast()) {
                                users.add(mapper.mapRow(rs, 999));
                            }
                            return users;
                        });
    }
}
